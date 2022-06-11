package com.mobile.liderstoreagent.data.models.reportmodel

data class ReportHistory(
    val agent: Agent,
    val client: Client,
    val comment: String,
    val images: List<String>,
    val created_date:String
)