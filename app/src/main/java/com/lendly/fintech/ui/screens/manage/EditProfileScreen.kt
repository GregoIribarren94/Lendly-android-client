package com.lendly.fintech.ui.screens.manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.inputs.AppTextField
import com.lendly.fintech.ui.components.navigation.AuthTopBar
import com.lendly.fintech.ui.theme.ContentPrimary
import com.lendly.fintech.ui.theme.ContentSecondary
import com.lendly.fintech.ui.theme.FormLabel
import com.lendly.fintech.ui.theme.Headline
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onSaved()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AuthTopBar(
                onBack = onBack,
                onInfo = {},
            )
        },
        bottomBar = {
            AuthBottomBar(
                text = stringResource(R.string.edit_profile_save),
                onClick = viewModel::saveProfile,
                enabled = state.isProfileComplete && !state.isSaving,
                isLoading = state.isSaving,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            Text(
                text = stringResource(R.string.profile_form_heading),
                style = Headline,
                color = ContentPrimary,
                modifier = Modifier.padding(top = Spacing.md),
            )

            LabeledEditProfileTextField(
                label = stringResource(R.string.profile_form_first_names_label),
                value = state.legalFirstNames,
                onValueChange = viewModel::onLegalFirstNamesChange,
                placeholder = stringResource(R.string.profile_form_first_names_placeholder),
            )

            LabeledEditProfileTextField(
                label = stringResource(R.string.profile_form_last_name_label),
                value = state.legalLastName,
                onValueChange = viewModel::onLegalLastNameChange,
                placeholder = stringResource(R.string.profile_form_last_name_placeholder),
            )

            DateOfBirthFields(
                label = stringResource(R.string.profile_form_birth_date_label),
                dayPlaceholder = "08",
                monthPlaceholder = "12",
                yearPlaceholder = "1997",
                selectedDay = state.birthDay,
                selectedMonth = state.birthMonth,
                selectedYear = state.birthYear,
                onDaySelected = viewModel::onBirthDayChange,
                onMonthSelected = viewModel::onBirthMonthChange,
                onYearSelected = viewModel::onBirthYearChange,
            )

            LabeledEditProfileTextField(
                label = stringResource(R.string.profile_form_address_label),
                value = state.address,
                onValueChange = viewModel::onAddressChange,
                placeholder = stringResource(R.string.profile_form_address_placeholder),
            )

            LabeledEditProfileTextField(
                label = stringResource(R.string.profile_form_city_label),
                value = state.city,
                onValueChange = viewModel::onCityChange,
                placeholder = stringResource(R.string.profile_form_city_placeholder),
            )

            LabeledEditProfileTextField(
                label = stringResource(R.string.profile_form_postal_code_label),
                value = state.postalCode,
                onValueChange = viewModel::onPostalCodeChange,
                placeholder = stringResource(R.string.profile_form_postal_code_placeholder),
                keyboardType = KeyboardType.Number,
            )

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                EditProfileFieldLabel(text = stringResource(R.string.profile_form_phone_label))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                ) {
                    Box(
                        modifier = Modifier.width(72.dp),
                    ) {
                        AppTextField(
                            value = "+${state.selectedCountry.dialCode}",
                            onValueChange = {},
                            label = "",
                            placeholder = "+${state.selectedCountry.dialCode}",
                            keyboardType = KeyboardType.Number,
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                        AppTextField(
                            value = state.phoneNumber,
                            onValueChange = viewModel::onPhoneNumberChange,
                            label = "",
                            placeholder = "991251255",
                            keyboardType = KeyboardType.Number,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LabeledEditProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        EditProfileFieldLabel(text = label)
        AppTextField(
            value = value,
            onValueChange = onValueChange,
            label = "",
            placeholder = placeholder,
            keyboardType = keyboardType,
        )
    }
}

@Composable
private fun EditProfileFieldLabel(text: String) {
    Text(
        text = text,
        style = FormLabel,
        color = ContentPrimary,
    )
}

@Composable
private fun DateOfBirthFields(
    label: String,
    dayPlaceholder: String,
    monthPlaceholder: String,
    yearPlaceholder: String,
    selectedDay: Int?,
    selectedMonth: Int?,
    selectedYear: Int?,
    onDaySelected: (Int?) -> Unit,
    onMonthSelected: (Int?) -> Unit,
    onYearSelected: (Int?) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        EditProfileFieldLabel(text = label)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                Text(text = "Day", style = FormLabel, color = ContentSecondary)
                DateInputField(
                    value = selectedDay,
                    placeholder = dayPlaceholder,
                    maxLength = 2,
                    onValueChange = onDaySelected,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                Text(text = "Month", style = FormLabel, color = ContentSecondary)
                DateInputField(
                    value = selectedMonth,
                    placeholder = monthPlaceholder,
                    maxLength = 2,
                    onValueChange = onMonthSelected,
                )
            }

            Column(
                modifier = Modifier.weight(1.4f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                Text(text = "Year", style = FormLabel, color = ContentSecondary)
                DateInputField(
                    value = selectedYear,
                    placeholder = yearPlaceholder,
                    maxLength = 4,
                    onValueChange = onYearSelected,
                )
            }
        }
    }
}

@Composable
private fun DateInputField(
    value: Int?,
    placeholder: String,
    maxLength: Int,
    onValueChange: (Int?) -> Unit,
) {
    val textValue = value?.toString().orEmpty()

    AppTextField(
        value = textValue,
        onValueChange = { newValue ->
            if (newValue.length <= maxLength && newValue.all { it.isDigit() }) {
                onValueChange(newValue.toIntOrNull())
            }
        },
        label = "",
        placeholder = placeholder,
        keyboardType = KeyboardType.Number,
    )
}
