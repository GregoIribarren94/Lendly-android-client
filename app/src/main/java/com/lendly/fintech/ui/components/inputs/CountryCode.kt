package com.lendly.fintech.ui.components.inputs

data class Country(
    val isoCode: String,
    val name: String,
    val dialCode: Int,
    val flagEmoji: String,
)

fun isoCodeToFlagEmoji(isoCode: String): String {
    return isoCode.uppercase().map { char ->
        Character.toCodePoint('\uD83C', '\uDDE6' + (char - 'A'))
    }.joinToString("") { codePoint ->
        String(Character.toChars(codePoint))
    }
}

private fun country(isoCode: String, name: String, dialCode: Int): Country =
    Country(isoCode = isoCode, name = name, dialCode = dialCode, flagEmoji = isoCodeToFlagEmoji(isoCode))

val defaultCountries: List<Country> = listOf(
    country("AR", "Argentina", 54),
    country("BR", "Brasil", 55),
    country("MX", "México", 52),
    country("CO", "Colombia", 57),
    country("CL", "Chile", 56),
    country("UY", "Uruguay", 598),
    country("PY", "Paraguay", 595),
    country("PE", "Perú", 51),
    country("BO", "Bolivia", 591),
    country("EC", "Ecuador", 593),
    country("VE", "Venezuela", 58),
    country("ES", "España", 34),
    country("US", "Estados Unidos", 1),
)
