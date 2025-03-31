package com.example.ejercicio1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercicio1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        //Opciones spinner

            val formulas = arrayOf(
                getString(R.string.string_chicharronera),
                getString(R.string.area_triangulo),
                getString(R.string.velocidad), getString(R.string.perimetro_rectangulo)
            )
            // Spinner Formulas
            val SpinnerOpt = ArrayAdapter(this, android.R.layout.simple_spinner_item, formulas)
            SpinnerOpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner4.adapter = SpinnerOpt
            //Opciones spinner
            //binding.spinner4.setOnClickListener {
            //    binding.IngresoVariables.visibility = View.VISIBLE
            //}

            //Colocar Imagen dependiendo de formula
            binding.spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val imageResource = when (position) {
                        0 -> R.drawable.chicharronera

                        1 -> R.drawable.triangulo
                        2 -> R.drawable.velocidad
                        3 -> R.drawable.perimetro
                        else -> { -> R.drawable.chicharronera }
                    }
                    binding.imgformula.setImageResource(imageResource as Int)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        binding.IngresoVariables.visibility = View.INVISIBLE
        binding.tvEncabezado.setOnClickListener() {
            binding.IngresoVariables.visibility = View.VISIBLE
        }
        }



}