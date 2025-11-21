package com.curiousapps.apimyapi.ui.presentation


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.curiousapps.apimyapi.domain.EmojiListItem

@Composable
fun EmojiListScreen(
    viewModel: EmojiListViewModel = hiltViewModel(),
    onSelectedEmoji: (slug: String) -> Unit,
    onNavigationClick: (slug: String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState(EmojiListViewModel.EmojiListState())
    val emojiList = state.emojiList
    val isLoading = state.isLoading
    val showDialog = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf<EmojiListItem?>(null) }

    GradientBackground(
        primaryColor = Color.Yellow
    )
    if (isLoading) {
        ProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 32.dp, bottom = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val count = emojiList.size
                items(count = count) { index ->
                    val emojiItem = emojiList[index]
                    Log.i("enoch", "EmojiListScreen: $emojiItem")
                    EmojiListRow(
                        emojiItem = emojiItem,
                        modifier = Modifier.clickable {
                            selectedItem.value = emojiItem
                            onSelectedEmoji(emojiItem.slug)
                            showDialog.value = true
                            Toast.makeText(
                                context,
                                "Selected Emoji: ${emojiItem.character}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
    if (showDialog.value) {
        EmojiDialog(
            emojiListItem = selectedItem.value!!,
            onDismiss = {
                showDialog.value = false
            },
            onNavigateClick = { slug ->
                onNavigationClick(slug)
                showDialog.value = false
                selectedItem.value = null
            }
        )
    }
}

@Composable
fun EmojiDialog(
    emojiListItem: EmojiListItem,
    onDismiss: () -> Unit,
    onNavigateClick: (slug: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(16.dp)

        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    text = emojiListItem.character,
                    fontSize = 100.sp
                )
                Spacer(modifier = modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        onNavigateClick(emojiListItem.slug)
                    }
                ) {
                    Text(text = "Navigate to Details")
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = onDismiss
                ) {
                    Text(
                        text = "Close",
                        color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun EmojiListRow(
    emojiItem: EmojiListItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emojiItem.character,
                fontSize = 80.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Emoji Group: ${emojiItem.group}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Emoji UnicodeName: ${emojiItem.unicodeName}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    color: Color = Color.Red,
    strokeWidth: Dp = 4.dp
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size = size),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

@Composable
fun GradientBackground(
    primaryColor: Color,
    topColor: Color = Color.LightGray,
    bottomColor: Color = Color.Black
) {
    val gradient = Brush.verticalGradient(
        listOf(
            topColor,
            primaryColor,
            primaryColor,
            primaryColor,
            primaryColor,
            bottomColor
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    )
}
