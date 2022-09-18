package com.freelancemarcel.zatec.houses.models

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun ListItemHouse(house: HouseListItem, onViewHouse: (String) -> Unit){
    Card(Modifier.fillMaxWidth().clickable { onViewHouse(house.url) }) {
        Column(Modifier.fillMaxWidth().padding(8.dp)) {
            Text(text = house.name,style = MaterialTheme.typography.h6.copy(fontFamily = FontFamily.Serif))
            Spacer(Modifier.height(8.dp))
            Text(text = house.region, style = MaterialTheme.typography.caption.copy(color = Color.White.copy(alpha = 0.8f),fontFamily = FontFamily.Serif))
        }
    }
}