package com.mobile.liderstoreagent.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.mobile.liderstoreagent.R
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun makeSafeCall(someFun: () -> Unit) {
    try {
        someFun.invoke()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showToast(st: String) {
    Toast.makeText(this, st, Toast.LENGTH_SHORT).show()
}

fun ViewPager.pageChangeListener(f: (Int) -> Unit) =
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            f.invoke(position)
        }
    })


fun log(message : String, tag : String = "TTT"){
    Log.d(tag,message)
}

fun View.hideKeyboard(activity: Activity) {
    val systemService = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    systemService.hideSoftInputFromWindow(this.applicationWindowToken, 0)
}


fun File.toRequestData(): MultipartBody.Part {
    val fileReqBody = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("file", name, fileReqBody)
}


fun Spinner.addItems(context: Context, data: ArrayList<String>){
    val aa: ArrayAdapter<String> = ArrayAdapter(context, R.layout.spinner_text_view, data)
    aa.setDropDownViewResource(R.layout.spinner_text_view)
    adapter = aa
}