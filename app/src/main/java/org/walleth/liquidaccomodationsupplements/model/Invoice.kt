package org.walleth.liquidaccomodationsupplements.model

enum class InvoiceStatus {
    pending, confirmed
}

data class Invoice(
    val uuid: String,
    val nonce: Long,
    val encoded: EncodedInvoice,
    val status: InvoiceStatus
)