package com.mobile.liderstoreagent.ui.dialogs

import android.content.Context
import android.widget.Toast
import com.mobile.liderstoreagent.R
import kotlinx.android.synthetic.main.returned_input.view.*


class ReturnedInputDialog(context: Context) :
    BaseDialog(context, R.layout.returned_input) {
    private var listener: ((Double, String) -> Unit)? = null


    var validationTypeText = ""

    init {
        view.apply {


            addAmountInput.setOnClickListener {

                if (returnedAmount.text.toString().isNotEmpty()) {


//                    val id = validationType.checkedRadioButtonId

//                    if (id == R.id.radioValid) {
//                        validationTypeText = "valid"
//                    }
//
//                    if (id == R.id.radioInvalid) {
//                        validationTypeText = "invalid"
//                    }

                    listener?.invoke(returnedAmount.text.toString().toDouble(), validationTypeText)
                } else Toast.makeText(context, "Яроқли миқдор киритинг", Toast.LENGTH_SHORT).show()
                close()
            }


        }
    }

    fun setOnAmountInput(f: ((Double, String) -> Unit)?) {
        listener = f
    }
}

