package com.lendly.fintech.ui.screens.shop.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.ui.theme.*

@Composable
fun ProductsRow(products: List<Product>, onProductClick: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(products, key = { it.id }) { product ->
            RowProductCard(product = product, onClick = { onProductClick(product.id) })
        }
    }
}
