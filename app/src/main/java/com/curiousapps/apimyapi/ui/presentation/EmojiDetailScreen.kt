package com.curiousapps.apimyapi.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.curiousapps.apimyapi.domain.EmojiListItem

@Composable
fun EmojiDetailScreen(
    viewmodel: EmojiListViewModel = hiltViewModel(),
    slug: String,
    onBackPressed: () -> Unit
) {
    val state by viewmodel.state.collectAsState(EmojiListViewModel.EmojiListState())
    val selectedEmoji = state.selectedEmoji

    LaunchedEffect(key1 = slug) {
        viewmodel.fetchOneData(slug = slug)
    }

    EmojiDetailContent(
        emoji = selectedEmoji,
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiDetailContent(
    emoji: List<EmojiListItem>,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Emoji Detail") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        GradientBackground(
            primaryColor = Color.Green,
            bottomColor = Color.DarkGray,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = emoji.firstOrNull()?.character ?: "No Emoji)",
                fontSize = 300.sp)
            Text(text = "CodePoint: ${emoji.firstOrNull()?.codePoint ?: "N/A"}")
            Text(text = "Group: ${emoji.firstOrNull()?.group ?: "N/A"}")
            Text(text = "Slug: ${emoji.firstOrNull()?.slug ?: "N/A"}")
            Text(text = "Sub-Group: ${emoji.firstOrNull()?.subGroup ?: "N/A"}")
            Text(text = "Unicode Name: ${emoji.firstOrNull()?.unicodeName ?: "N/A"}")

        }

    }
}
