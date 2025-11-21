package com.curiousapps.apimyapi.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.curiousapps.apimyapi.ui.theme.ApiMyApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiMyApiTheme {
                val viewmodel = hiltViewModel<EmojiListViewModel>()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "emoji_list_screen",
                ) {
                    composable(
                        route = "emoji_list_screen",
                    ){
                        EmojiListScreen(
                            onSelectedEmoji = { viewmodel.fetchOneData(it) },
                            onNavigationClick = { slug ->
                                navController.navigate("emoji_detail_screen/$slug")
                            }
                        )
                    }
                    composable(route = "emoji_detail_screen/{slug}") {
                        val slug = it.arguments?.getString("slug") ?: ""
                        EmojiDetailScreen(
                            slug = slug,
                            onBackPressed = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApiMyApiTheme {
        Greeting("Android")
    }
}