package com.freelancemarcel.zatec.houses.models

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListItemHouse(house: HouseListItem){
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth().padding(8.dp)) {
            Text(text = house.name)
            Spacer(Modifier.height(8.dp))
            Text(text = house.region, style = MaterialTheme.typography.caption)
        }
    }
}