package com.example.patientapp.ui.patient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patientapp.databinding.ActivityPatientBinding
import com.example.patientapp.ui.login.LoginActivity
import com.example.patientapp.utils.SessionManager

class PatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    private val viewModel: PatientViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupRecyclerView()
        setupUI()
        setupObserver()
        loadData()
    }

    private fun setupUI() {
        val userName = sessionManager.getUserName() ?: "User"
        binding.tvWelcome.text = userName
        binding.tvUserEmail.text = sessionManager.getUserEmail() ?: ""

        // Set avatar initial
        val initial = userName.firstOrNull()?.uppercase() ?: "U"
        binding.tvAvatarHeader.text = initial

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    sessionManager.clearSession()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadData()
        }
    }

    private fun setupRecyclerView() {
        adapter = PatientAdapter()
        binding.rvPatients.apply {
            layoutManager = LinearLayoutManager(this@PatientActivity)
            adapter = this@PatientActivity.adapter
        }
    }

    private fun setupObserver() {
        viewModel.patientState.observe(this) { state ->
            binding.swipeRefresh.isRefreshing = false
            when (state) {
                is PatientState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvPatients.visibility = View.GONE
                    binding.layoutError.visibility = View.GONE
                }
                is PatientState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutError.visibility = View.GONE
                    binding.rvPatients.visibility = View.VISIBLE
                    binding.tvPatientCount.text = "${state.patients.size}"
                    adapter.submitList(state.patients)
                }
                is PatientState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvPatients.visibility = View.GONE
                    binding.layoutError.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = state.message
                }
            }
        }
    }

    private fun loadData() {
        val token = sessionManager.getToken() ?: return
        viewModel.fetchPatients(token)
    }
}
