package br.edu.mouralacerda.cadalunos

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Pessoa(
    var nome: String,
    val curso: String,
    val idade: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    override fun toString(): String {
        //return "ID:" = id + "/ nome:" + nome

        return "ID: $id / NOME: $nome / CURSO: $curso / IDADE: $idade"
    }
}

