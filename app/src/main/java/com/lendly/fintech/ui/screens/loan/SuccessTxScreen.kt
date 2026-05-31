package com.lendly.fintech.ui.screens.loan

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.feedback.SuccessfulTransaction
import com.lendly.fintech.ui.components.feedback.SuccessTxDetail
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SuccessTxScreen(
    referenceCode: String? = null,
    amount: String = "",
    method: String = "",
    onDone: () -> Unit,
) {
    val dateTimeLabel = stringResource(R.string.success_tx_detail_datetime)
    val txNumberLabel = stringResource(R.string.success_tx_detail_tx_number)
    val now = remember {
        SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(Date())
    }

    val details = buildList {
        add(SuccessTxDetail(label = dateTimeLabel, value = now))
        if (referenceCode != null) {
            add(SuccessTxDetail(label = txNumberLabel, value = referenceCode, isLink = true))
        }
    }

    SuccessfulTransaction(
        title = stringResource(R.string.success_tx_title_cash_in),
        amount = amount,
        details = details,
        sectionTitle = stringResource(R.string.success_tx_details_heading),
        doneButtonText = stringResource(R.string.success_tx_done_button),
        onDone = onDone,
        subtitle = method.ifEmpty { null }?.let { stringResource(R.string.success_tx_from, it) },
        tag = stringResource(R.string.success_tx_tag_cash_in),
        illustration = Icons.Filled.Add,
        needHelpText = stringResource(R.string.success_tx_need_help),
        helpLinkText = stringResource(R.string.success_tx_help_center),
        onHelp = {},
        onClose = onDone,
    )
}
