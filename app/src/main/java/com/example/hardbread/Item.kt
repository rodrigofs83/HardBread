package com.example.hardbread

import java.io.Serializable
import java.util.Calendar

class Item: Serializable {

    var descricao: String
    var preco: Float
    var quantidade: Int
    var data: Calendar
    var id:Int
    var id_gasto:Int

    //  memoria
    constructor(descricao: String, preco: Float, quantidade: Int,id_gasto: Int){
        this.id = 0
        this.id_gasto = id_gasto
        this.descricao = descricao
        this.preco = preco
        this.quantidade = quantidade
        this.data = Calendar.getInstance()
    }

    constructor(id: Int, descricao: String, preco: Float, quantidade: Int, data: Long, id_gasto: Int){
            this.id = id
            this.descricao = descricao
            this.preco = preco
            this.quantidade = quantidade
            this.data =Calendar.getInstance()
            this.data.timeInMillis = data
            this.id_gasto = id_gasto
    }




    fun dataHora(): String {
        val dia = this.data.get(Calendar.DAY_OF_MONTH)
        val mes = this.data.get(Calendar.MONTH) + 1
        val ano = this.data.get(Calendar.YEAR)
        val hora = this.data.get(Calendar.HOUR_OF_DAY)
        val minuto = this.data.get(Calendar.MINUTE)
        val segundo = this.data.get(Calendar.SECOND)
        return "Data ${dia}/${mes}/${ano} Hora ${hora}:${minuto}:${segundo}"
    }

    override fun toString(): String {
    return " ID ${id} - ${descricao} - R$ ${preco} - X ${quantidade} - ${dataHora()} ID ${id_gasto}"
}
}

