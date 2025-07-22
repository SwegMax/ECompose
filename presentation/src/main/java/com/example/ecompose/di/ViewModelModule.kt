package com.example.ecompose.di

import com.example.ecompose.ui.feature.cart.CartViewModel
import com.example.ecompose.ui.feature.home.HomeViewModel
import com.example.ecompose.ui.feature.product_details.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(),get())
    }
    viewModel {
        ProductDetailsViewModel(get())
    }
    viewModel {
        CartViewModel(get())
    }
}