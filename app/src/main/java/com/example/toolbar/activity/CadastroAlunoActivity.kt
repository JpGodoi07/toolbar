package com.example.toolbar.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.toolbar.R
import com.example.toolbar.api.EnderecoAPI
import com.example.toolbar.api.RetrofitHelper
import com.example.toolbar.databinding.ActivityCadastroAlunoBinding
import com.example.toolbar.model.Aluno
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroAlunoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroAlunoBinding
    private var alunoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alunoId = intent.getIntExtra("ALUNO_ID", -1)
        if (alunoId != -1) {
            binding.edtNomeAluno.setText(intent.getStringExtra("ALUNO_NOME"))
            binding.edtCpfAluno.setText(intent.getStringExtra("ALUNO_CPF"))
            binding.edtEmailAluno.setText(intent.getStringExtra("ALUNO_EMAIL"))
            binding.edtMatriculaAluno.setText(intent.getStringExtra("ALUNO_MATRICULA"))
        }

        binding.btnSaveAluno.setOnClickListener {
            val nome = binding.edtNomeAluno.text.toString()
            val cpf = binding.edtCpfAluno.text.toString()
            val email = binding.edtEmailAluno.text.toString()
            val matricula = binding.edtMatriculaAluno.text.toString()

            if (nome.isNotEmpty() && cpf.isNotEmpty() && email.isNotEmpty() && matricula.isNotEmpty()) {
                val aluno = Aluno(alunoId ?: 0, nome, cpf, email, matricula)
                if (alunoId != null && alunoId != -1) {
                    alterarAluno(aluno)
                } else {
                    salvarAluno(aluno)
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarAluno(aluno: Aluno) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.inserirAluno(aluno)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@CadastroAlunoActivity, "Erro ao salvar aluno.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadastroAlunoActivity, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun alterarAluno(aluno: Aluno) {
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val service = retrofit.create(EnderecoAPI::class.java)
        val call = service.alterarAluno(aluno)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    setResult(Activity.RESULT_OK, Intent().putExtra("ALUNO_ALTERADO", true))
                    finish()
                } else {
                    Toast.makeText(this@CadastroAlunoActivity, "Erro ao alterar aluno.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CadastroAlunoActivity, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}