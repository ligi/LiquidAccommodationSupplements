package org.walleth.liquidaccomodationsupplements

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import org.junit.Test

const val CREATE_INVOICE_RESPONSE =
    """{"uuid":"6ea13bdbae3c4d7388477101536aefa0","destinations":[{"networkId":"1","contractAddress":"0xac8c3D5242b425DE1b86b17E407D8E949D994010","walletAddresses":["0xb2AF3e066C01c9478d8DEae3df00Ae4bDd570958"]}],"amount":"1","currency":"ETH","details":"0x290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563","nonce":1745803106,"encoded":{"url":"https://lqd.money/?data=eyJ1dWlkIjoiNmVhMTNiZGJhZTNjNGQ3Mzg4NDc3MTAxNTM2YWVmYTAiLCJkZXN0aW5hdGlvbnMiOlt7Im5ldHdvcmtJZCI6IjEiLCJjb250cmFjdEFkZHJlc3MiOiIweGFjOGMzRDUyNDJiNDI1REUxYjg2YjE3RTQwN0Q4RTk0OUQ5OTQwMTAiLCJ3YWxsZXRBZGRyZXNzZXMiOlsiMHhiMkFGM2UwNjZDMDFjOTQ3OGQ4REVhZTNkZjAwQWU0YkRkNTcwOTU4Il19XSwiYW1vdW50IjoxLCJjdXJyZW5jeSI6IkVUSCIsImRldGFpbHMiOiIweDI5MGRlY2Q5NTQ4YjYyYThkNjAzNDVhOTg4Mzg2ZmM4NGJhNmJjOTU0ODQwMDhmNjM2MmY5MzE2MGVmM2U1NjMifQ%3D%3D","raw":"eyJ1dWlkIjoiNmVhMTNiZGJhZTNjNGQ3Mzg4NDc3MTAxNTM2YWVmYTAiLCJkZXN0aW5hdGlvbnMiOlt7Im5ldHdvcmtJZCI6IjEiLCJjb250cmFjdEFkZHJlc3MiOiIweGFjOGMzRDUyNDJiNDI1REUxYjg2YjE3RTQwN0Q4RTk0OUQ5OTQwMTAiLCJ3YWxsZXRBZGRyZXNzZXMiOlsiMHhiMkFGM2UwNjZDMDFjOTQ3OGQ4REVhZTNkZjAwQWU0YkRkNTcwOTU4Il19XSwiYW1vdW50IjoxLCJjdXJyZW5jeSI6IkVUSCIsImRldGFpbHMiOiIweDI5MGRlY2Q5NTQ4YjYyYThkNjAzNDVhOTg4Mzg2ZmM4NGJhNmJjOTU0ODQwMDhmNjM2MmY5MzE2MGVmM2U1NjMifQ%3D%3D"}}"""

class TheInvoiceParser {

    @Test
    fun canParseInvoice() {

        val invoice = parseInvoice(CREATE_INVOICE_RESPONSE, Moshi.Builder().build())!!

        assertThat(invoice.encoded.raw).isEqualTo("eyJ1dWlkIjoiNmVhMTNiZGJhZTNjNGQ3Mzg4NDc3MTAxNTM2YWVmYTAiLCJkZXN0aW5hdGlvbnMiOlt7Im5ldHdvcmtJZCI6IjEiLCJjb250cmFjdEFkZHJlc3MiOiIweGFjOGMzRDUyNDJiNDI1REUxYjg2YjE3RTQwN0Q4RTk0OUQ5OTQwMTAiLCJ3YWxsZXRBZGRyZXNzZXMiOlsiMHhiMkFGM2UwNjZDMDFjOTQ3OGQ4REVhZTNkZjAwQWU0YkRkNTcwOTU4Il19XSwiYW1vdW50IjoxLCJjdXJyZW5jeSI6IkVUSCIsImRldGFpbHMiOiIweDI5MGRlY2Q5NTQ4YjYyYThkNjAzNDVhOTg4Mzg2ZmM4NGJhNmJjOTU0ODQwMDhmNjM2MmY5MzE2MGVmM2U1NjMifQ%3D%3D")

        assertThat(invoice.uuid).isEqualTo("6ea13bdbae3c4d7388477101536aefa0")
        assertThat(invoice.nonce).isEqualTo(1745803106)
    }
}
