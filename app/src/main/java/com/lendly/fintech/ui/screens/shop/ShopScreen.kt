package com.lendly.fintech.ui.screens.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.ui.screens.shop.components.BrandsRow
import com.lendly.fintech.ui.screens.shop.components.CategoriesRow
import com.lendly.fintech.ui.screens.shop.components.GridProductCard
import com.lendly.fintech.ui.screens.shop.components.HeroBanner
import com.lendly.fintech.ui.screens.shop.components.ProductsRow
import com.lendly.fintech.ui.screens.shop.components.SearchBarRow
import com.lendly.fintech.ui.screens.shop.components.SectionHeader
import com.lendly.fintech.ui.screens.shop.components.ShopTopBar
import com.lendly.fintech.ui.theme.*

// ── ShopScreen ────────────────────────────────────────────────────────────────

@Composable
fun ShopScreen(
    onSearch: () -> Unit,
    onFilter: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: ShopViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(containerColor = BackgroundScreen) { paddingValues ->
        when (val state = uiState) {
            is ShopUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = InteractivePrimary)
                }
            }
            is ShopUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Spacing.md),
                    ) {
                        Text(text = state.message, style = Body, color = ContentSecondary)
                        Button(
                            onClick = { viewModel.loadProducts() },
                            colors = ButtonDefaults.buttonColors(containerColor = InteractivePrimary),
                        ) {
                            Text("Retry", color = BaseContrast)
                        }
                    }
                }
            }
            is ShopUiState.Success -> {
                ShopContent(
                    products    = state.products,
                    recommended = viewModel.recommended(state.products),
                    bestSellers = viewModel.bestSellers(state.products),
                    paddingValues = paddingValues,
                    onSearch    = onSearch,
                    onFilter    = onFilter,
                    onProductClick = onProductClick,
                )
            }
        }
    }
}

// ── Loaded content ────────────────────────────────────────────────────────────

@Composable
private fun ShopContent(
    products: List<Product>,
    recommended: List<Product>,
    bestSellers: List<Product>,
    paddingValues: PaddingValues,
    onSearch: () -> Unit,
    onFilter: () -> Unit,
    onProductClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
    ) {
        ShopTopBar()
        Spacer(Modifier.height(4.dp))
        SearchBarRow(onSearch = onSearch, onFilter = onFilter)
        Spacer(Modifier.height(Spacing.md))
        HeroBanner()
        Spacer(Modifier.height(Spacing.xl))

        SectionHeader(title = "Shop By Category", onSeeAll = {})
        Spacer(Modifier.height(Spacing.sm))
        CategoriesRow()

        Spacer(Modifier.height(Spacing.lg))
        SectionHeader(title = "Popular Brands", onSeeAll = {})
        Spacer(Modifier.height(Spacing.sm))
        BrandsRow()

        if (recommended.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.lg))
            SectionHeader(title = "Recommended For You", onSeeAll = {})
            Spacer(Modifier.height(Spacing.sm))
            ProductsRow(products = recommended, onProductClick = onProductClick)
        }

        if (bestSellers.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.lg))
            SectionHeader(title = "Best Sellers", onSeeAll = {})
            Spacer(Modifier.height(Spacing.sm))
            ProductsRow(products = bestSellers, onProductClick = onProductClick)
        }

        Spacer(Modifier.height(Spacing.lg))
        SectionHeader(title = "All Products", onSeeAll = {})
        Spacer(Modifier.height(Spacing.sm))

        val rows = (products.size + 1) / 2
        val gridHeight = (rows * 188).dp

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight)
                .padding(horizontal = Spacing.md),
            contentPadding = PaddingValues(bottom = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            userScrollEnabled = false,
        ) {
            items(products, key = { it.id }) { product ->
                GridProductCard(product = product, onClick = { onProductClick(product.id) })
            }
        }

        Spacer(Modifier.height(Spacing.xl))
    }
}

@Preview(name = "ShopScreen", showBackground = true, showSystemUi = true, heightDp = 1200)
@Composable
private fun ShopScreenPreview() {
    val previewProducts = listOf(
        Product(
            id = "iphone-15",
            name = "iPhone 15 Pro",
            brand = "Apple",
            category = "electronics",
            price = 54990.0,
            installments = 4583,
            installmentMonths = 12,
            rating = 4.8,
            reviewCount = 128,
        ),
        Product(
            id = "galaxy-s24",
            name = "Galaxy S24",
            brand = "Samsung",
            category = "electronics",
            price = 45990.0,
            installments = 3833,
            installmentMonths = 12,
            rating = 4.7,
            reviewCount = 94,
        ),
        Product(
            id = "airpods-pro",
            name = "AirPods Pro",
            brand = "Apple",
            category = "electronics",
            price = 12990.0,
            installments = 1083,
            installmentMonths = 12,
            rating = 4.6,
            reviewCount = 76,
        ),
        Product(
            id = "nike-air",
            name = "Nike Air Max",
            brand = "Nike",
            category = "fashion",
            price = 6990.0,
            installments = 583,
            installmentMonths = 12,
            rating = 4.5,
            reviewCount = 42,
        ),
        Product(
            id = "sony-wh",
            name = "Sony WH-1000XM5",
            brand = "Sony",
            category = "electronics",
            price = 18990.0,
            installments = 1583,
            installmentMonths = 12,
            rating = 4.9,
            reviewCount = 61,
        ),
        Product(
            id = "apple-watch",
            name = "Apple Watch Series 9",
            brand = "Apple",
            category = "electronics",
            price = 21990.0,
            installments = 1833,
            installmentMonths = 12,
            rating = 4.7,
            reviewCount = 88,
        ),
    )

    LendlyTheme {
        ShopContent(
            products = previewProducts,
            recommended = previewProducts.take(4),
            bestSellers = previewProducts.takeLast(4),
            paddingValues = PaddingValues(0.dp),
            onSearch = {},
            onFilter = {},
            onProductClick = {},
        )
    }
}