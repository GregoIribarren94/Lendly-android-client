package com.lendly.fintech.ui.screens.shop.components

import com.lendly.fintech.data.model.Product

// Emoji fallback cuando no hay imagen
internal fun Product.emoji(): String = when (category.lowercase()) {
    "electronics"  -> "📱"
    "fashion"      -> "👟"
    "appliances"   -> "🏠"
    "sports"       -> "⚽"
    else           -> "🛍️"
}
