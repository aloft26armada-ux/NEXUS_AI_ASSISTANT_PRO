package com.nexus.ai.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexus.ai.features.chat.ChatViewModel
import com.nexus.ai.ui.screens.MainChatScreen

@Composable
fun NexusNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_chat_view") {
        composable("main_chat_view") {
            val chatVm: ChatViewModel = hiltViewModel()
            MainChatScreen(viewModel = chatVm)
        }
    }
}
