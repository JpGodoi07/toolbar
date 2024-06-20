package com.example.toolbar.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toolbar.activity.CadastroAlunoActivity
import com.example.toolbar.activity.CadastroTurmaActivity
import com.example.toolbar.databinding.ActivityListagemProfessorItemBinding
import com.example.toolbar.databinding.ActivityListagemTurmaItemBinding
import com.example.toolbar.model.Turma

class TurmaAdapter (
    private val context: Context,
    private val deleteCallback: (Int) -> Unit
) : RecyclerView.Adapter<TurmaAdapter.TurmaViewHolder>() {

    private var turmas: List<Turma> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurmaViewHolder {
        val binding = ActivityListagemTurmaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TurmaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TurmaViewHolder, position: Int) {
        val turma = turmas[position]
        holder.bind(turma)

        holder.binding.btnDeletarTurma.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Excluir Turma")
                .setMessage("Deseja realmente excluir a turma ${turma.curso}?")
                .setPositiveButton("Sim") { _, _ ->
                    deleteCallback(turma.id)
                }
                .setNegativeButton("Não", null)
                .show()
        }

        holder.binding.btnEditarTurma.setOnClickListener {
            val intent = Intent(context, CadastroTurmaActivity::class.java)
            intent.putExtra("TURMA_ID", turma.id)
            intent.putExtra("TURMA_NOME", turma.curso)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return turmas.size
    }

    fun setData(prof: List<Turma>) {
        this.turmas = prof
        notifyDataSetChanged()
    }

    inner class TurmaViewHolder(val binding: ActivityListagemTurmaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(turma: Turma) {
            binding.apply {
                txtNomeTurma.text = turma.curso

            }
        }
    }
}
