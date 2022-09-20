package com.freelancemarcel.gotown.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorSnackbar(modifier: Modifier, hostState: SnackbarHostState) {
    SnackbarHost(
        hostState = hostState,
        modifier = Modifier.padding(horizontal = 16.dp,).statusBarsPadding()
    ) {
        Snackbar(
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 0.dp,
            modifier = modifier
                .background(Color.Black)
                .border(1.dp, MaterialTheme.colors.error, shape = RoundedCornerShape(8.dp))
                .wrapContentSize()
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = it.message,
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.error)
                )
            }

        }
    }
}