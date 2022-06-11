package com.mobile.liderstoreagent.data.models.clientmodel.clientdetail

data class SaleAgent(
    val birthdate_children: String,
    val birthdate_father: String,
    val birthdate_mother: String,
    val date_joined: String,
    val department: Int,
    val family_address: String,
    val first_name: String,
    val full_name_children: String,
    val full_name_father: String,
    val full_name_mother: String,
    val groups: List<Any>,
    val id: Int,
    val is_active: Boolean,
    val is_admin: Boolean,
    val is_superuser: Boolean,
    val last_login: String,
    val last_name: String,
    val password: String,
    val phone_father: String,
    val phone_mother: String,
    val phone_number: String,
    val role: String,
    val self_address: String,
    val self_birth_date: String,
    val user_permissions: List<Any>
)