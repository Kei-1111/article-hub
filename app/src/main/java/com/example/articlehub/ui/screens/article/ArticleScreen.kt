package com.example.articlehub.ui.screens.article


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.articlehub.model.Screen
import com.example.articlehub.ui.component.ArticleHubTopBar
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState

@Composable
fun ArticleScreen(
    url: String,
    viewModel: ArticleViewModel = hiltViewModel(),
    back: () -> Unit
) {
    val state = rememberWebViewState(url)

    val context = LocalContext.current

    LaunchedEffect(state.lastLoadedUrl, state.pageTitle) {
        viewModel.updateArticle(url = state.lastLoadedUrl ?: "", title = state.pageTitle ?: "")
    }

    Scaffold(
        topBar = {
            ArticleHubTopBar(
                screenTitle = "",
                currentScreen = Screen.Article,
                back = back,
                actions = { viewModel.shareArticleUrl(context = context) }
            )
        }
    ) { innerPadding ->
        ArticleScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            state = state
        )
    }
}

@Composable
fun ArticleScreenContent(
    modifier: Modifier = Modifier,
    state: WebViewState,
) {
    WebView(
        modifier = modifier,
        state = state,
        onCreated = { it.settings.javaScriptEnabled = true }
    )
}

