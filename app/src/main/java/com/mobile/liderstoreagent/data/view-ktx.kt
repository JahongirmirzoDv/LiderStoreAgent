package com.mobile.liderstoreagent.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

val ViewGroup.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun View.attach(checkBox: CheckBox) {
    setOnClickListener { checkBox.isChecked = !checkBox.isChecked }
}
// file
fun File.toFormData(partName: String = "file"): MultipartBody.Part {
    val request = asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, name, request)
}