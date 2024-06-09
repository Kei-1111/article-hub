package com.example.articlehub.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberImagePainter
import com.example.articlehub.R
import com.example.articlehub.model.Article
import com.example.articlehub.model.ArticleSource
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    addFavorite: (Article) -> Unit,
    deleteFavorite: (Article) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.elevation))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_small_padding))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
            ) {
                Image(
                    painter = rememberImagePainter(article.userProfileImageUrl),
                    contentDescription = article.userName,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.user_icon_size))
                        .clip(MaterialTheme.shapes.small)
                        .background(Color.White, MaterialTheme.shapes.small)
                )
                Text(
                    text = article.userName,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
                when (ArticleSource.valueOf(article.source)) {
                    ArticleSource.QIITA -> {
                        Image(
                            painter = painterResource(id = R.drawable.logo_qiita),
                            contentDescription = null,
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.article_source_logo_size))
                                .clip(MaterialTheme.shapes.medium)
                                .background(Color.White, MaterialTheme.shapes.medium)
                        )
                    }

                    ArticleSource.ZENN -> {
                        Image(
                            painter = painterResource(id = R.drawable.logo_zenn),
                            contentDescription = null,
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.article_source_logo_size))
                                .clip(MaterialTheme.shapes.medium)
                                .background(Color.White, MaterialTheme.shapes.medium)
                                .padding(dimensionResource(id = R.dimen.extra_small_padding))
                        )
                    }

                    ArticleSource.NOTE -> {
                        Image(
                            painter = painterResource(id = R.drawable.logo_note),
                            contentDescription = null,
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.article_source_logo_size))
                                .clip(MaterialTheme.shapes.medium)
                                .background(Color.White, MaterialTheme.shapes.medium)
                        )
                    }
                }
            }
            Text(
                text = article.title,
                modifier = Modifier.height(dimensionResource(id = R.dimen.article_title_height)),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Column {
                Text(
                    text = stringResource(R.string.create_day),
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = formatDate(article.createdDate),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Column {
                Text(
                    text = stringResource(R.string.tag),
                    style = MaterialTheme.typography.labelSmall,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_small_padding))
                ) {
                    LazyRow(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_small_padding)),
                    ) {
                        items(article.tags) { tag ->
                            Text(
                                text = tag.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.background,
                                        MaterialTheme.shapes.medium
                                    )
                                    .padding(horizontal = dimensionResource(id = R.dimen.extra_small_padding))
                            )
                        }
                    }
                    IconButton(
                        modifier = Modifier.size(dimensionResource(id = R.dimen.small_icon_size)),
                        onClick = {
                            if (article.favoriteState) {
                                article.deleteFavorite()
                                deleteFavorite(article)
                            } else {
                                article.addFavorite()
                                addFavorite(article)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_favorite),
                            contentDescription = null,
                            tint = if (article.favoriteState) {
                                Color.Yellow
                            } else {
                                Color.Gray
                            }
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatDate(data: String): String {
    val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    val zonedDateTime = ZonedDateTime.parse(data, inputFormatter)
    return zonedDateTime.format(outputFormatter)
}