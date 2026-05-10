package com.example.patientapp.ui.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patientapp.data.model.Patient
import com.example.patientapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

sealed class PatientState {
    object Loading : PatientState()
    data class Success(val patients: List<Patient>) : PatientState()
    data class Error(val message: String) : PatientState()
}

class PatientViewModel : ViewModel() {

    private val _patientState = MutableLiveData<PatientState>()
    val patientState: LiveData<PatientState> = _patientState

    fun fetchPatients(token: String) {
        _patientState.value = PatientState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getPatients("Bearer $token")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        _patientState.value = PatientState.Success(body.data)
                    } else {
                        _patientState.value = PatientState.Error(body?.message ?: "Gagal memuat data")
                    }
                } else {
                    _patientState.value = PatientState.Error("Gagal memuat data pasien")
                }
            } catch (e: Exception) {
                _patientState.value = PatientState.Error("Koneksi gagal: ${e.message}")
            }
        }
    }
}
