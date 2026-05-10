package com.example.patientapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patientapp.data.model.LoginData
import com.example.patientapp.data.model.LoginRequest
import com.example.patientapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginData) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true && body.data != null) {
                        _loginState.value = LoginState.Success(body.data)
                    } else {
                        _loginState.value = LoginState.Error(body?.message ?: "Login gagal")
                    }
                } else {
                    _loginState.value = LoginState.Error("Email atau password salah")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Koneksi gagal: ${e.message}")
            }
        }
    }
}
