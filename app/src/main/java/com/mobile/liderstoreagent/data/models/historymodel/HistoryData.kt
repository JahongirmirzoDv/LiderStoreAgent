package com.mobile.liderstoreagent.data.models.historymodel

data class HistoryData <T>(
    val count: Int,
    val next:String,
    val previous:String,
    val results: List<T>,
)