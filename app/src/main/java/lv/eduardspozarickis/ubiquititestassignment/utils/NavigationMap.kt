package lv.eduardspozarickis.ubiquititestassignment.utils

import kotlinx.serialization.Serializable

class NavigationMap {

    @Serializable
    object Home

    @Serializable
    data class ProductDetails(
        val id: String,
        val deviceType: String,
        val line: String,
        val name: String,
        val imageId: String,
    )
}