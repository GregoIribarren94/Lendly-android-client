package com.lendly.fintech.ui.navigation

import android.net.Uri

object Routes {
    // Root flow (sin BottomBar)
    const val SPLASH = "splash"
    const val ONBOARDING_1 = "onboarding1"
    const val ONBOARDING_2 = "onboarding2"
    const val ONBOARDING_3 = "onboarding3"
    const val LOGIN = "login"
    // Sub-graph que agrupa el flujo de auth/registro y permite compartir el RegistrationViewModel.
    const val AUTH_GRAPH = "authGraph"
    const val VERIFY_PHONE = "verifyPhone"
    const val SMS_VERIFICATION = "smsVerification"
    const val PROFILE_FORM = "profileForm"
    const val CREATE_PASSWORD = "createPassword"
    const val DONE = "done"
    const val MAIN = "main" // Contenedor del MainNavHost con BottomNav

    // Main tabs (BottomNav)
    const val HOME = "home"
    const val LOAN = "loan"
    const val SHOP = "shop"
    const val HISTORY = "history"
    const val MANAGE = "manage"

    // Sub-rutas dentro de tabs
    const val CASH_IN = "cashIn"
    const val OTC_CASH_IN = "otcCashIn"
    const val ONLINE_CASH_IN = "onlineCashIn"
    const val SUCCESS_TX = "successTx"
    const val OTC_CASH_IN_FORM = "otcCashInForm/{partnerId}"
    const val ONLINE_CASH_IN_FORM = "onlineCashInForm/{methodId}"
    const val LOAN_INFO = "loanInfo"
    const val LOAN_FORM = "loanForm"
    const val ACTIVE_LOAN = "activeLoan"
    const val SEARCH = "search"
    const val PROFILE = "profile"
    const val PROFILE_DETAIL_BASE = "profileDetail"
    const val CREDIT_SCORE = "creditScore"
    const val PRODUCT_BASE = "product"
    const val TX_DETAILS_BASE = "txDetails"
    const val FILTER = "filter"

    // Rutas con argumentos
    const val PRODUCT = "$PRODUCT_BASE/{id}"
    const val TX_DETAILS = "$TX_DETAILS_BASE/{id}"
    const val PROFILE_DETAIL = "$PROFILE_DETAIL_BASE/{id}"

    // Helpers para navegar a rutas con argumentos
    fun product(id: String) = "$PRODUCT_BASE/$id"
    fun txDetails(id: String) = "$TX_DETAILS_BASE/$id"
    fun profileDetail(id: String) = "$PROFILE_DETAIL_BASE/$id"
    fun otcCashInForm(partnerId: String) = "otcCashInForm/$partnerId"
    fun onlineCashInForm(methodId: String) = "onlineCashInForm/$methodId"
    fun successTxWithRef(referenceCode: String, amount: String = "", method: String = "") =
        "$SUCCESS_TX?$ARG_REF_CODE=$referenceCode&$ARG_AMOUNT=${Uri.encode(amount)}&$ARG_METHOD=${Uri.encode(method)}"

    fun successTxOnline(amount: String, method: String) =
        "$SUCCESS_TX?$ARG_AMOUNT=${Uri.encode(amount)}&$ARG_METHOD=${Uri.encode(method)}"

    // Argument keys
    const val ARG_ID = "id"
    const val ARG_PARTNER_ID = "partnerId"
    const val ARG_METHOD_ID = "methodId"
    const val ARG_REF_CODE = "referenceCode"
    const val ARG_AMOUNT = "amount"
    const val ARG_METHOD = "method"
}
