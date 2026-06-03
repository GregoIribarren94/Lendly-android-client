package com.lendly.fintech.ui.screens.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.model.UpdateUserRequest
import com.lendly.fintech.data.repository.UserRepository
import com.lendly.fintech.ui.components.inputs.Country
import com.lendly.fintech.ui.components.inputs.defaultCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DateTimeException
import java.time.LocalDate
import javax.inject.Inject

data class EditProfileUiState(
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
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null,
) {
    val isProfileComplete: Boolean
        get() = requiredTextFieldsComplete && selectedBirthDateOrNull() != null

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

    fun toUpdateRequest(): UpdateUserRequest? {
        val birthDate = selectedBirthDateOrNull() ?: return null
        if (!requiredTextFieldsComplete) return null

        return UpdateUserRequest(
            name = legalFirstNames.trim(),
            lastName = legalLastName.trim(),
            birthDate = birthDate.toString(),
            address = address.trim(),
            city = city.trim(),
            postalCode = postalCode.trim(),
            phone = "+${selectedCountry.dialCode}${phoneNumber.trim()}",
        )
    }
}

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            runCatching { userRepository.getProfile(CURRENT_USER_ID) }
                .onSuccess { result ->
                    if (result is Resource.Success) {
                        val user = result.data
                        _uiState.update { state ->
                            state.copy(
                                legalFirstNames = user.name,
                                legalLastName = user.lastName,
                                phoneNumber = user.phone.removePrefix("+${state.selectedCountry.dialCode}"),
                                isLoading = false,
                            )
                        }
                    } else {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

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

    fun saveProfile() {
        val request = _uiState.value.toUpdateRequest() ?: return
        _uiState.update { it.copy(isSaving = true, errorMessage = null) }

        viewModelScope.launch {
            runCatching { userRepository.updateProfile(CURRENT_USER_ID, request) }
                .onSuccess { result ->
                    if (result is Resource.Success) {
                        _uiState.update { it.copy(isSaving = false, isSaved = true) }
                    } else {
                        _uiState.update { it.copy(isSaving = false) }
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isSaving = false) }
                }
        }
    }

    private fun EditProfileUiState.clearInvalidBirthDay(): EditProfileUiState {
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

    private companion object {
        const val CURRENT_USER_ID = "1"
    }
}
