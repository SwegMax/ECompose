package com.example.ecompose.di

import com.example.ecompose.ShopperSession
import org.koin.dsl.module

val presentationModule = module{
    includes(viewModelModule)
    single { ShopperSession(get()) }
}