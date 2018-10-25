package org.walleth.liquidaccomodationsupplements

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.walleth.liquidaccomodationsupplements.model.JSON_MEDIA_TYPE
import org.walleth.liquidaccomodationsupplements.model.liquiditynetwork.Invoice
import org.walleth.liquidaccomodationsupplements.model.liquiditynetwork.InvoiceRequest
import org.walleth.liquidaccomodationsupplements.model.liquiditynetwork.WalletInformation

class LiquidityAPI(
    private val baseURL: String,
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
    private val moshi: Moshi = Moshi.Builder().build()
) {

    fun requestInvoice(invoiceRequest: InvoiceRequest): Invoice? {

        val invoiceString = moshi.adapter(InvoiceRequest::class.java).toJson(invoiceRequest)
        val requestBody =
            RequestBody.create(JSON_MEDIA_TYPE, invoiceString)
        val request = Request.Builder()
            .post(requestBody)
            .url("$baseURL/invoices/generate")
            .build()

        val response = okHttpClient.newCall(request).execute()
        val result = response.body()?.string()

        return result?.let { parseInvoice(it, moshi) }
    }

    fun getTransactions(): Map<String, Invoice>? {
        val map = Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            Invoice::class.java
        )
        val adapter: JsonAdapter<Map<String, Invoice>> = moshi.adapter(map)

        val request = Request.Builder()
            .url("$baseURL/invoices/list")
            .build()

        val response = okHttpClient.newCall(request).execute()


        val result = response.body()?.string()
        Log.i("tx raw ", result)

        return result?.let { adapter.fromJson(result) }
    }

    fun getWalletInformation(): WalletInformation? {

        val adapter = moshi.adapter(WalletInformation::class.java)

        val request = Request.Builder()
            .url("$baseURL/wallet/information")
            .build()

        val response = okHttpClient.newCall(request).execute()

        val result = response.body()?.string()

        return result?.let { adapter.fromJson(result) }
    }

}