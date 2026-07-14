package com.nexus.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nexus.ai.features.chat.ChatViewModel
import com.nexus.ai.ui.components.AiOrb
import com.nexus.ai.ui.components.GlassCard

@Composable
fun MainChatScreen(viewModel: ChatViewModel) {
    val state by viewModel.uiState.collectAsState()
    var inputFieldValue by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Ambient Title Bar Matrix
            SmallTopAppBar(
                title = { Text("NEXUS DEPLOYMENT INTERFACE", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
            )

            // Dynamic Core Messaging Pipeline
            LazyColumn(
                modifier = Modifier
                    .fill MarySize
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.messagesList) { message ->
                    val positioningAlignment = if (message.role == "user") Alignment.End else Alignment.Start
                    val internalBubbleColor = if (message.role == "user") MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                    
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = positioningAlignment) {
                        GlassCard {
                            Column {
                                Text(
                                    text = message.content,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                if (message.hasCitations) {
                                    Text(
                                        text = "⚡ Augmented via Local Knowledge Collection",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                if (state.activeOutputStreamingText.isNotEmpty()) {
                    item {
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                            GlassCard {
                                Text(
                                    text = state.activeOutputStreamingText,
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }

            // Input Control Mechanics Dock Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .navigationBarsPadding()
                        .imePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = inputFieldValue,
                        onValueChange = { inputFieldValue = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Query completely offline...") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = {
                            viewModel.dispatchUserPrompt(inputFieldValue)
                            inputFieldValue = ""
                        }),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    IconButton(
                        onClick = {
                            viewModel.dispatchUserPrompt(inputFieldValue)
                            inputFieldValue = ""
                        },
                        enabled = inputFieldValue.isNotBlank() && !state.processingComputationActive
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Center Ambient Analytics Visualization Placement Overlay
        if (state.messagesList.isEmpty() && state.activeOutputStreamingText.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AiOrb()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Awaiting Instruction Vectors",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}
