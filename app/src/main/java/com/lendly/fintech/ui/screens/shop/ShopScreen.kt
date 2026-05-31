package com.lendly.fintech.ui.screens.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lendly.fintech.ui.theme.*

// ── Modelos de datos de ejemplo ─────────────────────────────────────────────

private data class Category(val label: String, val emoji: String)
private data class Brand(val name: String, val emoji: String)
private data class ShopProduct(
    val id: String,
    val name: String,
    val price: String,
    val months: String,
    val emoji: String,
)

private val categories = listOf(
    Category("Phone",      "📱"),
    Category("Headphones", "🎧"),
    Category("Apparel",    "💻"),
    Category("Shoes",      "👟"),
    Category("Watches",    "⌚"),
)

private val brands = listOf(
    Brand("Apple",   "🍎"),
    Brand("Jordan",  "👟"),
    Brand("Adidas",  "⚡"),
    Brand("Samsung", "📱"),
    Brand("Sony",    "🎧"),
)

private val recommended = listOf(
    ShopProduct("r1", "iPhone 12 Pro",     "1,200", "24", "📱"),
    ShopProduct("r2", "iPhone 12 Pro Max", "1,200", "24", "📱"),
    ShopProduct("r3", "Air Jordan 1",      "1,200", "24", "👟"),
    ShopProduct("r4", "Sony WH-1000XM5",   "1,200", "24", "🎧"),
)

private val bestSellers = listOf(
    ShopProduct("b1", "Surface Laptop",     "1,200", "24", "💻"),
    ShopProduct("b2", "iPhone 12 Pro",      "1,200", "24", "📱"),
    ShopProduct("b3", "PS4 Play Station",   "1,200", "24", "🎮"),
    ShopProduct("b4", "Adidas Ultra Boost", "1,200", "24", "👟"),
)

// ── ShopScreen ───────────────────────────────────────────────────────────────

@Composable
fun ShopScreen(
    onSearch: () -> Unit,
    onFilter: () -> Unit,
    onProductClick: (String) -> Unit,
) {
    Scaffold(containerColor = BackgroundScreen) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            ShopTopBar()
            Spacer(Modifier.height(4.dp))
            SearchBar(onSearch = onSearch, onFilter = onFilter)
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
            Spacer(Modifier.height(Spacing.lg))
            SectionHeader(title = "Recommended For You", onSeeAll = {})
            Spacer(Modifier.height(Spacing.sm))
            ProductsRow(products = recommended, onProductClick = onProductClick)
            Spacer(Modifier.height(Spacing.lg))
            SectionHeader(title = "Best Sellers", onSeeAll = {})
            Spacer(Modifier.height(Spacing.sm))
            ProductsRow(products = bestSellers, onProductClick = onProductClick)
            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

// ── Top Bar ──────────────────────────────────────────────────────────────────

@Composable
private fun ShopTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = ContentPrimary,
            modifier = Modifier.size(28.dp),
        )
        Spacer(Modifier.weight(1f))
        Text(text = "🌿", fontSize = 26.sp)
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = ContentPrimary,
            modifier = Modifier.size(28.dp),
        )
    }
}

// ── Search Bar ───────────────────────────────────────────────────────────────

@Composable
private fun SearchBar(onSearch: () -> Unit, onFilter: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md),
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
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = ContentTertiary,
                modifier = Modifier.size(20.dp),
            )
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
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Filters",
                tint = BaseContrast,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

// ── Hero Banner ──────────────────────────────────────────────────────────────

@Composable
private fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md)
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF122300), Color(0xFF2A5200)),
                )
            ),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "The New Shoes", style = SectionTitle, color = BaseContrast, fontSize = 20.sp)
            Text(text = "Shop this season's\nTop Silhouette", style = Body, color = Color(0xFFB1D18A), fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(InteractiveAccent)
                    .clickable { }
                    .padding(horizontal = 18.dp, vertical = 8.dp),
            ) {
                Text(text = "Shop Now", style = BodyEmphasis, color = ContentPrimary, fontSize = 13.sp)
            }
        }
        Text(
            text = "👟",
            fontSize = 72.sp,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = Spacing.md),
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Spacing.md, bottom = Spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            repeat(3) { i ->
                Box(
                    modifier = Modifier
                        .size(if (i == 0) 16.dp else 6.dp, 6.dp)
                        .clip(CircleShape)
                        .background(if (i == 0) InteractiveAccent else BaseContrast.copy(alpha = 0.4f)),
                )
            }
        }
    }
}

// ── Section Header ───────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, style = BodyEmphasis, color = ContentPrimary)
        TextButton(onClick = onSeeAll) {
            Text(text = "See All →", style = Caption, color = InteractivePrimary)
        }
    }
}

// ── Categories ───────────────────────────────────────────────────────────────

@Composable
private fun CategoriesRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(categories) { cat ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BackgroundNeutral)
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = cat.emoji, fontSize = 28.sp)
                }
                Text(text = cat.label, style = Caption, color = ContentSecondary)
            }
        }
    }
}

// ── Popular Brands ───────────────────────────────────────────────────────────

@Composable
private fun BrandsRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(brands) { brand ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(96.dp)
                        .height(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BackgroundNeutral)
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = brand.emoji, fontSize = 32.sp)
                }
                Text(text = brand.name, style = Caption, color = ContentSecondary)
            }
        }
    }
}

// ── Products Row ─────────────────────────────────────────────────────────────

@Composable
private fun ProductsRow(
    products: List<ShopProduct>,
    onProductClick: (String) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        items(products) { product ->
            ProductCard(product = product, onClick = { onProductClick(product.id) })
        }
    }
}

@Composable
private fun ProductCard(product: ShopProduct, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .wrapContentHeight()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(
            modifier = Modifier.padding(Spacing.sm),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(BackgroundNeutral),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = product.emoji, fontSize = 36.sp)
            }
            Text(
                text = product.name,
                style = CaptionMedium,
                color = ContentSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "₱${product.price} × ${product.months} mo",
                style = Caption,
                color = ContentTertiary,
                maxLines = 1,
            )
        }
    }
}