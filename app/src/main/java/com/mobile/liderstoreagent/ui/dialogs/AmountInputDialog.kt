package com.mobile.liderstoreagent.ui.dialogs

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.amount_input.view.*

class AmountInputDialog(context: Context, price: Double) :
    BaseDialog(context, R.layout.amount_input) {
    private var listener: ((Double, Double, Double?) -> Unit)? = null

    var total_price = 0.0
    init {
        view.apply {

            amountInput.addTextChangedListener(object : SimpleTextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val amount = amountInput.text.toString()
                    val sellPrice = sellInput.text.toString()
                    val discount = discountInput.text.toString()

                    if (amount.isNotEmpty() && sellPrice.isNotEmpty()) {
                        priceText.visibility = View.VISIBLE

                        val price = sellPrice.toDouble() * amount.toDouble()

                        priceText.text =
                            "Нархи: ${sellPrice.toDouble()}x${amount.toDouble()} = $price"

                        if (discount.isNotEmpty()) {
                            priceDiscount.visibility = View.VISIBLE
                            priceTotal.visibility = View.VISIBLE
                            priceDiscount.text =
                                "Чэгирма: ${price * discount.toDouble() / 100.0}"

                            priceTotal.text =
                                "Жами: ${price - price * discount.toDouble() / 100.0}"
                            total_price = price - price * discount.toDouble() / 100.0
                        } else {
                            priceDiscount.visibility = View.GONE
                            priceTotal.visibility = View.GONE
                        }

                    } else {
                        priceText.visibility = View.GONE
                        priceDiscount.visibility = View.GONE
                        priceTotal.visibility = View.GONE
                    }
                }
            })

            sellInput.addTextChangedListener(object : SimpleTextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val amount = amountInput.text.toString()
                    val sellPrice = sellInput.text.toString()
                    val discount = discountInput.text.toString()

                    if (amount.isNotEmpty() && sellPrice.isNotEmpty()) {
                        priceText.visibility = View.VISIBLE

                        val price = sellPrice.toDouble() * amount.toDouble()

                        priceText.text =
                            "Нархи: ${sellPrice.toDouble()}x${amount.toDouble()} = $price"

                        if (discount.isNotEmpty()) {
                            priceDiscount.visibility = View.VISIBLE
                            priceTotal.visibility = View.VISIBLE
                            priceDiscount.text =
                                "Чэгирма: ${price * discount.toDouble() / 100.0}"

                            priceTotal.text =
                                "Жами: ${price - price * discount.toDouble() / 100.0}"
                            total_price = price - price * discount.toDouble() / 100.0
                        } else {
                            priceDiscount.visibility = View.GONE
                            priceTotal.visibility = View.GONE
                        }

                    } else {
                        priceText.visibility = View.GONE
                        priceDiscount.visibility = View.GONE
                        priceTotal.visibility = View.GONE
                    }
                }
            })

            discountInput.addTextChangedListener(object : SimpleTextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val amount = amountInput.text.toString()
                    val sellPrice = sellInput.text.toString()
                    val discount = discountInput.text.toString()

                    if (amount.isNotEmpty() && sellPrice.isNotEmpty()) {
                        priceText.visibility = View.VISIBLE

                        val price = sellPrice.toDouble() * amount.toDouble()

                        priceText.text =
                            "Нархи: ${sellPrice.toDouble()}x${amount.toDouble()} = $price"

                        if (discount.isNotEmpty()) {
                            priceDiscount.visibility = View.VISIBLE
                            priceTotal.visibility = View.VISIBLE
                            priceDiscount.text =
                                "Чэгирма: ${price * discount.toDouble() / 100.0}"

                            priceTotal.text =
                                "Жами: ${price - price * discount.toDouble() / 100.0}"
                            total_price = price - price * discount.toDouble() / 100.0
                        } else {
                            priceDiscount.visibility = View.GONE
                            priceTotal.visibility = View.GONE
                        }

                    } else {
                        priceText.visibility = View.GONE
                        priceDiscount.visibility = View.GONE
                        priceTotal.visibility = View.GONE
                    }
                }
            })




            addAmountInput.setOnClickListener {
                val amount = amountInput.text.toString()
                val sellPrice = sellInput.text.toString()
                val discount = discountInput.text.toString()

                Log.e("TAG", "${total_price}: ${price}")
               if (total_price > price){
                   if (amount.isNotEmpty() && sellPrice.isNotEmpty()
                   ) {
                       textError.visibility = View.INVISIBLE
                       listener!!.invoke(
                           amount.toDouble(),
                           sellPrice.toDouble(),
                           discount.toDoubleOrNull()
                       )
                   } else {
                       textError.visibility = View.VISIBLE
                   }
               }else Toast.makeText(context, "umumiy summa narxdan kichik", Toast.LENGTH_SHORT)
                   .show()

                close()
            }

        }
    }

    fun setOnAmountInput(f: ((Double, Double, Double?) -> Unit)?) {
        listener = f
    }
}

