package com.lendly.fintech.ui.screens.loan.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.lendly.fintech.R
import com.lendly.fintech.ui.screens.loan.LoanPurpose
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.TitleMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanPurposeDropdown(
    purposes: List<LoanPurpose>,
    selected: LoanPurpose?,
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    onSelect: (LoanPurpose) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange() },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selected?.let { stringResource(it.labelRes) }
                ?: stringResource(R.string.loan_form_purpose_placeholder),
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            textStyle = TitleMedium,
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_keyboard_arrow_down),
                    contentDescription = null,
                    tint = ContentPrimary,
                )
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedTextColor = ContentPrimary,
                unfocusedTextColor = if (selected == null) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else ContentPrimary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
        ) {
            purposes.forEach { purpose ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(purpose.labelRes),
                            style = TitleMedium,
                            color = ContentPrimary,
                        )
                    },
                    onClick = { onSelect(purpose) },
                )
            }
        }
    }
}
