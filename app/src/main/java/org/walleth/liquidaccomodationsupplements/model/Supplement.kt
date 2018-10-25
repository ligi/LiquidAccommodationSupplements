package org.walleth.liquidaccomodationsupplements.model

data class AccommodationSupplement (
    val name: String,
    val imageURL: String,
    val amount: Long,
    val currency: String
)