package com.example.ecompose.ui.feature.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.OrdersData
import com.example.domain.usecase.OrderListUseCase
import com.example.ecompose.ShopperSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val orderListUseCase: OrderListUseCase,
    private val shopperSession: ShopperSession
) : ViewModel() {

    private val _ordersEvent = MutableStateFlow<OrdersEvent>(OrdersEvent.Loading)
    val ordersEvent = _ordersEvent.asStateFlow()
    val userDomainModel = shopperSession.getUser()

    init {
        getOrderList()
    }

    fun filterOrders(list: List<OrdersData>, filter: String): List<OrdersData> {
        val filteredList = list.filter { it.status == filter }
        return filteredList
    }

    private fun getOrderList(){
        viewModelScope.launch {
            val result = orderListUseCase.execute(userDomainModel!!.id!!.toLong())

            when(result) {
                is com.example.domain.network.ResultWrapper.Success -> {
                    val data = result.value
                    _ordersEvent.value = OrdersEvent.Success(data.`data`)
                }
                is com.example.domain.network.ResultWrapper.Failure -> {
                    _ordersEvent.value = OrdersEvent.Error("Cannot get OrderList")
                }
            }
        }
    }
}

sealed class OrdersEvent {
    object Loading: OrdersEvent()
    data class Success(val data: List<OrdersData>): OrdersEvent()
    data class Error(val errorMsg: String): OrdersEvent()
}