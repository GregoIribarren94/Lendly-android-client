package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.theme.ButtonLabel
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun LoanFormTopBar(
    onBack: () -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = Spacing.xs),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_alt),
                    contentDescription = stringResource(R.string.loan_form_back_content_description),
                    tint = ContentPrimary,
                    modifier = Modifier.size(20.dp),
                )
            }
            Box(modifier = Modifier.weight(1f))
            IconButton(onClick = onInfoClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = stringResource(R.string.loan_form_info_content_description),
                    tint = ContentPrimary,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
        Text(
            text = stringResource(R.string.loan_form_top_bar_title),
            style = ButtonLabel,
            color = ContentPrimary,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
