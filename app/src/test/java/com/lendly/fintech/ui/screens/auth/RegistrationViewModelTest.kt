package com.lendly.fintech.ui.screens.auth

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RegistrationViewModelTest {

    @Test
    fun `profile is incomplete initially`() {
        val viewModel = RegistrationViewModel()

        assertFalse(viewModel.uiState.value.isProfileComplete)
    }

    @Test
    fun `profile remains incomplete when required field is blank`() {
        val viewModel = RegistrationViewModel()

        fillValidProfile(viewModel)
        viewModel.onLegalFirstNamesChange("   ")

        assertFalse(viewModel.uiState.value.isProfileComplete)
    }

    @Test
    fun `profile is complete when all required fields and valid date are present`() {
        val viewModel = RegistrationViewModel()

        fillValidProfile(viewModel)

        assertTrue(viewModel.uiState.value.isProfileComplete)
    }

    @Test
    fun `future birth date is incomplete`() {
        val viewModel = RegistrationViewModel()
        val tomorrow = LocalDate.now().plusDays(1)

        fillValidProfile(viewModel)
        viewModel.onBirthDayChange(tomorrow.dayOfMonth)
        viewModel.onBirthMonthChange(tomorrow.monthValue)
        viewModel.onBirthYearChange(tomorrow.year)

        assertFalse(viewModel.uiState.value.isProfileComplete)
    }

    @Test
    fun `changing month clears invalid selected day`() {
        val viewModel = RegistrationViewModel()

        viewModel.onBirthYearChange(2020)
        viewModel.onBirthMonthChange(1)
        viewModel.onBirthDayChange(31)
        viewModel.onBirthMonthChange(2)

        assertNull(viewModel.uiState.value.birthDay)
    }

    @Test
    fun `profile details derive iso date and phone with country prefix`() {
        val viewModel = RegistrationViewModel()

        fillValidProfile(viewModel)
        val details = viewModel.uiState.value.profileDetails

        requireNotNull(details)
        assertEquals("John D.", details.legalFirstNames)
        assertEquals("Doe", details.legalLastName)
        assertEquals("1990-05-12", details.birthDate)
        assertEquals("Somewhere IN BLOCK 12", details.address)
        assertEquals("Davao City", details.city)
        assertEquals("8000", details.postalCode)
        assertEquals("+5411 2345 6789", details.phoneNumber)
    }

    private fun fillValidProfile(viewModel: RegistrationViewModel) {
        viewModel.onLegalFirstNamesChange(" John D. ")
        viewModel.onLegalLastNameChange(" Doe ")
        viewModel.onBirthDayChange(12)
        viewModel.onBirthMonthChange(5)
        viewModel.onBirthYearChange(1990)
        viewModel.onAddressChange(" Somewhere IN BLOCK 12 ")
        viewModel.onCityChange(" Davao City ")
        viewModel.onPostalCodeChange(" 8000 ")
        viewModel.onPhoneNumberChange("11 2345 6789")
    }
}
