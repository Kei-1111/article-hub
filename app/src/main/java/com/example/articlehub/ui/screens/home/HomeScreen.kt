package com.example.articlehub.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.articlehub.R
import com.example.articlehub.model.Article
import com.example.articlehub.model.ArticleSource
import com.example.articlehub.model.Screen
import com.example.articlehub.ui.component.ArticleCard
import com.example.articlehub.ui.component.ArticleHubBottomBar
import com.example.articlehub.ui.component.ArticleHubDropdown
import com.example.articlehub.ui.component.ArticleHubTopBar
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    toArticle: (String) -> Unit,
    toQuerySetting: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    val qiitaArticleList = viewModel.qiitaArticleList.collectAsLazyPagingItems()
    val zennArticleList = viewModel.zennArticleList.collectAsLazyPagingItems()
    val noteArticleList = viewModel.noteArticleList.collectAsLazyPagingItems()

    val hostState = remember { SnackbarHostState() }
    val pullToRefreshState = rememberPullToRefreshState()
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

    if(pullToRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            viewModel.refreshQiitaArticleList()
            viewModel.refreshZennArticleList()
            viewModel.refreshNoteArticleList()
            pullToRefreshState.endRefresh()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection),
        topBar = {
            ArticleHubTopBar(
                screenTitle = stringResource(id = Screen.Home.title),
                currentScreen = Screen.Home,
                actions = viewModel::expandDropdownMenu
            )
        },
        bottomBar = {
            ArticleHubBottomBar(
                navController = navController,
            )
        },
        snackbarHost = { SnackbarHost(hostState = hostState) },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            HomeScreenContent(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                qiitaArticleList = qiitaArticleList,
                zennArticleList = zennArticleList,
                noteArticleList = noteArticleList,
                selectQiitaTab = viewModel::selectQiitaTab,
                selectZennTab = viewModel::selectZennTab,
                selectNoteTab = viewModel::selectNoteTab,
                toArticle = toArticle,
                addFavorite = { addFavorite(it) },
                deleteFavorite = { deleteFavorite(it) }
            )

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        ArticleHubDropdown(
            dropdownMenus = mapOf(
                stringResource(id = R.string.keyword_setting) to toQuerySetting
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .wrapContentSize(Alignment.TopEnd),
            expanded = uiState.isExpanded,
            onDismissRequest = viewModel::closeDropdownMenu
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    qiitaArticleList: LazyPagingItems<Article>,
    zennArticleList: LazyPagingItems<Article>,
    noteArticleList: LazyPagingItems<Article>,
    selectQiitaTab: () -> Unit,
    selectZennTab: () -> Unit,
    selectNoteTab: () -> Unit,
    toArticle: (String) -> Unit,
    addFavorite: (Article) -> Unit,
    deleteFavorite: (Article) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        TabRow(
            selectedTabIndex = uiState.selectedTab.ordinal,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.tab_bar_height)),
        ) {
            Tab(
                selected = uiState.selectedTab == ArticleSource.QIITA,
                onClick = selectQiitaTab,
                text = {
                    Text(
                        text = stringResource(id = R.string.qiita),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
            Tab(
                selected = uiState.selectedTab == ArticleSource.ZENN,
                onClick = selectZennTab,
                text = {
                    Text(
                        text = stringResource(id = R.string.zenn),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
            Tab(
                selected = uiState.selectedTab == ArticleSource.NOTE,
                onClick = selectNoteTab,
                text = {
                    Text(
                        text = stringResource(id = R.string.note),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }

        when (uiState.selectedTab) {
            ArticleSource.QIITA -> {
                when (qiitaArticleList.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        ArticleCardList(
                            modifier = Modifier.fillMaxSize(),
                            articleList = qiitaArticleList,
                            toArticle = toArticle,
                            addFavorite = { addFavorite(it) },
                            deleteFavorite = { deleteFavorite(it) },
                        )
                    }

                    is LoadState.Loading -> {
                        LoadingContent()
                    }

                    is LoadState.Error -> {
                        ErrorContent(
                            refresh = {
                                qiitaArticleList.retry()
                            }
                        )
                    }
                }
            }

            ArticleSource.ZENN -> {
                when (zennArticleList.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        ArticleCardList(
                            modifier = Modifier.fillMaxSize(),
                            articleList = zennArticleList,
                            toArticle = toArticle,
                            addFavorite = { addFavorite(it) },
                            deleteFavorite = { deleteFavorite(it) },
                        )
                    }

                    is LoadState.Loading -> {
                        LoadingContent()
                    }

                    is LoadState.Error -> {
                        ErrorContent(
                            refresh = {
                                zennArticleList.retry()
                            }
                        )
                    }
                }
            }

            ArticleSource.NOTE -> {
                when (noteArticleList.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        ArticleCardList(
                            modifier = Modifier.fillMaxSize(),
                            articleList = noteArticleList,
                            toArticle = toArticle,
                            addFavorite = { addFavorite(it) },
                            deleteFavorite = { deleteFavorite(it) },
                        )
                    }

                    is LoadState.Loading -> {
                        LoadingContent()
                    }

                    is LoadState.Error -> {
                        ErrorContent(
                            refresh = {
                                noteArticleList.retry()
                            }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleCardList(
    modifier: Modifier = Modifier,
    articleList: LazyPagingItems<Article>,
    toArticle: (String) -> Unit,
    addFavorite: (Article) -> Unit,
    deleteFavorite: (Article) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(articleList.itemCount) { id ->
            articleList[id]?.let { article ->
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
                    addFavorite = {
                        addFavorite(article)
                    },
                    deleteFavorite = {
                        deleteFavorite(article)
                    }
                )
            }
        }
    }
}


@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(
    refresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_padding), Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(R.string.error),
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(
            onClick = refresh,
            modifier = Modifier
                .shadow(4.dp, MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_reload),
                contentDescription = "reload"
            )
        }
    }
}


