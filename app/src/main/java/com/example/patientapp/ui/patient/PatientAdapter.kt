package com.example.patientapp.ui.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.patientapp.data.model.Patient
import com.example.patientapp.databinding.ItemPatientBinding

class PatientAdapter : ListAdapter<Patient, PatientAdapter.PatientViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ItemPatientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PatientViewHolder(private val binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: Patient) {
            binding.apply {
                tvNama.text = patient.nama
                tvTanggalLahir.text = patient.tanggalLahir
                tvAlamat.text = patient.alamat
                tvTelepon.text = patient.noTelepon

                val gender = if (patient.jenisKelamin == "L") "Laki-laki" else "Perempuan"
                tvJenisKelamin.text = gender

                // Avatar initial
                tvAvatar.text = patient.nama.firstOrNull()?.uppercase() ?: "?"

                // Gender color indicator
                val colorRes = if (patient.jenisKelamin == "L")
                    com.example.patientapp.R.color.male_color
                else
                    com.example.patientapp.R.color.female_color
                tvAvatar.backgroundTintList =
                    itemView.context.getColorStateList(colorRes)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Patient, newItem: Patient) = oldItem == newItem
    }
}
