package com.example.ecompose.model

import android.os.Parcelable
import com.example.domain.model.AddressDomainModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserAddress(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
): Parcelable {
    override fun toString(): String {
        return "$addressLine, $city, $state, $postalCode, $country"
    }

    fun toAddressDomainModel() = AddressDomainModel(
        addressLine,
        city,
        state,
        postalCode,
        country
    )
}
