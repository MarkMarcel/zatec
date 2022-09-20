package com.freelancemarcel.gotown.houses.house

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freelancemarcel.gotown.R
import com.freelancemarcel.gotown.core.IceAndFireApplicationViewModel
import com.freelancemarcel.gotown.core.ui.components.FlowRow
import com.freelancemarcel.gotown.core.ui.theme.GOTFont

fun List<String>.hasContent():Boolean{
    var content = ""
    for(s in this) content += s
    return content.isNotBlank()
}

@Composable
private fun Dot() {
    Spacer(
        Modifier
            .size(6.dp)
            .background(
                color = Color.White.copy(alpha = 0.8f),
                shape = CircleShape
            )
    )
}

@Composable
private fun HouseScreenHeader(onNavigateBack: () -> Unit) {
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
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back), tint = Color.White)
            }
        }
    }
}

@Composable
fun HouseScreen(houseUrl:String,viewModel: IceAndFireApplicationViewModel,onNavigateBack: () -> Unit){
    val house by viewModel.getValueAsState(IceAndFireApplicationViewModel.State::selectedHouse)

    Column(Modifier.fillMaxWidth().statusBarsPadding()) {
        HouseScreenHeader(onNavigateBack = onNavigateBack)
        LazyColumn(
            Modifier
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)){
            item {
                house?.let {
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(Modifier.height(32.dp))
                        Text(
                            text = it.name,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6.copy(color = Color.White, fontFamily = GOTFont),
                        )
                        if(it.words.isNotBlank()){
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = it.words,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.caption.copy(color = Color.White.copy(alpha = 0.8f), fontFamily = FontFamily.Serif),
                            )
                        }
                        Spacer(Modifier.height(48.dp))
                        Text(
                            text = stringResource(R.string.region),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.caption.copy(color = Color.White, fontFamily = GOTFont),
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = it.region,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.caption.copy(color = Color.White.copy(alpha = 0.8f), fontFamily = FontFamily.Serif),
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.coat_of_arms),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.caption.copy(color = Color.White, fontFamily = GOTFont),
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = it.coatOfArms,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.caption.copy(color = Color.White.copy(alpha = 0.8f), fontFamily = FontFamily.Serif),
                        )
                        if(it.founded.isNotBlank()){
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.founded),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.caption.copy(color = Color.White, fontFamily = GOTFont),
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = it.founded,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.caption.copy(color = Color.White.copy(alpha = 0.8f), fontFamily = FontFamily.Serif),
                            )
                        }
                        if(it.titles.hasContent()){
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.titles),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.caption.copy(color = Color.White, fontFamily = GOTFont),
                            )
                            Spacer(Modifier.height(8.dp))
                            FlowRow(Modifier.fillMaxWidth(), alignment = Alignment.CenterHorizontally, horizontalGap = 8.dp, verticalGap = 8.dp){
                                for((i,title) in it.titles.withIndex()){
                                    if(i != 0){
                                        Dot()
                                    }
                                    Text(
                                        text = title,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.caption.copy(color = Color.White.copy(alpha = 0.8f), fontFamily = FontFamily.Serif),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit){
        viewModel.getHouse(houseUrl)
    }
}