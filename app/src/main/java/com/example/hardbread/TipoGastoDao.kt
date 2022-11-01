package com.example.hardbread

import android.content.ContentValues
import android.content.Context
import android.media.audiofx.DynamicsProcessing.BandBase
class TipoGastoDao {
    val banco: DataBase
    constructor(context: Context){
        this.banco = DataBase(context)
    }
    fun insert(tipo:TipoGasto){
        val contentValues = ContentValues()
        contentValues.put("descricao",tipo.getDescricao(),)
        this.banco.writableDatabase.insert("TipoGasto",null,contentValues)

    }
    fun read():ArrayList<TipoGasto>{
        val lista = arrayListOf<TipoGasto>()
        val colunas = arrayOf("id","descricao")
        val c = this.banco.readableDatabase.query("TipoGasto", colunas, null, null, null, null, "descricao")
        c.moveToFirst()
        for (i in 1 .. c.count){
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val tipo = TipoGasto(id,descricao)
            lista.add(tipo)
            c.moveToNext()
        }
        return lista
    }
    fun delete(id: Int){
        val where = arrayOf(id.toString())
        this.banco.writableDatabase.delete("TipoGasto", "id = ?", where)

    }
    // recusividade dando erro  java.lang.StackOverflowError: stack size
    fun delete(tipo: TipoGasto){
         this.delete(tipo)
    }

}
