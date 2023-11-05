package br.edu.mouralacerda.cadalunos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import br.edu.mouralacerda.CadAlunos.R


class CadastroNomes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_nomes)

        val botaoSalvar = findViewById<Button>(R.id.btnSalvar)
        val nome = findViewById<EditText>(R.id.edtNome)
        val curso = findViewById<Spinner>(R.id.edtCurso)
        val idade = findViewById<EditText>(R.id.edtIdade)

        val cursos = resources.getStringArray(R.array.cursos_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cursos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        curso.adapter = adapter // Definindo o adapter para o Spinner



        val db = Database.getInstance(this)!!.pessoaDAO()

        botaoSalvar.setOnClickListener {

            val n = nome.text.toString()
            val c = curso.selectedItem.toString()
            val i = idade.text.toString().toIntOrNull()

            if (i != null) {
                val pessoa = Pessoa(n, c, i)
                db.salvar(pessoa)
                finish()
            } else {
                // Mensagem de erro ou ação apropriada caso a idade não seja um número válido
                // Por exemplo, mostrar um Toast informando que a idade deve ser um número
            }
        }
    }
}