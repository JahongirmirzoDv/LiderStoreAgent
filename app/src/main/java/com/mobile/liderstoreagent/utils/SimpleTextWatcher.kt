package com.mobile.liderstoreagent.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Abrorjon Berdiyorov on 02.04.2022
 */

interface SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}
}