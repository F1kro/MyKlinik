package com.example.patientapp.data.network

import com.example.patientapp.data.model.LoginRequest
import com.example.patientapp.data.model.LoginResponse
import com.example.patientapp.data.model.PatientResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("pasien")
    suspend fun getPatients(
        @Header("Authorization") token: String
    ): Response<PatientResponse>
}
