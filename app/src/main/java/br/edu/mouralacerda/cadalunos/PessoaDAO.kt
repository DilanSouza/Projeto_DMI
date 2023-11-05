package br.edu.mouralacerda.cadalunos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface PessoaDAO {

    @Update
    fun alterar(obj: Pessoa)

    @Insert
    fun salvar(obj: Pessoa)

    @Delete
    fun apagar(obj: Pessoa)

    @Query("SELECT * FROM Pessoa ")
    fun listar(): List<Pessoa>

    @Query("SELECT * FROM Pessoa ORDER BY nome")
    fun listarNome(): List<Pessoa>

    @Query("SELECT * FROM Pessoa ORDER BY id")
    fun listarId(): List<Pessoa>

}