package com.example.lab5.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab5.R
import com.example.lab5.data.RouteInfo
import com.example.lab5.ui.theme.Lab5Theme

@Composable
fun FavoritesScreen(
    favorites: List<RouteInfo> = listOf(),
    changeIsFavoriteButtonClick: (RouteInfo) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Favorite routes",
            color = Color(0, 102, 102),
            fontWeight = FontWeight.Black,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp).padding(horizontal = 16.dp)
        )

        LazyColumn(Modifier.padding(bottom = 64.dp)) {
            items(favorites) { routeInfo ->
                RouteInfoCard(
                    routeInfo = routeInfo,
                    changeIsFavoriteButtonClick = { changeIsFavoriteButtonClick(routeInfo) }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    heightDp = 915,
    widthDp = 412
)
@Composable
fun FavoritesScreenPreview() {
    Lab5Theme {
        FavoritesScreen(
            favorites = listOf(
                RouteInfo("test", "test"),
                RouteInfo("test", "test", isFavorite = true),
                RouteInfo("test", "test"),
                RouteInfo("test", "test"),
                RouteInfo("test", "test"),
                RouteInfo("test", "test"),
                RouteInfo("test", "test"),
            )
        )
    }
}