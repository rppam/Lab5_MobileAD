package com.example.lab5.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab5.R

@Composable
fun AppBar(
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(128.dp)
            .background(Color(234, 255, 255))
    ) {
        Text(
            text = stringResource(R.string.flight_search),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0, 102, 102),
            modifier = Modifier.padding(start = 32.dp).padding(bottom = 8.dp),
        )
        Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(Color(0, 102, 102))) {

        }
    }
}