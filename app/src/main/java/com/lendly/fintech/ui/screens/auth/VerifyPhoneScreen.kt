package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.navigation.AuthTopBar

@Composable
fun VerifyPhoneScreen(
    onBack: () -> Unit,
    onInfo: () -> Unit,
    onSendCode: (countryCode: String, phoneNumber: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var countryCode by remember { mutableStateOf("+65") }
    var phoneNumber by remember { mutableStateOf("991251255") }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFFCF8F8),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                AuthTopBar(
                    onBack = onBack,
                    onInfo = onInfo,
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Verify your phone\nnumber with a code",
                        color = Color(0xFF202124),
                        fontSize = 22.sp,
                        lineHeight = 27.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "We will send you a One-Time-Password\n(OTP) to confirm you number.",
                        color = Color(0xFF666666),
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Text(
                        text = "Your Phone Number",
                        color = Color(0xFF4A4A4A),
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        PhoneInputBox(
                            value = countryCode,
                            onValueChange = { countryCode = it },
                            modifier = Modifier
                                .width(61.dp)
                                .height(50.dp),
                        )

                        PhoneInputBox(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                        )
                    }
                }
            }

            AuthBottomBar(
                text = "Send Code",
                onClick = { onSendCode(countryCode, phoneNumber) },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun PhoneInputBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, Color(0xFF969696)),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFF5A5A5A),
                fontSize = 13.sp,
                lineHeight = 18.sp,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 13.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    innerTextField()
                }
            },
        )
    }
}