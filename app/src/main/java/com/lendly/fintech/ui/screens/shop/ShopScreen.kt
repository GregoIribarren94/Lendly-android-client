package com.lendly.fintech.ui.screens.shop

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lendly.fintech.data.model.Product
import com.lendly.fintech.ui.components.navigation.LendlyTopBar
import com.lendly.fintech.ui.theme.*

// ── Static display data ───────────────────────────────────────────────────────

private data class CategoryItem(val label: String, val emoji: String)
private data class BrandItem(val name: String, val logo: String)

private val staticCategories = listOf(
    CategoryItem("Phone",      "📱"),
    CategoryItem("Headphones", "🎧"),
    CategoryItem("Laptop",     "💻"),
    CategoryItem("Shoes",      "👟"),
    CategoryItem("Watches",    "⌚"),
)

// Brands con logos reales de la API
private val staticBrands = listOf(
    BrandItem("Apple",   "https://img.logo.dev/apple.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
    BrandItem("Samsung", "https://img.logo.dev/samsung.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
    BrandItem("Nike",    "https://img.logo.dev/nike.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
    BrandItem("Sony",    "https://img.logo.dev/sony.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
)

// Emoji fallback cuando no hay imagen
private fun Product.emoji(): String = when (category.lowercase()) {
    "electronics"  -> "📱"
    "fashion"      -> "👟"
    "appliances"   -> "🏠"
    "sports"       -> "⚽"
    else           -> "🛍️"
}

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
        ShopTopBar(onSearch = onSearch)
        Spacer(Modifier.height(4.dp))
        SearchBarRow(onSearch = onSearch, onFilter = onFilter)
        Spacer(Modifier.height(Spacing.md))
        HeroBanner()
        Spacer(Modifier.height(Spacing.lg))

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

// ── Top Bar ───────────────────────────────────────────────────────────────────

@Composable
private fun ShopTopBar(onSearch: () -> Unit) {
    LendlyTopBar(
        leading = { Text(text = "🌿", fontSize = 26.sp) },
        actions = {
            IconButton(onClick = onSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = ContentPrimary, modifier = Modifier.size(24.dp))
            }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications", tint = ContentPrimary, modifier = Modifier.size(24.dp))
            }
        },
    )
}

// ── Search Bar ────────────────────────────────────────────────────────────────

@Composable
private fun SearchBarRow(onSearch: () -> Unit, onFilter: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BackgroundNeutral)
                .clickable(onClick = onSearch)
                .padding(horizontal = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = ContentTertiary, modifier = Modifier.size(20.dp))
            Text(text = "Search for product", style = Body, color = ContentTertiary)
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(InteractivePrimary)
                .clickable(onClick = onFilter),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Filters", tint = BaseContrast, modifier = Modifier.size(20.dp))
        }
    }
}

// ── Hero Banner ───────────────────────────────────────────────────────────────

@Composable
private fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md)
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(colors = listOf(Color(0xFF122300), Color(0xFF2A5200)))),
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart).padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "The New Shoes", style = SectionTitle, color = BaseContrast, fontSize = 20.sp)
            Text(text = "Shop this season's\nTop Silhouette", style = Body, color = Color(0xFFB1D18A), fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(InteractiveAccent).clickable { }.padding(horizontal = 18.dp, vertical = 8.dp),
            ) {
                Text(text = "Shop Now", style = BodyEmphasis, color = ContentPrimary, fontSize = 13.sp)
            }
        }
        Text(text = "👟", fontSize = 72.sp, modifier = Modifier.align(Alignment.CenterEnd).padding(end = Spacing.md))
        Row(
            modifier = Modifier.align(Alignment.BottomStart).padding(start = Spacing.md, bottom = Spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            repeat(3) { i ->
                Box(modifier = Modifier.size(if (i == 0) 16.dp else 6.dp, 6.dp).clip(CircleShape).background(if (i == 0) InteractiveAccent else BaseContrast.copy(alpha = 0.4f)))
            }
        }
    }
}

// ── Section Header ────────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, style = BodyEmphasis, color = ContentPrimary)
        TextButton(onClick = onSeeAll) { Text(text = "See All →", style = Caption, color = InteractivePrimary) }
    }
}

// ── Categories ────────────────────────────────────────────────────────────────

@Composable
private fun CategoriesRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(staticCategories) { cat ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)).background(BackgroundNeutral).clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = cat.emoji, fontSize = 28.sp)
                }
                Text(text = cat.label, style = Caption, color = ContentSecondary)
            }
        }
    }
}

// ── Popular Brands — logos reales ─────────────────────────────────────────────

@Composable
private fun BrandsRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(staticBrands) { brand ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier
                        .width(96.dp)
                        .height(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BackgroundNeutral)
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = brand.logo,
                        contentDescription = brand.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(48.dp).padding(4.dp),
                    )
                }
                Text(text = brand.name, style = Caption, color = ContentSecondary)
            }
        }
    }
}

// ── Horizontal Products Row ───────────────────────────────────────────────────

@Composable
private fun ProductsRow(products: List<Product>, onProductClick: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(products, key = { it.id }) { product ->
            RowProductCard(product = product, onClick = { onProductClick(product.id) })
        }
    }
}

@Composable
private fun RowProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(120.dp).wrapContentHeight().clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(modifier = Modifier.padding(Spacing.sm), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth().height(72.dp).clip(RoundedCornerShape(8.dp)).background(BackgroundNeutral),
                contentAlignment = Alignment.Center,
            ) {
                if (!product.image.isNullOrBlank()) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize().padding(4.dp),
                    )
                } else {
                    Text(text = product.emoji(), fontSize = 36.sp)
                }
            }
            Text(text = product.name, style = CaptionMedium, color = ContentSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = "₱${"%.0f".format(product.price)} × ${product.installments} mo", style = Caption, color = ContentTertiary, maxLines = 1)
        }
    }
}

// ── Grid Product Card ─────────────────────────────────────────────────────────

@Composable
private fun GridProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(modifier = Modifier.padding(Spacing.sm), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(8.dp)).background(BackgroundNeutral),
                contentAlignment = Alignment.Center,
            ) {
                if (!product.image.isNullOrBlank()) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                    )
                } else {
                    Text(text = product.emoji(), fontSize = 48.sp)
                }
            }
            Text(text = product.name, style = CaptionMedium, color = ContentSecondary, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = product.brand, style = Caption, color = ContentTertiary, maxLines = 1)
            Text(text = "₱${"%.0f".format(product.price)} × ${product.installments} mo", style = Caption, color = InteractivePrimary, maxLines = 1)
        }
    }
}