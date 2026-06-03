package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.navigation.AuthTopBar

@Composable
fun SmsVerificationScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit,
) {
    var code by remember { mutableStateOf("254444") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFCF8F8),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AuthTopBar(
                    onBack = onBack,
                    onInfo = { },
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = "Enter the code",
                        color = Color(0xFF202124),
                        fontSize = 22.sp,
                        lineHeight = 27.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Enter the security code we sent to",
                        color = Color(0xFF4F4F4F),
                        fontSize = 13.sp,
                        lineHeight = 17.sp,
                    )

                    Text(
                        text = "*******731",
                        color = Color(0xFF4F4F4F),
                        fontSize = 13.sp,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Your Phone Number",
                        color = Color(0xFF202124),
                        fontSize = 12.sp,
                        lineHeight = 15.sp,
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                focusRequester.requestFocus()
                            }
                    ) {
                        BasicTextField(
                            value = code,
                            onValueChange = { value ->
                                code = value.filter { it.isDigit() }.take(6)

                                if (code.length == 6) {
                                    focusManager.clearFocus()
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Transparent,
                                fontSize = 1.sp,
                            ),
                            cursorBrush = SolidColor(Color.Transparent),
                            modifier = Modifier
                                .size(1.dp)
                                .focusRequester(focusRequester),
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 6.dp,
                                alignment = Alignment.CenterHorizontally,
                            ),
                        ) {
                            repeat(6) { index ->
                                OtpDigitBox(
                                    digit = code.getOrNull(index)?.toString().orEmpty(),
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(44.dp),
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    TextButton(
                        onClick = { },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(24.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            text = "Didn’t received a code?",
                            color = Color(0xFF006B4F),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                }
            }

            AuthBottomBar(
                text = "Next",
                onClick = onContinue,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun OtpDigitBox(
    digit: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, Color(0xFFB8B8B8)),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = digit,
                color = Color(0xFF4F4F4F),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}