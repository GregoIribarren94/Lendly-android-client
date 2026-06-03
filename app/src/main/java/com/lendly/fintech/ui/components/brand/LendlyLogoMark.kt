package com.lendly.fintech.ui.components.brand

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R

@Composable
fun LendlyLogoMark(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(width = 80.dp, height = 28.dp),
        contentAlignment = Alignment.TopStart,
    ) {
        Image(
            painter = painterResource(R.drawable.logo_rectangle_5),
            contentDescription = null,
            modifier = Modifier
                .size(width = 70.dp, height = 15.dp)
                .offset(x = 10.dp, y = 12.dp),
        )
        Image(
            painter = painterResource(R.drawable.logo_rectangle_4),
            contentDescription = null,
            modifier = Modifier
                .size(width = 70.dp, height = 15.dp)
                .offset(x = 5.dp, y = 6.dp),
        )
        Image(
            painter = painterResource(R.drawable.logo_rectangle_3),
            contentDescription = "Lendly Logo",
            modifier = Modifier.size(width = 70.dp, height = 15.dp),
        )
    }
}
