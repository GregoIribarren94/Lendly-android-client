package com.lendly.fintech.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lendly.fintech.R

enum class OtcPartner(
    @StringRes val nameRes: Int,
    @DrawableRes val logoRes: Int,
    val maxAmount: Double = 5_000.0,
) {
    SEVEN_ELEVEN(R.string.otc_partner_7eleven, R.drawable.ic_partner_7eleven),
    CEBUANA_LHUILLIER(R.string.otc_partner_cebuana, R.drawable.ic_partner_cebuana),
    LBC(R.string.otc_partner_lbc, R.drawable.ic_partner_lbc),
    M_LHUILLIER(R.string.otc_partner_m_lhuillier, R.drawable.ic_partner_m_lhuillier),
}

enum class OnlineMethodType {
    BANK,
    E_WALLET,
}

enum class OnlineMethod(
    @StringRes val nameRes: Int,
    @DrawableRes val logoRes: Int,
    val type: OnlineMethodType,
) {
    BPI(R.string.online_bank_bpi, R.drawable.ic_bank_bpi, OnlineMethodType.BANK),
    CHINABANK(R.string.online_bank_chinabank, R.drawable.ic_bank_chinabank, OnlineMethodType.BANK),
    RCBC(R.string.online_bank_rcbc, R.drawable.ic_bank_rcbc, OnlineMethodType.BANK),
    UNIONBANK(R.string.online_bank_unionbank, R.drawable.ic_bank_unionbank, OnlineMethodType.BANK),
    GCASH(R.string.online_ewallet_gcash, R.drawable.ic_ewallet_gcash, OnlineMethodType.E_WALLET),
    PAYMAYA(R.string.online_ewallet_paymaya, R.drawable.ic_ewallet_paymaya, OnlineMethodType.E_WALLET),
    PAYPAL(R.string.online_ewallet_paypal, R.drawable.ic_ewallet_paypal, OnlineMethodType.E_WALLET),
}

enum class PaymentMethodType(@StringRes val labelRes: Int) {
    TRANSFER(R.string.online_cash_in_form_transfer_label),
    CREDIT_CARD(R.string.online_cash_in_form_credit_card_label),
    DEBIT_CARD(R.string.online_cash_in_form_debit_card_label),
}
