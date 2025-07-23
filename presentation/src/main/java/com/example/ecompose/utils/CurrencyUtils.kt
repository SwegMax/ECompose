package com.example.ecompose.utils

import java.text.NumberFormat
import java.util.Currency

object CurrencyUtils {

    fun formatPrice(price: Double, currency: String = "SGD"): String {
        val format = NumberFormat.getCurrencyInstance()
        format.currency = Currency.getInstance(currency)
        return format.format(price)
    }
}