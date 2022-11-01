package com.example.hardbread

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TelaGasto : AppCompatActivity() {
    private lateinit var tvTitulo: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvValorTotal: TextView
    private lateinit var lvGastos: ListView
    private lateinit var fabAddGasto: FloatingActionButton
    private lateinit var titulo: TipoGasto
   private lateinit var itemDao: ItemDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_gasto)
        this.tvTitulo = findViewById(R.id.tvTitulo2)
        this.tvTotal = findViewById(R.id.tvTotal)
        this.tvValorTotal = findViewById(R.id.tvValorTotal)
        this.lvGastos = findViewById(R.id.lvGastos)
        this.fabAddGasto = findViewById(R.id.fabAddGasto)
        this.itemDao = ItemDao(this)



        val resultForm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                Atualizar()

            }
        }
        if (intent.hasExtra("tipoGasto")) {
            this.titulo = intent.getSerializableExtra("tipoGasto") as TipoGasto
            this.tvTitulo.setText(this.titulo.getDescricao())
            Toast.makeText(this@TelaGasto, titulo.getId().toString(), Toast.LENGTH_SHORT).show()

            var adapter = ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, this.itemDao.read(this.titulo.getId().toString()))
            this.lvGastos.adapter = adapter
            Soma(this.titulo.getId().toString())
            this.fabAddGasto.setOnClickListener {
                val tipoGasto = this.titulo
                val intent = Intent(this, FormCadastraItem::class.java).apply {
                    putExtra("tipoGasto",tipoGasto)
                }
                if(intent.resolveActivity(packageManager) != null ){
                    //startActivity(intent)
                    resultForm.launch(intent)
                }
            }
            this.lvGastos.setOnItemLongClickListener(OnItemLongClick())
        }
    }
    fun Soma(id: String){
        var total = 0.0F
        val gastos: MutableList<Item>
        gastos = this.itemDao.read(id)
        for(item  in gastos) {
            total = total + (item.preco * item.quantidade)
        }

        this.tvValorTotal.setText("R$"+" "+total.toString().replace(".",","))
    }

    fun Atualizar(){
        val layout = android.R.layout.simple_list_item_1
        this.lvGastos.adapter = ArrayAdapter<Item>(this, layout,this.itemDao.read(this.titulo.getId().toString()))
        Soma(this.titulo.getId().toString())
    }
    inner class OnItemLongClick: AdapterView.OnItemLongClickListener {
        override fun onItemLongClick(
            adapter: AdapterView<*>?,
            view: View?,
            index: Int,
            id: Long
        ): Boolean {
            val itens = adapter?.getItemAtPosition(index) as Item
            this@TelaGasto.itemDao.delete(itens.id)
            val msg = "${itens.descricao}removido com sucesso!"
            Toast.makeText(this@TelaGasto, msg, Toast.LENGTH_SHORT).show()
            this@TelaGasto.Atualizar()
            return true
        }
    }
    /*
    fun btAdd(){
            val tipoGasto = this.titulo
            val intent = Intent(this, FormCadastraItem::class.java).apply {
                putExtra("tipoGasto",tipoGasto)
            }
            if(intent.resolveActivity(packageManager) != null ){
                //startActivity(intent)
               // resultForm.launch(intent)
            }

        }

     */

}