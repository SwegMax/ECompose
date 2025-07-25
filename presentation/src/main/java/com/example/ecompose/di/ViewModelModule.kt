package com.example.ecompose.di

import com.example.ecompose.ui.feature.account.login.LoginViewModel
import com.example.ecompose.ui.feature.account.register.RegisterViewModel
import com.example.ecompose.ui.feature.cart.CartViewModel
import com.example.ecompose.ui.feature.home.HomeViewModel
import com.example.ecompose.ui.feature.orders.OrdersViewModel
import com.example.ecompose.ui.feature.product_details.ProductDetailsViewModel
import com.example.ecompose.ui.feature.summary.CartSummaryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(),get())
    }
    viewModel {
        ProductDetailsViewModel(get(), get())
    }
    viewModel {
        CartViewModel(get(), get(), get(), get())
    }
    viewModel {
        CartSummaryViewModel(get(), get(), get())
    }
    viewModel {
        OrdersViewModel(get(), get())
    }
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        RegisterViewModel(get(), get())
    }

}