package br.com.siecola.androidproject03.product

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("productPrice")
fun bindProductPrice(txtProductPrice: TextView, productPrice: Float?) {
    txtProductPrice.text = productPrice?.let { "$ " + "%.2f".format(productPrice) }
}

@BindingAdapter("productCode")
fun bindProductCode(txtProductCode: TextView, productCode: Int?) {
    txtProductCode.text = productCode?.let { "$productCode" }
}