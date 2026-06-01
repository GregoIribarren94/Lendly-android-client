package com.lendly.fintech.ui.screens.shop

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lendly.fintech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productId: String,
    onBack: () -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ProductDetailUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = InteractivePrimary)
            }
        }
        is ProductDetailUiState.NotFound -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Product not found", style = Body, color = ContentTertiary)
            }
        }
        is ProductDetailUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.message, style = Body, color = ContentTertiary)
            }
        }
        is ProductDetailUiState.Success -> {
            ProductContent(product = state.product, onBack = onBack)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductContent(
    product: ProductDetails,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${product.brand} ${product.name.split(" ").take(3).joinToString(" ")}",
                        style = BodyEmphasis,
                        color = ContentPrimary,
                        maxLines = 1,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = ContentPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundScreen),
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundScreen)
                        .padding(horizontal = Spacing.md, vertical = Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(text = "From as low as", style = Caption, color = ContentTertiary)
                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "₱${product.price.toInt()}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = ContentAmount)
                            Text(text = "per month", style = Caption, color = ContentTertiary, modifier = Modifier.padding(bottom = 3.dp))
                        }
                    }
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = InteractiveAccent, contentColor = ContentPrimary),
                        modifier = Modifier.height(48.dp).widthIn(min = 120.dp),
                    ) {
                        Text(text = "Continue", style = BodyEmphasis, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        },
        containerColor = BackgroundScreen,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            // ── Badges ───────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(InteractiveAccent)
                    .padding(horizontal = Spacing.md, vertical = Spacing.xs),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
            ) {
                product.badges.forEach { badge ->
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = ContentPrimary, modifier = Modifier.size(14.dp))
                        Text(text = badge, style = Caption, color = ContentPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // ── Imagen del producto ──────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(BackgroundNeutral),
                contentAlignment = Alignment.Center,
            ) {
                if (!product.imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(Spacing.md),
                    )
                } else {
                    Text(text = product.emoji, fontSize = 120.sp)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(Spacing.sm)
                        .clip(CircleShape)
                        .border(1.dp, BorderNeutral, CircleShape)
                        .background(BackgroundScreen)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(text = "1/4", style = Caption, color = ContentSecondary)
                }
            }

            Spacer(Modifier.height(Spacing.md))

            // ── Precio + nombre ──────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = Spacing.md)) {
                Text(text = "From as low as", style = Caption, color = ContentTertiary)
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "₱${product.price.toInt()}", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = ContentAmount)
                    Text(text = "per month", style = Caption, color = ContentTertiary, modifier = Modifier.padding(bottom = 5.dp))
                }
                Spacer(Modifier.height(4.dp))
                Text(text = product.name, style = BodyEmphasis, color = ContentPrimary, fontSize = 16.sp)
            }

            Spacer(Modifier.height(Spacing.lg))
            HorizontalDivider(color = BorderNeutral.copy(alpha = 0.5f))
            Spacer(Modifier.height(Spacing.md))

            // ── Where to shop ────────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = Spacing.md)) {
                Text(text = "WHERE DO YOU WANT TO SHOP?", style = Caption, color = ContentTertiary, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
                Spacer(Modifier.height(Spacing.sm))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, BorderNeutral, RoundedCornerShape(8.dp))
                        .padding(horizontal = Spacing.md, vertical = 14.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "📍  Davao City, Davao del Sur", style = Body, color = ContentPrimary)
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = ContentTertiary)
                    }
                }
            }

            Spacer(Modifier.height(Spacing.md))

            // ── Merchants ────────────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = Spacing.md)) {
                Text(text = "MARKETPLACE PARTNER MERCHANTS", style = Caption, color = ContentTertiary, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
                Spacer(Modifier.height(Spacing.sm))
                product.merchants.forEach { merchant ->
                    MerchantCard(merchant = merchant)
                    Spacer(Modifier.height(Spacing.sm))
                }
            }

            Spacer(Modifier.height(Spacing.sm))
            HorizontalDivider(color = BorderNeutral.copy(alpha = 0.5f))

            // ── Features ─────────────────────────────────────────────────
            ExpandableSection(title = "FEATURES") {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    FeatureRow(emoji = "📋", title = "How To Apply For A Loan", description = "(1) Only 1 ID needed for the loan approval and,\n(2) Click on Continue to check if you are qualified")
                    FeatureRow(emoji = "🛡️", title = "Disclaimer", description = "Estimated calculation only. Down Payment and other loan terms may vary.")
                }
            }

            HorizontalDivider(color = BorderNeutral.copy(alpha = 0.5f))

            // ── Specs ─────────────────────────────────────────────────────
            ExpandableSection(title = "PRODUCT SPECIFICATIONS") {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                    product.specs.forEach { spec ->
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = spec.label, style = BodyEmphasis, color = ContentPrimary)
                            spec.values.forEach { value ->
                                Text(text = value, style = Body, color = ContentSecondary)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

// ── Merchant Card ─────────────────────────────────────────────────────────────

@Composable
private fun MerchantCard(merchant: Merchant) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().animateContentSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                Box(
                    modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(BackgroundNeutral),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = merchant.logo, fontSize = 20.sp)
                }
                Text(text = merchant.name, style = BodyEmphasis, color = ContentPrimary)
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = ContentTertiary,
                )
            }
        }

        Box(
            modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(BackgroundNeutral).padding(horizontal = 8.dp, vertical = 3.dp),
        ) {
            Text(text = merchant.availability, style = Caption, color = InteractivePrimary, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(4.dp))
        Text(text = "From ₱${merchant.price.toInt()} × ${merchant.installments} months", style = Body, color = ContentSecondary)

        if (expanded) {
            Text(text = "₱${merchant.totalPrice.toInt()} total price", style = Caption, color = ContentTertiary)
            Text(text = "${merchant.downPayment} Downpayment", style = Caption, color = ContentTertiary)
        }

        Spacer(Modifier.height(Spacing.sm))
        HorizontalDivider(color = BorderNeutral.copy(alpha = 0.4f))
    }
}

// ── Expandable Section ────────────────────────────────────────────────────────

@Composable
private fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(true) }

    Column(modifier = Modifier.animateContentSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md, vertical = Spacing.sm),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title, style = Caption, color = ContentTertiary, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = ContentTertiary,
                )
            }
        }
        if (expanded) {
            Box(modifier = Modifier.padding(horizontal = Spacing.md).padding(bottom = Spacing.md)) {
                content()
            }
        }
    }
}

// ── Feature Row ───────────────────────────────────────────────────────────────

@Composable
private fun FeatureRow(emoji: String, title: String, description: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md), verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).border(1.dp, BorderNeutral, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = emoji, fontSize = 18.sp)
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = title, style = BodyEmphasis, color = ContentPrimary)
            Text(text = description, style = Body, color = ContentSecondary)
        }
    }
}