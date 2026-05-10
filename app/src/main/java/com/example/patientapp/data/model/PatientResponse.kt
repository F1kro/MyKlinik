package com.example.patientapp.data.model

import com.google.gson.annotations.SerializedName

data class PatientResponse(
    val success: Boolean,
    val message: String,
    val data: List<Patient>
)

data class Patient(
    val id: Int,
    val nama: String,
    @SerializedName("tanggal_lahir") val tanggalLahir: String,
    @SerializedName("jenis_kelamin") val jenisKelamin: String,
    val alamat: String,
    @SerializedName("no_telepon") val noTelepon: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)
