package com.freelancemarcel.gotown.houses

import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.freelancemarcel.gotown.R
import com.freelancemarcel.gotown.core.IceAndFireApplicationViewModel
import com.freelancemarcel.gotown.core.models.Error
import com.freelancemarcel.gotown.core.ui.theme.GOTFont
import com.freelancemarcel.gotown.houses.models.HouseListItem
import com.freelancemarcel.gotown.houses.models.ListItemHouse

@Composable
private fun ListOfHousesScreenHeader() {
    Box(
        Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.houses),
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.h6.copy(color = Color.White, fontFamily = GOTFont),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ListOfHousesScreen(viewModel: IceAndFireApplicationViewModel, onViewHouse: (String) -> Unit) {
    val houses: LazyPagingItems<HouseListItem> = viewModel.houses.collectAsLazyPagingItems()
    var isLoadError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    /*Column(
        Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(32.dp))
        HtmlText(
            text = HtmlCompat.fromHtml(
                stringResource(R.string.tos),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ),
            color = Color.White,
            style = MaterialTheme.typography.body1,
            factory = { context -> TextView(context) },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        )
    }*/

    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(32.dp))
        ListOfHousesScreenHeader()
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(houses) { house ->
                house?.let {
                    ListItemHouse(house = it, onViewHouse = onViewHouse)
                    Spacer(Modifier.height(16.dp))
                }
            }
            if (isLoading) {
                item {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(16.dp))
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }
            if (isLoadError) {
                item {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(16.dp))
                        IconButton(onClick = {
                            isLoadError = false
                            isLoading = true
                            houses.retry()
                        }) {
                            Icon(Icons.Default.Refresh,contentDescription = "Refresh", tint = Color.White)
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = houses.loadState) {
        when (houses.loadState.refresh) {
            is LoadState.Loading -> {
                isLoading = true
            }
            is LoadState.Error -> {
                val e = (houses.loadState.refresh as LoadState.Error).error as Error
                viewModel.onError(e)
                isLoading = false
                isLoadError = true
            }
            else -> {
                isLoading = false
            }
        }
        when (houses.loadState.append) {
            is LoadState.Loading -> {
                isLoading = true
            }
            is LoadState.Error -> {
                val e = (houses.loadState.refresh as LoadState.Error).error as Error
                viewModel.onError(e)
                isLoading = false
                isLoadError = true
            }
            else -> {
                isLoading = false
            }
        }
    }
}