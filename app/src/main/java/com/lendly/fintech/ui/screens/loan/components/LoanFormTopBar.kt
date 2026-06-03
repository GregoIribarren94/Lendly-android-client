package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.navigation.LendlyTopBar
import com.lendly.fintech.ui.theme.ButtonLabel
import com.lendly.fintech.ui.theme.ContentPrimary

@Composable
fun LoanFormTopBar(
    onBack: () -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LendlyTopBar(
        modifier = modifier,
        leading = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_alt),
                    contentDescription = stringResource(R.string.loan_form_back_content_description),
                    tint = ContentPrimary,
                    modifier = Modifier.size(20.dp),
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.loan_form_top_bar_title),
                style = ButtonLabel,
                color = ContentPrimary,
            )
        },
        actions = {
            IconButton(onClick = onInfoClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = stringResource(R.string.loan_form_info_content_description),
                    tint = ContentPrimary,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
    )
}
