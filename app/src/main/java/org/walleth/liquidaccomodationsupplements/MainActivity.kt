package org.walleth.liquidaccomodationsupplements

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.walleth.liquidaccomodationsupplements.model.InvoiceRequest

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