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
    monthlyFee: String? = null,
    interest: String? = null,
    installments: String? = null,
    onDone: () -> Unit,
) {
    val dateTimeLabel = stringResource(R.string.success_tx_detail_datetime)
    val amountLabel = stringResource(R.string.success_tx_detail_amount)
    val txNumberLabel = stringResource(R.string.success_tx_detail_tx_number)
    val txNumberFullLabel = stringResource(R.string.success_tx_detail_tx_number_full)
    val monthlyFeeLabel = stringResource(R.string.success_tx_detail_monthly_fee)
    val interestLabel = stringResource(R.string.success_tx_detail_interest)
    val installmentLabel = stringResource(R.string.success_tx_detail_installment_plan)
    val now = remember {
        SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(Date())
    }

    // El flujo de préstamo aporta cuota/interés/plan; sin ellos, es un cash-in común.
    val isLoan = monthlyFee != null && interest != null && installments != null

    val details = buildList {
        if (isLoan) {
            add(SuccessTxDetail(label = monthlyFeeLabel, value = monthlyFee!!))
            add(SuccessTxDetail(label = interestLabel, value = interest!!))
            add(SuccessTxDetail(label = installmentLabel, value = installments!!))
            add(SuccessTxDetail(label = dateTimeLabel, value = now))
            if (referenceCode != null) {
                add(SuccessTxDetail(label = txNumberFullLabel, value = "#$referenceCode", isLink = true))
            }
        } else {
            add(SuccessTxDetail(label = dateTimeLabel, value = now))
            if (amount.isNotEmpty()) {
                add(SuccessTxDetail(label = amountLabel, value = amount))
            }
            if (referenceCode != null) {
                add(SuccessTxDetail(label = txNumberLabel, value = referenceCode, isLink = true))
            }
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
        tag = stringResource(
            if (isLoan) R.string.success_tx_tag_loan else R.string.success_tx_tag_cash_in,
        ),
        illustration = Icons.Filled.Add,
        needHelpText = stringResource(R.string.success_tx_need_help),
        helpLinkText = stringResource(R.string.success_tx_help_center),
        onHelp = {},
        onClose = onDone,
    )
}
