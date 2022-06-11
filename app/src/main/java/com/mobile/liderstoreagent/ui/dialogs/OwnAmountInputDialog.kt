package com.mobile.liderstoreagent.ui.dialogs

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import com.mobile.liderstoreagent.R
import kotlinx.android.synthetic.main.amount_input.view.addAmountInput
import kotlinx.android.synthetic.main.amount_input.view.amountInput
import kotlinx.android.synthetic.main.amount_input.view.discountInput
import kotlinx.android.synthetic.main.amount_input.view.priceText
import kotlinx.android.synthetic.main.own_amount_input.view.*

class OwnAmountInputDialog(context: Context, price: Double) :
    BaseDialog(context, R.layout.own_amount_input) {
    private var listener: ((Double, Double, Double) -> Unit)? = null

    init {
        view.apply {

            amountInput.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val d = amountInput.text.toString()
                    if (d.isNotEmpty()) {
                        priceText.text = "Цена: ${price}x${d.toDouble()} = ${price * d.toDouble()}"
                    } else {
                        priceText.text = ""
                    }
                }
            })

            addAmountInput.setOnClickListener {
                if (amountInput.text.toString().isNotEmpty() && ediTextTakeClient.text.toString()
                        .isNotEmpty() && discountInput.text.toString().isNotEmpty()
                ) {
                    listener!!.invoke(amountInput.text.toString().toDouble(),
                        ediTextTakeClient.text.toString().toDouble(),
                        discountInput.text.toString().toDouble())
                } else if (amountInput.text.toString()
                        .isNotEmpty() && ediTextTakeClient.text.toString()
                        .isNotEmpty() && discountInput.text.toString().isEmpty()
                ) {
                    listener!!.invoke(amountInput.text.toString().toDouble(),
                        ediTextTakeClient.text.toString().toDouble(),
                        0.0)
                }

                close()
            }

        }
    }

    fun setOnAmountInput(f: ((Double,Double, Double) -> Unit)?) {
        listener = f
    }
}

