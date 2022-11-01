package com.example.hardbread

import android.content.ContentValues
import android.content.Context

class ItemDao {
    val banco: DataBase

    constructor(context: Context) {
        this.banco = DataBase(context)
    }

    fun insert(item: Item) {
        val contentValues = ContentValues()
        contentValues.put("descricao", item.descricao)
        contentValues.put("preco", item.preco)
        contentValues.put("quantidade", item.quantidade)
        contentValues.put("data", item.data.timeInMillis)
        contentValues.put("id_gasto",item.id_gasto)
        this.banco.writableDatabase.insert("Item", null, contentValues)

    }

    fun read(): ArrayList<Item> {
        val lista = arrayListOf<Item>()
        val colunas = arrayOf("id", "descricao", "preco", "quantidade", "data","id_gasto")
        val c =
            this.banco.readableDatabase.query("Item", colunas, null, null, null, null, "descricao")
        c.moveToFirst()
        for (i in 1..c.count) {
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val preco = c.getFloat(2)
            val quantidade = c.getInt(3)
            val data = c.getLong(4)
            val id_gasto = c.getInt(5)
            val tipo = Item(id, descricao, preco, quantidade, data,id_gasto)
            lista.add(tipo)
            c.moveToNext()
        }
        return lista
    }
    fun read(id: String): ArrayList<Item> {
        val lista = arrayListOf<Item>()
        val colunas = arrayOf("id", "descricao", "preco", "quantidade", "data", "id_gasto")
        val where = "id_gasto= ?"
        val pwhere =  arrayOf(id)

        val c =
            this.banco.readableDatabase.query("Item", colunas, where,pwhere, null, null, "descricao")
        c.moveToFirst()
        for (i in 1..c.count) {
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val preco = c.getFloat(2)
            val quantidade = c.getInt(3)
            val data = c.getLong(4)
            val id_gasto = c.getInt(5)
            val tipo = Item(id, descricao, preco, quantidade, data,id_gasto)
            lista.add(tipo)
            c.moveToNext()
        }
        return lista
    }



    fun delete(id: Int) {
        val where = arrayOf(id.toString())
        this.banco.writableDatabase.delete("Item", "id = ?", where)

    }
    // recusividade dando erro  java.lang.StackOverflowError: stack size
    fun delete(tipo: Item) {
         this.delete(tipo)
    }
    fun deleteCascade(id:Int) {
        val id = id.toString()
        val where = "id_gasto= ?"
        val pwhere = arrayOf(id)
        this.banco.writableDatabase.delete("Item",where, pwhere)

    }
    fun update(tipo: Item) {

    }
}

