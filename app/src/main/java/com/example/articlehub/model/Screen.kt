package com.example.articlehub.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.articlehub.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_name,
    @DrawableRes val icon: Int,
) {
    data object Home : Screen(
        route = "home",
        title = R.string.home_screen_title,
        icon = R.drawable.ic_home,
    )
    data object Article : Screen(
        route = "article",
        title = R.string.article_screen_title,
        icon = R.drawable.ic_article,
    )
    data object Favorite : Screen(
        route = "favorite",
        title = R.string.favorite_screen_title,
        icon = R.drawable.ic_favorite,
    )
    data object QuerySetting : Screen(
        route = "setting",
        title = R.string.keyword_setting_screen_title,
        icon = R.drawable.ic_setting,
    )
}