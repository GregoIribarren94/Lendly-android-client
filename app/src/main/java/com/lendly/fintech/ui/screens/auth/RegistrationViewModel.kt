package com.lendly.fintech.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.lendly.fintech.ui.components.inputs.Country
import com.lendly.fintech.ui.components.inputs.defaultCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DateTimeException
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileDetails(
    val legalFirstNames: String,
    val legalLastName: String,
    val birthDate: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val phoneNumber: String,
)

data class RegistrationUiState(
    val legalFirstNames: String = "",
    val legalLastName: String = "",
    val birthDay: Int? = null,
    val birthMonth: Int? = null,
    val birthYear: Int? = null,
    val address: String = "",
    val city: String = "",
    val postalCode: String = "",
    val phoneNumber: String = "",
    val selectedCountry: Country = defaultCountries.first(),
) {
    val isProfileComplete: Boolean
        get() = requiredTextFieldsComplete && selectedBirthDateOrNull() != null

    val profileDetails: ProfileDetails?
        get() {
            val birthDate = selectedBirthDateOrNull() ?: return null
            if (!requiredTextFieldsComplete) return null

            return ProfileDetails(
                legalFirstNames = legalFirstNames.trim(),
                legalLastName = legalLastName.trim(),
                birthDate = birthDate.toString(),
                address = address.trim(),
                city = city.trim(),
                postalCode = postalCode.trim(),
                phoneNumber = "+${selectedCountry.dialCode}${phoneNumber.trim()}",
            )
        }

    private val requiredTextFieldsComplete: Boolean
        get() = listOf(
            legalFirstNames,
            legalLastName,
            address,
            city,
            postalCode,
            phoneNumber,
        ).all { it.trim().isNotEmpty() }

    fun selectedBirthDateOrNull(today: LocalDate = LocalDate.now()): LocalDate? {
        val day = birthDay ?: return null
        val month = birthMonth ?: return null
        val year = birthYear ?: return null

        return try {
            LocalDate.of(year, month, day).takeIf { !it.isAfter(today) }
        } catch (_: DateTimeException) {
            null
        }
    }
}

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun onLegalFirstNamesChange(value: String) {
        _uiState.update { it.copy(legalFirstNames = value) }
    }

    fun onLegalLastNameChange(value: String) {
        _uiState.update { it.copy(legalLastName = value) }
    }

    fun onBirthDayChange(value: Int?) {
        _uiState.update { it.copy(birthDay = value) }
    }

    fun onBirthMonthChange(value: Int?) {
        _uiState.update { state ->
            state.copy(birthMonth = value).clearInvalidBirthDay()
        }
    }

    fun onBirthYearChange(value: Int?) {
        _uiState.update { state ->
            state.copy(birthYear = value).clearInvalidBirthDay()
        }
    }

    fun onAddressChange(value: String) {
        _uiState.update { it.copy(address = value) }
    }

    fun onCityChange(value: String) {
        _uiState.update { it.copy(city = value) }
    }

    fun onPostalCodeChange(value: String) {
        _uiState.update { it.copy(postalCode = value) }
    }

    fun onPhoneNumberChange(value: String) {
        _uiState.update { it.copy(phoneNumber = value) }
    }

    fun onCountrySelected(country: Country) {
        _uiState.update { it.copy(selectedCountry = country) }
    }

    private fun RegistrationUiState.clearInvalidBirthDay(): RegistrationUiState {
        val day = birthDay ?: return this
        val month = birthMonth ?: return this
        val year = birthYear ?: return this

        val isValidDay = try {
            LocalDate.of(year, month, day)
            true
        } catch (_: DateTimeException) {
            false
        }

        return if (isValidDay) this else copy(birthDay = null)
    }
}
