package com.example.hardbread

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DataBase(context: Context):SQLiteOpenHelper(context,"gasto.db",null,2){
    override fun onCreate(db: SQLiteDatabase?) {


        val tb_gasto = "CREATE TABLE  TipoGasto(" +
                "id INTEGER PRIMARY KEY  autoincrement," +
                " descricao TEXT)"

        val tb_item = "CREATE TABLE Item(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "descricao TEXT," +
                "preco Real," +
                "quantidade INTEGER," +
                "data INTEGER," +
                "id_gasto INTEGER,"+
                "FOREIGN KEY(id_gasto) REFERENCES tb_gasto(id_gasto) ON DELETE CASCADE )"

        db?.execSQL(tb_gasto)
        db?.execSQL(tb_item)
        db?.execSQL("PRAGMA foreign_keys=ON");

    }

    override fun onUpgrade(db: SQLiteDatabase?, anterior: Int, atual: Int) {
        val upgrade_tb_gasto = "DROP TABLE if EXISTS tb_gasto"
        val upgrade_tb_item = "DROP TABLE if EXISTS tb_item"
        db?.execSQL(upgrade_tb_gasto)
        db?.execSQL(upgrade_tb_item)
        onCreate(db)
    }
}
