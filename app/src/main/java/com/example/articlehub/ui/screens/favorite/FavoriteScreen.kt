package com.example.articlehub.ui.screens.favorite

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.articlehub.R
import com.example.articlehub.model.Article
import com.example.articlehub.model.Screen
import com.example.articlehub.ui.component.ArticleCard
import com.example.articlehub.ui.component.ArticleHubBottomBar
import com.example.articlehub.ui.component.ArticleHubDropdown
import com.example.articlehub.ui.component.ArticleHubTopBar
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navController: NavHostController,
    toArticle: (String) -> Unit,
    toQuerySetting: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val addFavorite = { article: Article ->
        viewModel.addArticleToFavorite(article)
        scope.launch {
            hostState.showSnackbar("「${article.title}」をお気に入りに追加しました")
        }
    }

    val deleteFavorite = { article: Article ->
        viewModel.deleteArticleFromFavorite(article)
        scope.launch {
            hostState.showSnackbar("「${article.title}」をお気に入りから削除しました")
        }
    }

    Scaffold(
        topBar = {
            ArticleHubTopBar(
                screenTitle = stringResource(id = Screen.Favorite.title),
                currentScreen = Screen.Favorite
            )
        },
        bottomBar = {
            ArticleHubBottomBar(
                navController = navController,
            )
        },
        snackbarHost = { SnackbarHost(hostState) }
    ) { innerPadding ->
        FavoriteScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            favoriteArticleList = uiState.favoriteArticleList,
            toArticle = toArticle,
            addFavorite = { addFavorite(it) },
            deleteFavorite = { deleteFavorite(it) }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteScreenContent(
    modifier: Modifier = Modifier,
    favoriteArticleList: List<Article>,
    toArticle: (String) -> Unit,
    addFavorite: (Article) -> Unit,
    deleteFavorite: (Article) -> Unit
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(favoriteArticleList) { article ->
            ArticleCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.small_padding))
                    .clickable(
                        onClick = {
                            toArticle(
                                URLEncoder.encode(
                                    article.url,
                                    StandardCharsets.UTF_8.toString()
                                )
                            )
                        }
                    ),
                article = article,
                addFavorite = { addFavorite(it) },
                deleteFavorite = { deleteFavorite(it) }
            )
        }
    }
}