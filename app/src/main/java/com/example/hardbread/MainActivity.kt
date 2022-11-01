package com.example.hardbread

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var tvLista: TextView
    private lateinit var tvTotalT1: TextView
    private lateinit var lvGastos: ListView
    private lateinit var fabAddListaGasto: FloatingActionButton
    private lateinit var itemDao: ItemDao
    private lateinit var gastoDao: TipoGastoDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.gastoDao = TipoGastoDao(this)
        this.itemDao = ItemDao(this)
        this.tvLista = findViewById(R.id.tvlista)
        this.tvTotalT1 = findViewById(R.id.tvValorTotalT1)
        this.lvGastos = findViewById(R.id.lvGastos)
        this.fabAddListaGasto = findViewById(R.id.fabAddListaGasto)
        this.fabAddListaGasto.setOnClickListener { salva() }
        this.Soma()
        this.Atualiza()


        this.lvGastos.setOnItemClickListener(OnItemClick())
        this.lvGastos.setOnItemLongClickListener(OnItemLongClick())



    }

    fun salva(){
        var editText = EditText(this)
        val input = AlertDialog.Builder(this)
        input.setMessage("Adicionar ")
        input.setTitle("Nome para Lista")
        input.setView(editText)
        input.setPositiveButton("Salvar", DialogInterface.OnClickListener { dialogInterface, i ->
            val descricao = editText.text.toString()
            val tipoGasto = TipoGasto(descricao)
            this.gastoDao.insert(tipoGasto)
            this.Atualiza()
        })
        input.setNegativeButton("Cancelar", null)
        input.create().show()
    }

    fun Atualiza(){
        val layout = android.R.layout.simple_list_item_1
        this.lvGastos.adapter = ArrayAdapter<TipoGasto>(this, layout,this.gastoDao.read())
        this.Soma()
    }
    fun Soma(){
        var total = 0.0F
        val gastos: MutableList<Item>
        gastos = this.itemDao.read()
        for(item  in gastos) {
            total = total + (item.preco * item.quantidade)
        }

        this.tvTotalT1.setText("R$"+" "+total.toString().replace(".",","))
    }

    inner class OnItemClick: AdapterView.OnItemClickListener{
        override fun onItemClick(adapter: AdapterView<*>?, view: View?, index: Int, id: Long) {
            val tipogasto = adapter?.getItemAtPosition(index) as TipoGasto

            val intent = Intent(this@MainActivity, TelaGasto::class.java).apply {
                putExtra("tipoGasto",tipogasto)
            }
            if(intent.resolveActivity(packageManager) != null ){
                startActivity(intent)
            }

          Toast.makeText(this@MainActivity, tipogasto?.getId().toString(), Toast.LENGTH_SHORT).show()
        }
    }

    inner class OnItemLongClick: AdapterView.OnItemLongClickListener{
        override fun onItemLongClick(adapter: AdapterView<*>?, view: View?, index: Int, id: Long): Boolean {
            val tipogasto = adapter?.getItemAtPosition(index) as TipoGasto
            // se faze dessa forma  this@MainActivity.gastoDao.delete(tipogasto) da esse erro  java.lang.StackOverflowError: stack size
            this@MainActivity.itemDao.deleteCascade(tipogasto.getId())
            this@MainActivity.gastoDao.delete(tipogasto.getId())

            val msg = "${tipogasto.getDescricao()}removido com sucesso!"
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            this@MainActivity.Atualiza()
            return true
        }
    }

    override fun onResume() {
        super.onResume()
        Atualiza()
    }



}