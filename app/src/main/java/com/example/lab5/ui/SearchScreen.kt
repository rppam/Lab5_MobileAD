package com.example.lab5.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    departureAirportCodesAndNamesList: List<Pair<String, String>> = listOf(),
    onRowClick: (String) -> Unit = {}, // передаёт код выбранного аэропорта
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        items(departureAirportCodesAndNamesList) { item ->
            TextButton(onClick = { onRowClick(item.first) }) {
                SearchTextRow(item)
            }
        }
    }
}

@Composable
private fun SearchTextRow(
    departureAirportCodeAndName: Pair<String, String> = Pair("", ""),
    modifier: Modifier = Modifier
) {
    val color = Color(0, 102, 102)
    Row(modifier = modifier) {
        Text(
            text = departureAirportCodeAndName.first.uppercase(),
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = departureAirportCodeAndName.second,
            color = color
        )
    }
}