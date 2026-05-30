package com.lendly.fintech.ui.components.inputs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.lendly.fintech.ui.theme.LendlyTheme
import com.lendly.fintech.ui.theme.Spacing

@Composable
fun PhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    selectedCountry: Country,
    onCountrySelected: (Country) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        TextButton(onClick = { expanded = true }) {
            Text(text = "${selectedCountry.flagEmoji} +${selectedCountry.dialCode}")
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                defaultCountries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text("${country.flagEmoji} ${country.name} (+${country.dialCode})") },
                        onClick = {
                            onCountrySelected(country)
                            expanded = false
                        },
                    )
                }
            }
        }

        AppTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = "Teléfono",
            keyboardType = KeyboardType.Phone,
            isError = isError,
            errorMessage = errorMessage,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(name = "PhoneInput - Empty")
@Composable
private fun PhoneInputEmptyPreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            PhoneNumberInput(
                phoneNumber = "",
                onPhoneNumberChange = {},
                selectedCountry = defaultCountries.first(),
                onCountrySelected = {},
            )
        }
    }
}

@Preview(name = "PhoneInput - With value")
@Composable
private fun PhoneInputWithValuePreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            PhoneNumberInput(
                phoneNumber = "11 2345 6789",
                onPhoneNumberChange = {},
                selectedCountry = defaultCountries.first(),
                onCountrySelected = {},
            )
        }
    }
}

@Preview(name = "PhoneInput - Error")
@Composable
private fun PhoneInputErrorPreview() {
    LendlyTheme {
        Surface(modifier = Modifier.padding(Spacing.md)) {
            PhoneNumberInput(
                phoneNumber = "11 2345",
                onPhoneNumberChange = {},
                selectedCountry = defaultCountries.first(),
                onCountrySelected = {},
                isError = true,
                errorMessage = "El número de teléfono no es válido",
            )
        }
    }
}
