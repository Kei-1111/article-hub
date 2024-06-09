package com.example.articlehub.ui.screens.query_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.articlehub.R
import com.example.articlehub.model.Screen
import com.example.articlehub.ui.component.ArticleHubTopBar

@Composable
fun QuerySettingScreen(
    viewModel: QuerySettingViewModel = hiltViewModel(),
    back: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            ArticleHubTopBar(
                screenTitle = stringResource(id = Screen.QuerySetting.title),
                currentScreen = Screen.QuerySetting,
                back = {
                    back()
                    viewModel.saveUserQuery()
                }
            )
        }
    ) { innerPadding ->
        QuerySettingScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            uiState = uiState,
            updateQiitaQueryUserInput = viewModel::updateQiitaQueryUserInput,
            updateZennQueryUserInput = viewModel::updateZennQueryUserInput,
            updateNoteQueryUserInput = viewModel::updateNoteQueryUserInput,
            removeQiitaQuery = viewModel::removeQiitaQuery,
            filterTags = viewModel::filterTags,
            setExpandSuggestions = viewModel::setExpandSuggestions,
            qiitaOnDone = viewModel::qiitaOnDone,
            zennOnDone = viewModel::zennOnDone,
            noteOnDone = viewModel::noteOnDone,
        )
    }
}

@Composable
fun QuerySettingScreenContent(
    modifier: Modifier = Modifier,
    uiState: QuerySettingUiState,
    updateQiitaQueryUserInput: (String) -> Unit,
    updateZennQueryUserInput: (String) -> Unit,
    updateNoteQueryUserInput: (String) -> Unit,
    removeQiitaQuery: (String) -> Unit,
    filterTags: (String) -> MutableList<String>,
    setExpandSuggestions: (Boolean) -> Unit,
    qiitaOnDone: (String) -> Unit,
    zennOnDone: (String) -> Unit,
    noteOnDone: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.small_padding))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_padding))
    ) {
        QiitaQuerySetting(
            modifier = Modifier.fillMaxWidth(),
            qiitaQueryUserInput = uiState.qiitaQueryUserInput,
            qiitaQueryList = uiState.qiitaQueryList,
            updateQiitaQueryUserInput = updateQiitaQueryUserInput,
            removeQiitaQuery = removeQiitaQuery,
            filterQiitaTagList = uiState.filteredQiitaTagList,
            filterTags = filterTags,
            expandSuggestions = uiState.expandSuggestions,
            setExpandSuggestions = setExpandSuggestions,
            onDone = qiitaOnDone
        )
        ZennQuerySetting(
            modifier = Modifier.fillMaxWidth(),
            zennQueryUserInput = uiState.zennQueryUserInput,
            zennQuery = uiState.zennQuery,
            updateZennQueryUserInput = updateZennQueryUserInput,
            onDone = zennOnDone
        )
        NoteQuerySetting(
            modifier = Modifier.fillMaxWidth(),
            noteQueryUserInput = uiState.noteQueryUserInput,
            noteQuery = uiState.noteQuery,
            updateNoteQueryUserInput = updateNoteQueryUserInput,
            onDone = noteOnDone
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QiitaQuerySetting(
    modifier: Modifier = Modifier,
    qiitaQueryUserInput: String,
    qiitaQueryList: MutableList<String>,
    updateQiitaQueryUserInput: (String) -> Unit,
    removeQiitaQuery: (String) -> Unit,
    filterQiitaTagList: MutableList<String>,
    filterTags: (String) -> MutableList<String>,
    expandSuggestions: Boolean,
    setExpandSuggestions: (Boolean) -> Unit,
    onDone: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
    ) {
        Text(
            text = stringResource(id = R.string.qiita),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.qiita_desciption),
            style = MaterialTheme.typography.titleMedium
        )
        Column {
            Text(
                text = stringResource(R.string.qiita_tag_list),
                style = MaterialTheme.typography.titleMedium,
            )
            Column(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.tag_list_height))
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                FlowRow(
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small_padding)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
                ) {
                    if (qiitaQueryList.isNotEmpty()) {
                        qiitaQueryList.forEach {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        modifier = Modifier.offset(x = -5.dp, y = -5.dp),
                                        containerColor = MaterialTheme.colorScheme.outline,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_batu),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(dimensionResource(id = R.dimen.tiny_icon_size))
                                                .clickable { removeQiitaQuery(it) },
                                            tint = MaterialTheme.colorScheme.surface
                                        )
                                    }
                                }
                            ) {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.onSurfaceVariant,
                                            MaterialTheme.shapes.medium
                                        )
                                        .padding(horizontal = dimensionResource(id = R.dimen.extra_small_padding)),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.surface
                                )
                            }
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.query_none),
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.onSurfaceVariant,
                                    MaterialTheme.shapes.medium
                                )
                                .padding(horizontal = dimensionResource(id = R.dimen.extra_small_padding)),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
            TextField(
                value = qiitaQueryUserInput,
                onValueChange = {
                    updateQiitaQueryUserInput(it)
                    setExpandSuggestions(it.isNotEmpty() && filterTags(it).isNotEmpty())
                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleMedium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onDone(qiitaQueryUserInput) }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )
            if (expandSuggestions) {
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.extra_small_padding)))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = MaterialTheme.shapes.extraLarge
                        )
                        .height(dimensionResource(id = R.dimen.tag_list_height))
                ) {
                    items(filterQiitaTagList.size) {
                        if (it != 0) HorizontalDivider(modifier = Modifier.fillMaxWidth())
                        Text(
                            text = filterQiitaTagList[it],
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = R.dimen.small_padding))
                                .clickable { onDone(filterQiitaTagList[it]) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ZennQuerySetting(
    modifier: Modifier = Modifier,
    zennQueryUserInput: String,
    zennQuery: String,
    updateZennQueryUserInput: (String) -> Unit,
    onDone: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
    ) {
        Text(
            text = stringResource(id = R.string.zenn),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.zenn_description),
            style = MaterialTheme.typography.titleMedium
        )
        Row {
            Text(
                text = stringResource(R.string.query),
                style = MaterialTheme.typography.titleMedium,
            )
            if (zennQuery.isNotEmpty()) {
                Text(
                    text = zennQuery,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = dimensionResource(id = R.dimen.extra_small_padding)),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            } else {
                Text(
                    text = "なし",
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = dimensionResource(id = R.dimen.extra_small_padding)),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
        TextField(
            value = zennQueryUserInput,
            onValueChange = { updateZennQueryUserInput(it) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleMedium,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone(zennQueryUserInput) }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        )
    }
}

@Composable
fun NoteQuerySetting(
    modifier: Modifier = Modifier,
    noteQueryUserInput: String,
    noteQuery: String,
    updateNoteQueryUserInput: (String) -> Unit,
    onDone: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
    ) {
        Text(
            text = stringResource(id = R.string.note),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.note_description),
            style = MaterialTheme.typography.titleMedium
        )
        Row {
            Text(
                text = stringResource(id = R.string.query),
                style = MaterialTheme.typography.titleMedium,
            )
            if (noteQuery.isNotEmpty()) {
                Text(
                    text = noteQuery,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = dimensionResource(id = R.dimen.extra_small_padding)),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            } else {
                Text(
                    text = "なし",
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = dimensionResource(id = R.dimen.extra_small_padding)),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
        TextField(
            value = noteQueryUserInput,
            onValueChange = { updateNoteQueryUserInput(it) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleMedium,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone(noteQueryUserInput) }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        )
    }
}