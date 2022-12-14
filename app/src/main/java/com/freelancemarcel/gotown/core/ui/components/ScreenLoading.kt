package com.freelancemarcel.gotown.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.freelancemarcel.gotown.core.ui.theme.BlueSapphire
import com.freelancemarcel.gotown.core.ui.theme.Rust

@Composable
fun ScreenLoading(){
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Rust.copy(alpha = .08f),
                            BlueSapphire.copy(alpha = .08f)
                        )
                    ),
                )
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.secondary)
        }
    }
}