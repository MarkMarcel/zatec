package com.freelancemarcel.gotown.houses.models

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.freelancemarcel.gotown.core.ui.theme.HouseListItemShape

@Composable
fun ListItemHouse(house: HouseListItem, onViewHouse: (String) -> Unit){
    Card(Modifier.fillMaxWidth().clip(HouseListItemShape(cornerRadius = LocalDensity.current.run { 24.dp.toPx() })).clickable { onViewHouse(house.url) }) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 8.dp)) {
            Text(text = house.name,style = MaterialTheme.typography.h6.copy(fontFamily = FontFamily.Serif))
            Text(text = house.region, style = MaterialTheme.typography.caption.copy(color = Color.White.copy(alpha = 0.8f),fontFamily = FontFamily.Serif))
        }
    }
}