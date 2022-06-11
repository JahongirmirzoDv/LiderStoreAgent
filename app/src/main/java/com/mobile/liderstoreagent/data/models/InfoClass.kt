package com.mobile.liderstoreagent.data.models

import java.io.Serializable

data class InfoClass (
    var name: String,
    var quantity: String,
    var isHere: String,
    var status: String,
    var info: String = "",
    var price: String = ""
): Serializable