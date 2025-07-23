package com.example.domain.repository

import com.example.domain.model.AddressDomainModel
import com.example.domain.network.ResultWrapper

interface OrderRepository {
    suspend fun placeOrder(addressDomainModel: AddressDomainModel) : ResultWrapper<Long>
}