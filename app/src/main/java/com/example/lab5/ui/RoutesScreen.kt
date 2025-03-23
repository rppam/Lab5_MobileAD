package com.example.lab5.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab5.data.RouteInfo

@Composable
fun RoutesScreen(
    departureAirportCode: String,
    routes: List<RouteInfo> = listOf(),
    changeIsFavoriteButtonClick: (RouteInfo) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Flights from " + departureAirportCode.uppercase(),
            color = Color(0, 102, 102),
            fontWeight = FontWeight.Black,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp).padding(horizontal = 16.dp)
        )

        LazyColumn(Modifier.padding(bottom = 64.dp)) {
            items(routes) { routeInfo ->
                RouteInfoCard(
                    routeInfo = routeInfo,
                    changeIsFavoriteButtonClick = { changeIsFavoriteButtonClick(routeInfo) }
                )
            }
        }
    }
}