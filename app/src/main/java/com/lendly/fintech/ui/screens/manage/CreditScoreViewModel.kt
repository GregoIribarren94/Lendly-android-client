package com.lendly.fintech.ui.screens.manage

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.R
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreditScoreUiState(
    val score: Int? = null,
    @StringRes val labelRes: Int = R.string.credit_score_label_good,
)

@HiltViewModel
class CreditScoreViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreditScoreUiState())
    val uiState: StateFlow<CreditScoreUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching { userRepository.getProfile(CURRENT_USER_ID) }
                .onSuccess { res ->
                    if (res is Resource.Success) {
                        val score = res.data.creditScore
                        _uiState.value = CreditScoreUiState(
                            score = score,
                            labelRes = labelForScore(score),
                        )
                    }
                }
        }
    }

    private fun labelForScore(score: Int): Int = when {
        score < 580 -> R.string.credit_score_label_poor
        score < 670 -> R.string.credit_score_label_fair
        score < 740 -> R.string.credit_score_label_good
        score < 800 -> R.string.credit_score_label_very_good
        else -> R.string.credit_score_label_excellent
    }

    private companion object {
        const val CURRENT_USER_ID = "1"
    }
}
