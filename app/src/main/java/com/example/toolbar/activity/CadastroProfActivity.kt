package com.example.toolbar.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.toolbar.ListagemProfessor
import com.example.toolbar.api.EnderecoAPI
import com.example.toolbar.api.RetrofitHelper
import com.example.toolbar.databinding.ActivityCadastroProfBinding
import com.example.toolbar.databinding.ActivityCadprofBinding
import com.example.toolbar.model.Professor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroProfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroProfBinding
    private var profId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroProfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profId = intent.getIntExtra("PROF_ID", -1)
        if (profId   != -1) {
            binding.edtNome.setText(intent.getStringExtra("PROF_NOME"))
            binding.edtCpf.setText(intent.getStringExtra("PROF_CPF"))
            binding.edtEmail.setText(intent.getStringExtra("PROF_EMAIL"))
        }
        binding.btnSave.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val cpf = binding.edtCpf.text.toString()
            val email = binding.edtEmail.text.toString()
            if (nome.isNotEmpty() && cpf.isNotEmpty() && email.isNotEmpty()) {
                val professor = Professor(profId ?: 0, nome, cpf, email)
                if (profId != null && profId != -1) {
                    alterarProfessor(professor)
                } else {
                    salvarProfessor(professor)
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun salvarProfessor(professor: Professor) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.inserirProfessor(professor)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@CadastroProfActivity, "Erro ao salvar professor.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadastroProfActivity, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun alterarProfessor(professor: Professor) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.alterarProfessor(professor)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK, Intent().putExtra("PROF_ALTERADO", true))
                    finish()
                } else {
                    Toast.makeText(this@CadastroProfActivity, "Erro ao alterar professor.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadastroProfActivity, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}