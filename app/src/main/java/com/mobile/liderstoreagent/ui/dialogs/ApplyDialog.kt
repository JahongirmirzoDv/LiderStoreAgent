package com.mobile.liderstoreagent.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.liderstoreagent.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_apply.*

class ApplyDialog : BottomSheetDialogFragment() {
    private var listener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_apply, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonApply.setOnClickListener {
            listener?.invoke()
        }
    }

    fun clickApply(f :() -> Unit){
        listener = f
    }
}