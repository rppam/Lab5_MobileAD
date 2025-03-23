package com.example.lab5.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab5.data.RouteInfo
import com.example.lab5.ui.theme.Lab5Theme

@Composable
fun RouteInfoCard(
    routeInfo: RouteInfo,
    changeIsFavoriteButtonClick: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    val textColor : Color = Color(0, 102, 102)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(3.dp, color = Color(0, 102, 102), shape = RoundedCornerShape(8.dp))
            .background(Color(234, 255, 255))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Column() {
                Text(
                    text = "DEPART",
                    fontWeight = FontWeight.Bold
                )
                Row() {
                    Text(
                        text = routeInfo.departureAirportCode.uppercase(),
                        fontWeight = FontWeight.Black,
                        color = textColor
                    )
                    if (routeInfo.departureAirportName != null) {
                        Text(
                            text = routeInfo.departureAirportName,
                            modifier.padding(start = 8.dp).width(250.dp)
                        )
                    }
                }
                Text(
                    text = "ARRIVE",
                    fontWeight = FontWeight.Bold
                )
                Row() {
                    Text(
                        text = routeInfo.arrivalAirportCode.uppercase(),
                        fontWeight = FontWeight.Black,
                        color = textColor
                    )
                    if (routeInfo.arrivalAirportName != null) {
                        Text(
                            text = routeInfo.arrivalAirportName,
                            modifier.padding(start = 8.dp).width(250.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = changeIsFavoriteButtonClick
            ) {
                Icon(
                    imageVector = when (routeInfo.isFavorite) {
                        true -> Icons.Filled.Favorite
                        else -> Icons.Filled.FavoriteBorder
                    },
                    contentDescription = "Favorite",
                    tint = Color(0, 102, 102)
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412)
@Composable
fun RouteInfoCardPreview() {
    Lab5Theme {
        RouteInfoCard(
            RouteInfo(
                departureAirportCode = "Test",
                arrivalAirportCode = "Test",
                departureAirportName = "Test",
                arrivalAirportName = null,
                isFavorite = false
            )
        )
    }
}