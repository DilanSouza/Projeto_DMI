package br.edu.mouralacerda.cadalunos

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [Pessoa::class], version = 2)
abstract class Database: RoomDatabase() {

    abstract fun pessoaDAO(): PessoaDAO

    companion object {

        private var database: Database? = null
        private val DATABASE = "BDNOMES"

        fun getInstance(context: Context): Database?{
            if (database == null){
                return criaBanco(context)
            } else {
                return database
            }
        }
        private fun criaBanco(context: Context): Database?{
            return Room.databaseBuilder(context, Database::class.java, DATABASE)
                .fallbackToDestructiveMigration() // Isso irá destruir e recriar o banco de dados em caso de migração problemática
                .allowMainThreadQueries()
                .build()
        }
    }
}
