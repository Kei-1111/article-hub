package com.example.articlehub.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.articlehub.R
import com.example.articlehub.model.Screen

@Composable
fun ArticleHubTopBar(
    screenTitle: String,
    currentScreen: Screen,
    back: () -> Unit = {},
    actions: () -> Unit = {}
) {
    Surface(
        shadowElevation = dimensionResource(id = R.dimen.elevation),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.top_bar_height)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (currentScreen) {
                in listOf(Screen.Article, Screen.QuerySetting) -> {
                    IconButton(
                        onClick = back
                    ) {
                        Icon(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.small_icon_size)),
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                }

                else -> {}

            }
            Text(
                text = screenTitle,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = dimensionResource(id = R.dimen.medium_padding)),
                style = MaterialTheme.typography.headlineMedium,
            )
            when (currentScreen) {
                Screen.Home -> {
                    IconButton(
                        onClick = actions
                    ) {
                        Icon(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.small_icon_size)),
                            painter = painterResource(id = R.drawable.ic_three_point),
                            contentDescription = null
                        )
                    }
                }
                Screen.Article -> {
                    IconButton(
                        onClick = actions
                    ) {
                        Icon(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.small_icon_size)),
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null
                        )
                    }
                }
                else -> {}
            }
        }
    }
}