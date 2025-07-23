package com.example.domain.repository

import com.example.domain.model.AddressDomainModel
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper

class OrderRepositoryImpl(private val networkService: NetworkService): OrderRepository {
    override suspend fun placeOrder(addressDomainModel: AddressDomainModel): ResultWrapper<Long> {
        return networkService.placeOrder(addressDomainModel, 1)
    }
}