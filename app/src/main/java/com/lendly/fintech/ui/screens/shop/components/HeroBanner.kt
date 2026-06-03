package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.ui.theme.*

@Composable
fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md)
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(colors = listOf(Color(0xFF122300), Color(0xFF2A5200)))),
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart).padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "The New Shoes", style = SectionTitle, color = BaseContrast, fontSize = 20.sp)
            Text(text = "Shop this season's\nTop Silhouette", style = Body, color = Color(0xFFB1D18A), fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(InteractiveAccent).clickable { }.padding(horizontal = 18.dp, vertical = 8.dp),
            ) {
                Text(text = "Shop Now", style = BodyEmphasis, color = ContentPrimary, fontSize = 13.sp)
            }
        }
        Text(text = "👟", fontSize = 72.sp, modifier = Modifier.align(Alignment.CenterEnd).padding(end = Spacing.md))
        Row(
            modifier = Modifier.align(Alignment.BottomStart).padding(start = Spacing.md, bottom = Spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            repeat(3) { i ->
                Box(modifier = Modifier.size(if (i == 0) 16.dp else 6.dp, 6.dp).clip(CircleShape).background(if (i == 0) InteractiveAccent else BaseContrast.copy(alpha = 0.4f)))
            }
        }
    }
}
