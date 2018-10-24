package org.walleth.liquidaccomodationsupplements

import com.squareup.moshi.Moshi
import org.walleth.liquidaccomodationsupplements.model.Invoice

fun parseInvoice(invoiceString: String, moshi: Moshi): Invoice? {
    val invoiceAdapter = moshi.adapter(Invoice::class.java)
    return invoiceAdapter.fromJson(invoiceString)
}