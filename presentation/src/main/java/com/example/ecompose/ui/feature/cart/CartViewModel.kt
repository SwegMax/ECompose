package com.example.ecompose.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CartItemModel
import com.example.domain.usecase.DeleteProductUseCase
import com.example.domain.usecase.GetCartUseCase
import com.example.domain.usecase.UpdateQuantityUseCase
import com.example.ecompose.ShopperSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    val cartUseCase: GetCartUseCase,
    private val updateQuantityUseCase: UpdateQuantityUseCase,
    private val deleteItem : DeleteProductUseCase,
    private val shopperSession: ShopperSession
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartEvent>(CartEvent.Loading)
    val uiState = _uiState.asStateFlow()
    val userDomainModel = shopperSession.getUser()

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            cartUseCase.execute(userDomainModel!!.id!!.toLong()).let { result ->
                when (result) {
                    is com.example.domain.network.ResultWrapper.Success -> {
                        _uiState.value = CartEvent.Success(result.value.data)
                    }

                    is com.example.domain.network.ResultWrapper.Failure -> {
                        _uiState.value = CartEvent.Error("Something went wrong!")
                    }
                }

            }
        }
    }

    fun incrementQuantity(cartItem: CartItemModel) {
        if (cartItem.quantity == 10) return
        updateQuantity(cartItem.copy(quantity = cartItem.quantity + 1))
    }

    fun decrementQuantity(cartItem: CartItemModel) {
        if (cartItem.quantity == 1) return
        updateQuantity(cartItem.copy(quantity = cartItem.quantity - 1))
    }

    private fun updateQuantity(cartItem: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            val result = updateQuantityUseCase.execute(cartItem, userDomainModel!!.id!!.toLong())
            when (result) {
                is com.example.domain.network.ResultWrapper.Success -> {
                    _uiState.value = CartEvent.Success(result.value.data)
                }
                is com.example.domain.network.ResultWrapper.Failure -> {
                    _uiState.value = CartEvent.Error("UpdateQuantity went wrong!")
                }
            }
        }
    }

    fun removeItem(cartItem: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            val result = deleteItem.execute(cartItem.id, 1)
            when (result) {
                is com.example.domain.network.ResultWrapper.Success -> {
                    _uiState.value = CartEvent.Success(result.value.data)
                }
                is com.example.domain.network.ResultWrapper.Failure -> {
                    _uiState.value = CartEvent.Error("removeItem went wrong!")
                }
            }
        }
    }

}

sealed class CartEvent {
    data object Loading : CartEvent()
    data class Success(val message: List<CartItemModel>) : CartEvent()
    data class Error(val message: String) : CartEvent()
}