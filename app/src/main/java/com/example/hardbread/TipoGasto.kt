package com.example.hardbread

import java.io.Serializable
class TipoGasto: Serializable{
    private var id:Int
    private var descricao: String
    private var gastos: MutableList<Item>

    constructor(descricao: String){
        this.id = -1
        this.descricao = descricao
    }
    constructor(id: Int,descricao: String){
        this.id = id
        this.descricao = descricao
    }

    init {
        this.gastos = mutableListOf()
    }

    fun addGasto(gasto:  Item){
        this.gastos.add(gasto)

    }
    fun removerGasto(gasto: Item){
        this.gastos.remove(gasto)
    }

    fun getDescricao():String{
        return this.descricao
    }
    fun getId(): Int {
        return this.id
    }

    override fun toString(): String {
        return "${id} - ${descricao}"
    }

}
