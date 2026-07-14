package com.nexus.ai.features.chat

import androidx.lifecycle.viewModelScope
import com.nexus.ai.core.ai.InferenceToken
import com.nexus.ai.core.ai.LlmInferenceEngine
import com.nexus.ai.core.ai.embedding.OnnxEmbeddingEngine
import com.nexus.ai.core.base.BaseViewModel
import com.nexus.ai.core.database.dao.MessageDao
import com.nexus.ai.data.source.local.entity.MessageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ChatUiState(
    val conversationId: String = "default_turn",
    val messagesList: List<MessageEntity> = emptyList(),
    val activeOutputStreamingText: String = "",
    val processingComputationActive: Boolean = false
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val inferenceEngine: LlmInferenceEngine,
    private val embeddingEngine: OnnxEmbeddingEngine,
    private val messageDao: MessageDao
) : BaseViewModel<ChatUiState>(ChatUiState()) {

    init {
        loadConversationHistory()
    }

    private fun loadConversationHistory() {
        viewModelScope.launch {
            messageDao.getMessagesForConversation(uiState.value.conversationId).collectLatest { entityList ->
                updateState { copy(messagesList = entityList) }
            }
        }
    }

    fun dispatchUserPrompt(text: String) {
        if (text.isBlank() || uiState.value.processingComputationActive) return

        val userMessage = MessageEntity(
            id = UUID.randomUUID().toString(),
            conversationId = uiState.value.conversationId,
            role = "user",
            content = text,
            timestamp = System.currentTimeMillis(),
            hasCitations = false
        )

        viewModelScope.launch {
            messageDao.insertMessage(userMessage)
            updateState { copy(processingComputationActive = true, activeOutputStreamingText = "") }

            // Semantic vector augmentation step (RAG Pipeline)
            val contextChunks = embeddingEngine.fetchRelevantKnowledgeChunks(text)
            val groundedPrompt = if (contextChunks.isNotEmpty()) {
                "Context information:\n${contextChunks.joinToString("\n")}\n\nUser instructions: $text"
            } else {
                text
            }

            inferenceEngine.initializeEngine("") // Triggers dynamic context classification pipeline
            
            inferenceEngine.streamModelInference(groundedPrompt).collect { executionToken ->
                when (executionToken) {
                    is InferenceToken.PartialChunk -> {
                        updateState { copy(activeOutputStreamingText = activeOutputStreamingText + executionToken.tokenText) }
                    }
                    is InferenceToken.ProcessCompleted -> {
                        val finalResponseContent = uiState.value.activeOutputStreamingText
                        val assistantMessage = MessageEntity(
                            id = UUID.randomUUID().toString(),
                            conversationId = uiState.value.conversationId,
                            role = "assistant",
                            content = finalResponseContent,
                            timestamp = System.currentTimeMillis(),
                            hasCitations = contextChunks.isNotEmpty()
                        )
                        messageDao.insertMessage(assistantMessage)
                        updateState { copy(processingComputationActive = false, activeOutputStreamingText = "") }
                    }
                    is InferenceToken.OperationalFailure -> {
                        updateState { copy(processingComputationActive = false) }
                    }
                }
            }
        }
    }
}
