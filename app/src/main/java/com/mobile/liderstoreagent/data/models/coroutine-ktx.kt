package com.mobile.liderstoreagent.data.models

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = CoroutineScope(context).launch(context, start, block)

fun Fragment.launchWithDelay(interval: Long = 300, onLaunch: () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        delay(interval)
        onLaunch.invoke()
    }
}