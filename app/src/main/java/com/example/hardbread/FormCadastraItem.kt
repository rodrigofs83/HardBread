package com.example.hardbread

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar

class FormCadastraItem : AppCompatActivity() {
    private lateinit var tvTitulo: TextView
    private lateinit var etNome: EditText
    private lateinit var etPreco: EditText
    private lateinit var etQuantidade: EditText
    private lateinit var btFormSalva: Button
    private lateinit var itemDao: ItemDao
    private var tipo: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastra_item)
        Log.i("MainActivity","erro" )
        this.tvTitulo = findViewById(R.id.tvTitulo)
        this.etNome = findViewById(R.id.nomeItem)
        this.etPreco =  findViewById(R.id.etPreco)
        this.etQuantidade = findViewById(R.id.quantidadeItem)
        this.btFormSalva = findViewById(R.id.btSalvar)
        this.itemDao = ItemDao(this)



        if (intent.hasExtra("tipoGasto")) {
            var tipoGasto = intent.getSerializableExtra("tipoGasto") as TipoGasto
            this.tvTitulo.setText(tipoGasto.getDescricao())
            this.tipo = tipoGasto.getId()
            Toast.makeText(this@FormCadastraItem, tipoGasto.getId().toString(), Toast.LENGTH_SHORT).show()
        }
        this.btFormSalva.setOnClickListener{SalveForm()}
    }
    fun SalveForm() {
        val nome = this.etNome.text.toString()
        val  valor = this.etPreco.text.toString().toFloat()
        val quantidade = this.etQuantidade.text.toString().toInt()
        val fk = this.tipo
        val itens = Item(nome,valor,quantidade,fk)
        this.itemDao.insert(itens)
        setResult(RESULT_OK, intent)
        var msg = "cadrastrado"
        Toast.makeText(this, msg+fk.toString(), Toast.LENGTH_SHORT).show()
        finish()

    }

}





