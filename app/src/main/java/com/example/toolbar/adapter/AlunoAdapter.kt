package com.example.mysql.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toolbar.activity.CadastroAlunoActivity
import com.example.toolbar.databinding.ActivityCadastroAlunoBinding
import com.example.toolbar.databinding.ActivityListagemAlunoItemBinding
import com.example.toolbar.model.Aluno

class AlunoAdapter(
    private val context: Context,
    private val deleteCallback: (Int) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    private var alunos: List<Aluno> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val binding = ActivityListagemAlunoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlunoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = alunos[position]
        holder.bind(aluno)

        holder.binding.btnDeletar.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Excluir Aluno")
                .setMessage("Deseja realmente excluir o aluno ${aluno.nome}?")
                .setPositiveButton("Sim") { _, _ ->
                    deleteCallback(aluno.id)
                }
                .setNegativeButton("Não", null)
                .show()
        }

        holder.binding.btnEditarAluno.setOnClickListener {
            val intent = Intent(context, CadastroAlunoActivity::class.java)
            intent.putExtra("ALUNO_ID", aluno.id)
            intent.putExtra("ALUNO_NOME", aluno.nome)
            intent.putExtra("ALUNO_CPF", aluno.cpf)
            intent.putExtra("ALUNO_EMAIL", aluno.email)
            intent.putExtra("ALUNO_MATRICULA", aluno.matricula)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return alunos.size
    }

    fun setData(alunos: List<Aluno>) {
        this.alunos = alunos
        notifyDataSetChanged()
    }

    inner class AlunoViewHolder(val binding: ActivityListagemAlunoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aluno: Aluno) {
            binding.apply {
               txtnome.text = aluno.nome
                txtcpf.text = aluno.cpf
                txtemail.text = aluno.email
                txtmatricula.text = aluno.matricula
            }
        }
    }
}

