package com.lendly.fintech.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.lendly.fintech.ui.components.buttons.AuthBottomBar
import com.lendly.fintech.ui.components.inputs.AppTextField
import com.lendly.fintech.ui.components.inputs.PhoneNumberInput
import com.lendly.fintech.ui.components.navigation.AuthTopBar
import com.lendly.fintech.ui.theme.ContentSecondary
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
            AuthTopBar(
                onBack = onBack,
                onInfo = {},
            )
        },
        bottomBar = {
            AuthBottomBar(
                text = stringResource(R.string.profile_form_next),
                onClick = onContinue,
                enabled = state.isProfileComplete,
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
                dayPlaceholder = "08",   // Ejemplo real
                monthPlaceholder = "12", // Ejemplo real
                yearPlaceholder = "1997",// Ejemplo real
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md) // Espaciado idéntico entre las cajas
                ) {
                    // Contenedor del código de país (+65)
                    Box(
                        modifier = Modifier.width(72.dp) // Ancho fijo y compacto como la captura
                    ) {
                        AppTextField(
                            value = "+65", // Si necesitas cambiarlo dinámicamente podés usar state.selectedCountry
                            onValueChange = { /* Si es editable o abre selector, va acá */ },
                            label = "",
                            placeholder = "+65",
                            keyboardType = KeyboardType.Number,
                        )
                    }

                    // Contenedor expandible para el número de celular
                    Box(
                        modifier = Modifier.weight(1f) // Toma de forma fluida todo el espacio sobrante
                    ) {
                        AppTextField(
                            value = state.phoneNumber,
                            onValueChange = viewModel::onPhoneNumberChange,
                            label = "",
                            placeholder = "991251255", // Ejemplo real que pusiste en la imagen anterior
                            keyboardType = KeyboardType.Number,
                        )
                    }
                }

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
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        ProfileFieldLabel(text = label)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            // Campo del DÍA
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                Text(text = "Day", style = MaterialTheme.typography.bodyMedium, color = ContentSecondary)
                DateInputField(
                    value = selectedDay,
                    placeholder = dayPlaceholder,
                    maxLength = 2,
                    onValueChange = onDaySelected
                )
            }

            // Campo del MES
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                Text(text = "Month", style = MaterialTheme.typography.bodyMedium, color = ContentSecondary)
                DateInputField(
                    value = selectedMonth,
                    placeholder = monthPlaceholder,
                    maxLength = 2,
                    onValueChange = onMonthSelected
                )
            }

            // Campo del AÑO (Más ancho como el diseño)
            Column(
                modifier = Modifier.weight(1.4f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                Text(text = "Year", style = MaterialTheme.typography.bodyMedium, color = ContentSecondary)
                DateInputField(
                    value = selectedYear,
                    placeholder = yearPlaceholder,
                    maxLength = 4,
                    onValueChange = onYearSelected
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
            // Filtramos para aceptar solo números y respetar el límite de caracteres (2 o 4 dígitos)
            if (newValue.length <= maxLength && newValue.all { it.isDigit() }) {
                // Si borra todo mandamos null, si no, lo convertimos a entero de forma segura
                onValueChange(newValue.toIntOrNull())
            }
        },
        label = "",
        placeholder = placeholder,
        keyboardType = KeyboardType.Number, // Abre directamente el teclado numérico
    )
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
