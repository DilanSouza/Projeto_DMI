package br.edu.mouralacerda.cadalunos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import br.edu.mouralacerda.CadAlunos.R
import br.edu.mouralacerda.CadAlunos.R.menu.menu_principal
import br.edu.mouralacerda.cadalunos.CadastroNomes
import br.edu.mouralacerda.cadalunos.Database
import br.edu.mouralacerda.cadalunos.Pessoa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var listaDaTela : ListView? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaDaTela = findViewById(R.id.lstNomes)

        val botaoNovoNome = findViewById<FloatingActionButton>(R.id.btnNovoNome)

        botaoNovoNome.setOnClickListener {
            val i = Intent(this, CadastroNomes::class.java)
            startActivity(i)
        }

        listaDaTela!!.setOnItemLongClickListener { parent, view, i, id ->

            val alerta = AlertDialog.Builder(this)

            alerta
                .setTitle("Apagar Nome")
                .setMessage("Deseja reamente apagar esse nome da lista?")
                .setPositiveButton("Sim") { dialog, whitch ->
                    val p = parent.adapter.getItem(i) as Pessoa
                    Database.getInstance(this)!!.pessoaDAO().apagar(p)
                    atualizarLista("id")
                    Toast.makeText(this, "Nome apagado", Toast.LENGTH_LONG)


                }
                .setNegativeButton("Não") { dialog, witch ->
                    Toast.makeText(this, "Nome não apagado", Toast.LENGTH_LONG)

                }
                .show()

            ///Toast.makeText(this,"Pessoa: "+p, Toast.LENGTH_LONG)

            //atualizarLista("id")
            true
        }


        listaDaTela!!.setOnItemClickListener { parent, _, i, _ ->
            try {
                val selectedItem = listaDaTela!!.getItemAtPosition(i) as Pessoa
                Log.d("DEBUG", "Item selecionado: $selectedItem")
                val alterarLista = AlertDialog.Builder(this)
                alterarLista.setTitle("Alterar Item")
                val input = EditText(this)
                input.setText(selectedItem.nome) // Suponho que "nome" é um campo em Pessoa
                alterarLista.setView(input)
                alterarLista.setPositiveButton("Salvar") { dialog, which ->
                    val newValue = input.text.toString()
                    selectedItem.nome = newValue // Atualiza o nome na lista
                    Database.getInstance(this)!!.pessoaDAO().alterar(selectedItem) // Atualiza no banco de dados
                    atualizarLista("id") // Atualiza a lista na tela
                    Toast.makeText(this, "Nome alterado", Toast.LENGTH_LONG).show()
                }
                alterarLista.setNegativeButton("Cancelar") { dialog, which ->
                    dialog.cancel()
                }
                alterarLista.show()
            } catch (e: Exception) {
                Log.e("ERROR", "Erro ao lidar com o item da lista: ${e.message}")
            }
        }







    }

    override fun onResume() {
        super.onResume()

        try {
            atualizarLista("id")
        } catch (e: Exception) {
            Log.e("ERROR", "Erro ao atualizar lista: ${e.message}")
        }
    }


    class PessoaAdapter(context: Context, val listaPessoas: List<Pessoa>) : ArrayAdapter<Pessoa>(context, 0, listaPessoas) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
            }

            val pessoa = listaPessoas[position]

            val text = "Nome: ${pessoa.nome}, Curso: ${pessoa.curso}, Idade: ${pessoa.idade}"
            view?.findViewById<TextView>(android.R.id.text1)?.text = text

            return view!!
        }
    }



    fun atualizarLista(ordem: String) {


        var lista: List<Pessoa>? = null
        if(ordem.equals("id")) {
            lista = Database.getInstance(this)!!.pessoaDAO().listarId()
        } else if (ordem.equals("nome")) {
            lista = Database.getInstance(this)!!.pessoaDAO().listarNome()
        }


        //Log.e( "Lista BD",lista.size.toString())

        val adapter = PessoaAdapter(this, lista ?: emptyList())
        listaDaTela?.adapter = adapter

        /*findViewById<ListView>(R.id.lstNomes).adapter =
            ArrayAdapter(this,android.R.layout.simple_list_item_1,
                Database.getInstance(this)!!.pessoa)*/

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item.itemId == R.id.ordemNome) {
            atualizarLista("nome")
        } else if (item.itemId == R.id.ordemId) {
            atualizarLista("id")
        }


        return super.onOptionsItemSelected(item)
    }

}