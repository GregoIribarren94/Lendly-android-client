package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.R
import com.lendly.fintech.ui.theme.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun HeroBanner() {
    val heroShape = RoundedCornerShape(16.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md)
            .height(250.dp)
            .clip(heroShape) // Esto recorta todo lo que sobresalga del hero
            .background(ContentAmount),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = Spacing.lg, top = Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "The New Shoes",
                style = SectionTitle,
                color = BaseContrast,
                fontSize = 28.sp,
                lineHeight = 34.sp
            )

            Text(
                text = "Shop this season's Top Silhouette",
                style = Body,
                color = BaseContrast,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(118.dp)
                    .height(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(InteractiveAccent)
                    .clickable { },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Shop Now",
                    style = ButtonLabel,
                    color = ContentAmount
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 5.dp, y = 21.dp) // Podés mantener la posición original
                .size(118.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(
                        topStart = 19.dp,
                        topEnd = 19.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 11.dp
                    ),
                    clip = false
                )
                .background(
                    color = InteractiveAccent,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 19.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 11.dp
                    )
                ),
        )

// Sombra de las zapatillas
        Image(
            painter = painterResource(R.drawable.img_shop_shoes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.55f),
                blendMode = BlendMode.SrcIn
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp)
                .offset(x = (-2).dp, y = 17.dp)
                .size(width = 209.dp, height = 188.dp)
                .blur(
                    radius = 8.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                )
        )

// Zapatillas reales
        Image(
            painter = painterResource(R.drawable.img_shop_shoes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp)
                .offset(y = 11.dp)
                .size(width = 209.dp, height = 188.dp),
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Spacing.lg, bottom = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(BaseContrast)
                )
            }
        }
    }
}