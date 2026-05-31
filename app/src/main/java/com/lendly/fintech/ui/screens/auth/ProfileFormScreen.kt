package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lendly.fintech.R
import com.lendly.fintech.ui.components.buttons.PrimaryButton
import com.lendly.fintech.ui.components.inputs.AppTextField
import com.lendly.fintech.ui.components.inputs.PhoneNumberInput
import com.lendly.fintech.ui.components.navigation.AppTopBar
import com.lendly.fintech.ui.theme.Spacing
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun ProfileFormScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "",
                onBackClick = onBack,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(
                                R.string.profile_form_info_content_description
                            ),
                        )
                    }
                },
            )
        },
        bottomBar = {
            PrimaryButton(
                text = stringResource(R.string.profile_form_next),
                onClick = {
                    if (state.isProfileComplete) onContinue()
                },
                enabled = state.isProfileComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = Spacing.md, vertical = Spacing.md),
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
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = Spacing.md),
            )

            LabeledProfileTextField(
                label = stringResource(R.string.profile_form_first_names_label),
                value = state.legalFirstNames,
                onValueChange = viewModel::onLegalFirstNamesChange,
                placeholder = stringResource(R.string.profile_form_first_names_placeholder),
            )

            LabeledProfileTextField(
                label = stringResource(R.string.profile_form_last_name_label),
                value = state.legalLastName,
                onValueChange = viewModel::onLegalLastNameChange,
                placeholder = stringResource(R.string.profile_form_last_name_placeholder),
            )

            DateOfBirthFields(
                label = stringResource(R.string.profile_form_birth_date_label),
                dayPlaceholder = stringResource(R.string.profile_form_day_placeholder),
                monthPlaceholder = stringResource(R.string.profile_form_month_placeholder),
                yearPlaceholder = stringResource(R.string.profile_form_year_placeholder),
                selectedDay = state.birthDay,
                selectedMonth = state.birthMonth,
                selectedYear = state.birthYear,
                onDaySelected = viewModel::onBirthDayChange,
                onMonthSelected = viewModel::onBirthMonthChange,
                onYearSelected = viewModel::onBirthYearChange,
            )

            LabeledProfileTextField(
                label = stringResource(R.string.profile_form_address_label),
                value = state.address,
                onValueChange = viewModel::onAddressChange,
                placeholder = stringResource(R.string.profile_form_address_placeholder),
            )

            LabeledProfileTextField(
                label = stringResource(R.string.profile_form_city_label),
                value = state.city,
                onValueChange = viewModel::onCityChange,
                placeholder = stringResource(R.string.profile_form_city_placeholder),
            )

            LabeledProfileTextField(
                label = stringResource(R.string.profile_form_postal_code_label),
                value = state.postalCode,
                onValueChange = viewModel::onPostalCodeChange,
                placeholder = stringResource(R.string.profile_form_postal_code_placeholder),
                keyboardType = KeyboardType.Number,
            )

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                ProfileFieldLabel(text = stringResource(R.string.profile_form_phone_label))
                PhoneNumberInput(
                    phoneNumber = state.phoneNumber,
                    onPhoneNumberChange = viewModel::onPhoneNumberChange,
                    selectedCountry = state.selectedCountry,
                    onCountrySelected = viewModel::onCountrySelected,
                    label = "",
                )
            }
        }
    }
}

@Composable
private fun LabeledProfileTextField(
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
        ProfileFieldLabel(text = label)
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
private fun ProfileFieldLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground,
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
    val currentYear = remember { LocalDate.now().year }
    val days = remember(selectedMonth, selectedYear) {
        val dayCount = if (selectedMonth != null && selectedYear != null) {
            YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
        } else {
            31
        }
        (1..dayCount).toList()
    }
    val months = remember { (1..12).toList() }
    val years = remember(currentYear) { (currentYear downTo 1900).toList() }

    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        ProfileFieldLabel(text = label)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            DateDropdownField(
                selectedValue = selectedDay,
                placeholder = dayPlaceholder,
                options = days,
                onValueSelected = onDaySelected,
                modifier = Modifier.weight(1f),
            )
            DateDropdownField(
                selectedValue = selectedMonth,
                placeholder = monthPlaceholder,
                options = months,
                onValueSelected = onMonthSelected,
                modifier = Modifier.weight(1f),
            )
            DateDropdownField(
                selectedValue = selectedYear,
                placeholder = yearPlaceholder,
                options = years,
                onValueSelected = onYearSelected,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateDropdownField(
    selectedValue: Int?,
    placeholder: String,
    options: List<Int>,
    onValueSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selectedValue?.toString().orEmpty(),
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            placeholder = { Text(text = placeholder) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.toString()) },
                    onClick = {
                        onValueSelected(option)
                        expanded = false
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                )
            }
        }
    }
}
