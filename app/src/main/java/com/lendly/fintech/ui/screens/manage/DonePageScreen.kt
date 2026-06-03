package com.lendly.fintech.ui.screens.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.theme.BackgroundCircleNeutral
import com.lendly.fintech.ui.theme.BackgroundScreen
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.DisplayTitle
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.OnboardingSubtitle
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun DonePageScreen(
    onDone: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundScreen,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = onDone,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(Spacing.md)
                    .align(Alignment.TopStart)
                    .size(40.dp)
                    .background(color = BackgroundCircleNeutral, shape = CircleShape),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(R.string.done_page_close_content_description),
                    tint = ContentPrimary,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = Spacing.lg),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.illu_check_mark),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentScale = ContentScale.Fit,
                )

                Spacer(modifier = Modifier.height(Spacing.xl))

                Text(
                    text = stringResource(R.string.done_page_title),
                    style = DisplayTitle,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(Spacing.md))

                Text(
                    text = stringResource(R.string.done_page_subtitle),
                    style = OnboardingSubtitle,
                    color = ContentSecondary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            AuthBottomBar(
                text = stringResource(R.string.done_page_button),
                onClick = onDone,
                showDivider = false,
                containerColor = BackgroundScreen,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Preview(name = "DonePage", showBackground = true)
@Composable
private fun DonePageScreenPreview() {
    LendlyTheme {
        DonePageScreen(onDone = {})
    }
}
