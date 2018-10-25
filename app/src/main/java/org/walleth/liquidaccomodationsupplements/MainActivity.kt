package org.walleth.liquidaccomodationsupplements

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.supplement_item.view.*
import org.walleth.liquidaccomodationsupplements.model.AccommodationSupplement
import org.walleth.liquidaccomodationsupplements.model.AccommodationSupplements
import org.walleth.liquidaccomodationsupplements.model.liquiditynetwork.InvoiceRequest

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(supplement: AccommodationSupplement) {
        itemView.supplement_name.text = supplement.name
        itemView.supplement_price.text = supplement.amount.toString() + " " + supplement.currency
    }
}

class ItemAdapter(private val supplements: List<AccommodationSupplement>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val layout=layoutInflater.inflate(R.layout.supplement_item,viewGroup,false)
        return ItemViewHolder(layout)
    }

    override fun getItemCount() = supplements.size

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(supplements[position])
    }

}

class MainActivity : AppCompatActivity() {

    val api by lazy {
        LiquidityAPI("http://10.0.2.2:3600")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)

        recycler_view.adapter = ItemAdapter(AccommodationSupplements)

        Thread(Runnable {

            val walletInformation = api.getWalletInformation()

            if (walletInformation == null) {

            } else {
                val address = walletInformation.address

                val invoiceRequest =
                    InvoiceRequest(1, address)
                val invoice = api.requestInvoice(invoiceRequest)

                runOnUiThread {
                    //debug_text_view.text = "address: $address \nnonce: ${invoice?.nonce}"
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