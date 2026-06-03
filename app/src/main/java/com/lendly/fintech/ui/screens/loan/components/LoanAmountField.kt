package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.R
import com.lendly.fintech.ui.theme.BorderNeutral
import com.lendly.fintech.ui.theme.ContentAmount
import com.lendly.fintech.ui.theme.Montserrat
import com.lendly.fintech.ui.theme.Spacing

private val AmountTextStyle = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    color = ContentAmount,
    textAlign = TextAlign.Center,
)

@Composable
fun LoanAmountField(
    amount: String,
    onAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.sm),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "₱",
                style = AmountTextStyle,
                modifier = Modifier.padding(end = 6.dp),
            )
            BasicTextField(
                value = amount,
                onValueChange = onAmountChange,
                textStyle = AmountTextStyle,
                cursorBrush = SolidColor(ContentAmount),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.widthIn(min = 64.dp),
                decorationBox = { inner ->
                    Box(contentAlignment = Alignment.Center) {
                        if (amount.isEmpty()) {
                            Text(
                                text = stringResource(R.string.loan_form_amount_placeholder),
                                style = AmountTextStyle.copy(
                                    color = ContentAmount.copy(alpha = 0.3f),
                                ),
                            )
                        }
                        inner()
                    }
                },
            )
        }
        HorizontalDivider(color = BorderNeutral, thickness = 1.dp)
    }
}
