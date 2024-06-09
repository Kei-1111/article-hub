package com.example.articlehub.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.articlehub.model.Screen.Article
import com.example.articlehub.model.Screen.Favorite
import com.example.articlehub.model.Screen.Home
import com.example.articlehub.model.Screen.QuerySetting
import com.example.articlehub.ui.screens.article.ArticleScreen
import com.example.articlehub.ui.screens.favorite.FavoriteScreen
import com.example.articlehub.ui.screens.home.HomeScreen
import com.example.articlehub.ui.screens.query_setting.QuerySettingScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home.route,
    ) {
        composable(route = Home.route) {
            HomeScreen(
                navController = navController,
                toArticle = { url ->
                    navController.navigate("${Article.route}/$url")
                },
                toQuerySetting = {
                    navController.navigate(QuerySetting.route)
                },
            )
        }
        composable(
            route = "${Article.route}/{url}",
            arguments = listOf(
                navArgument("url") { type = NavType.StringType },
            ),
        ) {
            ArticleScreen(
                url = it.arguments?.getString("url") ?: "",
                back = { navController.popBackStack() },
            )
        }
        composable(route = Favorite.route) {
            FavoriteScreen(
                navController = navController,
                toArticle = { url ->
                    navController.navigate("${Article.route}/$url")
                },
                toQuerySetting = {
                    navController.navigate(QuerySetting.route)
                },
            )
        }
        composable(route = QuerySetting.route) {
            QuerySettingScreen(
                back = { navController.navigate(Home.route) }
            )
        }
    }
}