package com.example.toolbar.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysql.adapter.AlunoAdapter
import com.example.toolbar.activity.CadastroAlunoActivity
import com.example.toolbar.databinding.ActivityListagemAlunoItemBinding
import com.example.toolbar.databinding.ActivityListagemProfessorItemBinding
import com.example.toolbar.model.Aluno
import com.example.toolbar.model.Professor

class ProfessorAdapter(
    private val context: Context,
    private val deleteCallback: (Int) -> Unit
) : RecyclerView.Adapter<ProfessorAdapter.ProfessorViewHolder>() {

    private var professores: List<Professor> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorViewHolder {
        val binding = ActivityListagemProfessorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfessorViewHolder, position: Int) {
        val professor = professores[position]
        holder.bind(professor)

        holder.binding.btnDeletarProf.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Excluir Professor")
                .setMessage("Deseja realmente excluir o professor ${professor.nome}?")
                .setPositiveButton("Sim") { _, _ ->
                    deleteCallback(professor.id)
                }
                .setNegativeButton("Não", null)
                .show()
        }

        holder.binding.btnEditarProf.setOnClickListener {
            val intent = Intent(context, CadastroAlunoActivity::class.java)
            intent.putExtra("PROF_ID", professor.id)
            intent.putExtra("PROF_NOME", professor.nome)
            intent.putExtra("PROF_CPF", professor.cpf)
            intent.putExtra("PROF_EMAIL", professor.email)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return professores.size
    }

    fun setData(prof: List<Professor>) {
        this.professores = prof
        notifyDataSetChanged()
    }

    inner class ProfessorViewHolder(val binding: ActivityListagemProfessorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(professor: Professor) {
            binding.apply {
                txtNomeProf.text = professor.nome
                txtCpf.text = professor.cpf
                txtEmail.text = professor.email
            }
        }
    }
}

