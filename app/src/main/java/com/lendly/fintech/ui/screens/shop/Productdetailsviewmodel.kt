package com.lendly.fintech.ui.screens.shop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lendly.fintech.data.common.Resource
import com.lendly.fintech.data.repository.ProductRepository
import com.lendly.fintech.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductSpec(val label: String, val values: List<String>)

data class Merchant(
    val name: String,
    val logo: String,
    val availability: String,
    val price: Double,
    val installments: Int,
    val totalPrice: Double,
    val downPayment: String,
)

data class ProductDetails(
    val id: String,
    val name: String,
    val brand: String,
    val price: Double,
    val installments: Int,
    val emoji: String,
    val imageUrl: String? = null,
    val description: String,
    val badges: List<String>,
    val merchants: List<Merchant>,
    val specs: List<ProductSpec>,
)

sealed class ProductDetailUiState {
    data object Loading : ProductDetailUiState()
    data class Success(val product: ProductDetails) : ProductDetailUiState()
    data object NotFound : ProductDetailUiState()
    data class Error(val message: String) : ProductDetailUiState()
}

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val productId: String = savedStateHandle[Routes.ARG_ID] ?: ""

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    // Keep for backwards compat with ProductScreen that reads viewModel.product
    val product: ProductDetails?
        get() = (_uiState.value as? ProductDetailUiState.Success)?.product

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState.Loading
            _uiState.value = when (val result = productRepository.getAll()) {
                is Resource.Success -> {
                    val match = result.data.find { it.id == productId }
                    if (match != null) {
                        ProductDetailUiState.Success(match.toProductDetails())
                    } else {
                        ProductDetailUiState.NotFound
                    }
                }
                is Resource.Error   -> ProductDetailUiState.Error(result.message)
                is Resource.Loading -> ProductDetailUiState.Loading
            }
        }
    }

    private fun com.lendly.fintech.data.model.Product.toProductDetails(): ProductDetails {
        val emoji = when (category.lowercase()) {
            "electronics"           -> "📱"
            "fashion"               -> "👟"
            "appliances"            -> "🏠"
            "sports"                -> "⚽"
            else                    -> "🛍️"
        }
        return ProductDetails(
            id           = id,
            name         = name,
            brand        = brand,
            price        = installments.toDouble(),
            installments = installmentMonths,
            emoji        = emoji,
            description  = description ?: "$name by $brand. Available in $category.",
            badges       = buildList {
                if (interestRate == 0.0) add("0% Installment")
                if (isFeatured) add("Featured")
                add("Easy pick-up")
            },
            merchants    = listOf(
                Merchant(
                    name         = brand,
                    logo         = emoji,
                    availability = if (isAvailable) "Available" else "Out of Stock",
                    price        = installments.toDouble(),
                    installments = installmentMonths,
                    totalPrice   = price,
                    downPayment  = "30%",
                )
            ),
            specs        = listOf(
                ProductSpec("Brand",    listOf(brand)),
                ProductSpec("Category", listOf(category)),
                ProductSpec("Price",    listOf("₱${price.toInt()} total")),
                ProductSpec("Rating",   listOf("$rating ★ ($reviewCount reviews)")),
            ),
        )
    }
}