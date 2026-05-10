package com.example.patientapp.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData?
)

data class LoginData(
    val token: String,
    val user: UserData
)

data class UserData(
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)
