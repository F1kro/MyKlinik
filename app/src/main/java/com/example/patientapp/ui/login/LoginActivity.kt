package com.example.patientapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.patientapp.databinding.ActivityLoginBinding
import com.example.patientapp.ui.patient.PatientActivity
import com.example.patientapp.utils.SessionManager
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Skip login if already logged in
        if (sessionManager.isLoggedIn()) {
            navigateToPatient()
            return
        }

        setupObserver()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.tilEmail.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.tilPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            binding.tilEmail.error = null
            binding.tilPassword.error = null
            viewModel.login(email, password)
        }
    }

    private fun setupObserver() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false
                    binding.btnLogin.text = ""
                }
                is LoginState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.text = "Masuk"
                    sessionManager.saveSession(
                        token = state.data.token,
                        name = state.data.user.name,
                        email = state.data.user.email
                    )
                    navigateToPatient()
                }
                is LoginState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.text = "Masuk"
                    Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    private fun navigateToPatient() {
        startActivity(Intent(this, PatientActivity::class.java))
        finish()
    }
}
