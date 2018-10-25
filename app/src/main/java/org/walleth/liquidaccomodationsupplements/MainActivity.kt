package org.walleth.liquidaccomodationsupplements

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_main.*
import net.glxn.qrgen.android.QRCode
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.walleth.liquidaccomodationsupplements.model.Invoice
import org.walleth.liquidaccomodationsupplements.model.InvoiceRequest
import org.walleth.liquidaccomodationsupplements.model.WalletInformation

val JSON_MEDIA_TYPE = MediaType.parse("application/json")

class LiquidityAPI(
    private val baseURL: String,
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
    private val moshi: Moshi = Moshi.Builder().build()
) {

    fun requestInvoice(invoiceRequest: InvoiceRequest): Invoice? {

        val invoiceString = moshi.adapter(InvoiceRequest::class.java).toJson(invoiceRequest)
        val requestBody = RequestBody.create(JSON_MEDIA_TYPE, invoiceString)
        val request = Request.Builder()
            .post(requestBody)
            .url("$baseURL/invoices/generate")
            .build()

        val response = okHttpClient.newCall(request).execute()
        val result = response.body()?.string()

        return result?.let { parseInvoice(it, moshi) }
    }

    fun getTransactions(): Map<String, Invoice>? {
        val map = Types.newParameterizedType(Map::class.java, String::class.java, Invoice::class.java)
        val adapter: JsonAdapter<Map<String, Invoice>> = moshi.adapter(map)

        val request = Request.Builder()
            .url("$baseURL/invoices/list")
            .build()

        val response = okHttpClient.newCall(request).execute()


        val result = response.body()?.string()
        Log.i("tx raw " , result)

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

class MainActivity : AppCompatActivity() {

    val api by lazy {
        LiquidityAPI("http://10.0.2.2:3600")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Thread(Runnable {

            val walletInformation = api.getWalletInformation()

            if (walletInformation == null) {

            } else {
                val address = walletInformation.address

                val invoiceRequest = InvoiceRequest(1, address)
                val invoice = api.requestInvoice(invoiceRequest)

                runOnUiThread {
                    debug_text_view.text = "address: $address \nnonce: ${invoice?.nonce}"
                    qr_code.setQRCode(invoice?.encoded?.raw!!)
                }

                while (true) {
                    SystemClock.sleep(100)
                    val transactions = api.getTransactions()
                    Log.i("", "transactions:" + transactions.toString())
                }
            }
        }).start()

    }


}

fun ImageView.setQRCode(content: String) {
    val drawable = BitmapDrawable(resources, QRCode.from(content).bitmap())
    drawable.setAntiAlias(false)
    drawable.isFilterBitmap = false
    setImageDrawable(drawable)
}
