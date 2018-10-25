package org.walleth.liquidaccomodationsupplements

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.supplement_item.view.*
import org.ligi.kaxtui.alert
import org.walleth.liquidaccomodationsupplements.model.AccommodationSupplement
import org.walleth.liquidaccomodationsupplements.model.AccommodationSupplements
import org.walleth.liquidaccomodationsupplements.model.liquiditynetwork.InvoiceRequest
import org.walleth.liquidaccomodationsupplements.model.liquiditynetwork.WalletInformation

var currentSelectedSupplement = AccommodationSupplements.first()
val adapter = ItemAdapter(AccommodationSupplements)
var walletInformation: WalletInformation? = null

val api by lazy {
    LiquidityAPI("http://10.0.2.2:3600")
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(supplement: AccommodationSupplement) {
        itemView.supplement_name.text = supplement.name
        itemView.supplement_price.text = supplement.amount.toString() + " " + supplement.currency

        if (supplement == currentSelectedSupplement) {
            itemView.supplement_card.setBackgroundColor(Color.argb(0xff,0,0xcc,0))
        } else {
            itemView.supplement_card.setBackgroundColor(Color.WHITE)
        }

        UrlImageViewHelper.setUrlDrawable(itemView.supplement_image, supplement.imageURL)

        itemView.setOnClickListener {
            currentSelectedSupplement = supplement
            adapter.notifyDataSetChanged()
            (itemView.context as Activity).createInvoice()
        }
    }
}

class ItemAdapter(private val supplements: List<AccommodationSupplement>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val layout = layoutInflater.inflate(R.layout.supplement_item, viewGroup, false)
        return ItemViewHolder(layout)
    }

    override fun getItemCount() = supplements.size

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(supplements[position])
    }

}

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)

        recycler_view.adapter = adapter

                Thread(Runnable {

                    walletInformation = api.getWalletInformation()

                    if (walletInformation == null) {
                        runOnUiThread {
                            alert("Could not load wallet information")
                        }
                    } else {
                        createInvoice()

                        while (true) {
                            SystemClock.sleep(100)
                            val transactions = api.getTransactions()
                            Log.i("", "transactions:" + transactions.toString())
                        }
                    }
                }).start()

    }


}

private fun Activity.createInvoice() {
    Thread(Runnable {

        val address = walletInformation!!.address

        val invoiceRequest = InvoiceRequest(currentSelectedSupplement.amount, address)
        val invoice = api.requestInvoice(invoiceRequest)

        runOnUiThread {
            qr_code.setQRCode(invoice?.encoded?.raw!!)
        }
    }).start()
}