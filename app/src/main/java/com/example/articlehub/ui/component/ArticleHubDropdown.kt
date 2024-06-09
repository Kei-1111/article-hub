package com.example.articlehub.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ArticleHubDropdown(
    dropdownMenus: Map<String, () -> Unit>,
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            dropdownMenus.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        onDismissRequest()
                        label.value()
                    },
                    text = {
                        Text(text = label.key)
                    }
                )
            }
        }
    }
}
