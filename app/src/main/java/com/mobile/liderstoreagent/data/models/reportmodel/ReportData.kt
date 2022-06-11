package com.mobile.liderstoreagent.data.models.reportmodel

import java.io.File

data class ReportData(
    val comment: String = "",
    val image: File? = null,
    val sale_agent: Int
)

data class ClientReportData(
    val comment: String = "",
    val images: ArrayList<File>,
    val agent: Int,
    val client: Int
)