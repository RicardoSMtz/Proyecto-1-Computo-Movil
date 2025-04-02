package com.example.ejercicio1

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercicio1.databinding.ActivityMainBinding
import kotlin.math.sqrt

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
        val formulas = arrayOf(
            getString(R.string.string_chicharronera),
            getString(R.string.area_triangulo),
            getString(R.string.velocidad),
            getString(R.string.perimetro_rectangulo)
        )

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, formulas)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner4.adapter = spinnerAdapter

        binding.spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val imageResource = when (position) {
                    0 -> R.drawable.chicharronera
                    1 -> R.drawable.triangulo
                    2 -> R.drawable.velocidad
                    3 -> R.drawable.perimetro
                    else -> R.drawable.chicharronera
                }
                binding.imgformula.setImageResource(imageResource)
                mostrarCampos(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnCalcular.setOnClickListener {
            val seleccion = binding.spinner4.selectedItem.toString()

            when (seleccion) {
                getString(R.string.string_chicharronera) -> {
                    if (validarCamposVacios(3)) {
                        val a = binding.Var1.text.toString().toInt()
                        val b = binding.Var2.text.toString().toInt()
                        val c = binding.Var3.text.toString().toInt()
                        val resultado = formGeneral(a, b, c)
                        binding.tvResult.text = resultado
                    }
                }
                getString(R.string.area_triangulo) -> {
                    if (validarCamposVacios(2)) {
                        val base = binding.Var1.text.toString().toInt()
                        val altura = binding.Var2.text.toString().toInt()
                        val resultado = areaTriangulo(base, altura)
                        binding.tvResult.text = getString(R.string.displayarea, resultado)
                    }
                }
                getString(R.string.velocidad) -> {
                    if (validarCamposVacios(2)) {
                        val distancia = binding.Var1.text.toString().toInt()
                        val tiempo = binding.Var2.text.toString().toInt()
                        val resultado = calcularVelocidad(distancia, tiempo)
                        binding.tvResult.text = getString(R.string.displayVelocidad, resultado)
                    }
                }
                getString(R.string.perimetro_rectangulo) -> {
                    if (validarCamposVacios(2)) {
                        val lado1 = binding.Var1.text.toString().toInt()
                        val lado2 = binding.Var2.text.toString().toInt()
                        val resultado = perimetroRectangulo(lado1, lado2)
                        binding.tvResult.text = getString(R.string.displayPerimetro, resultado)
                    }
                }
            }
        }
    }

    private fun mostrarCampos(position: Int) {
        binding.LayoutChicarronera.visibility = View.VISIBLE

        when (position) {
            0 -> { // Fórmula general
                binding.Var1.hint = getString(R.string.coeficientea)
                binding.Var2.hint = getString(R.string.coeficienteb)
                binding.Var3.hint = getString(R.string.coeficientec)
                binding.a.text = getString(R.string.adospuntos)
                binding.b.text = getString(R.string.bdospuntos)
                binding.Var3.visibility = View.VISIBLE
                binding.c.visibility = View.VISIBLE
                binding.Var3.isEnabled = true // Deshabilitar Var3 ya que no es necesario
            }
            1 -> { // Área del triángulo
                binding.Var1.hint = getString(R.string.basee)
                binding.Var2.hint = getString(R.string.alturaa)
                binding.a.text = getString(R.string.bdospuntos)
                binding.b.text = getString(R.string.hdospuntos)
                binding.Var3.visibility = View.INVISIBLE
                binding.c.visibility = View.INVISIBLE
                binding.Var3.isEnabled = false // Deshabilitar Var3 ya que no es necesario
            }
            2 -> { // Velocidad
                binding.Var1.hint = getString(R.string.distanciaa)
                binding.Var2.hint = getString(R.string.tiempoo) //Tiempo
                binding.a.text = getString(R.string.ddospuntos) //d
                binding.b.text = getString(R.string.tdospuntos)//t
                binding.Var3.visibility = View.INVISIBLE
                binding.c.visibility = View.INVISIBLE
                binding.Var3.isEnabled = false // Deshabilitar Var3 ya que no es necesario
            }
            3 -> { // Perímetro del rectángulo
                binding.Var1.hint = getString(R.string.lado1) //L1
                binding.Var2.hint = getString(R.string.lado2) //L2
                binding.a.text = getString(R.string.ele1)//"l1:"
                binding.b.text = getString(R.string.ele2)//"l2:"
                binding.Var3.visibility = View.INVISIBLE
                binding.c.visibility = View.INVISIBLE
                binding.Var3.isEnabled = false // Deshabilitar Var3 ya que no es necesario
             }
         }
     }
    private fun validarCamposVacios(numCampos: Int): Boolean {
        var valido = true

        // Validar Var1
        if (binding.Var1.visibility == View.VISIBLE && binding.Var1.text.toString().isEmpty()) {
            binding.Var1.error = getString(R.string.requierenum)
            binding.Var1.requestFocus()
            valido = false
        } else if (binding.Var1.visibility == View.VISIBLE && binding.Var1.text.toString().toIntOrNull() == 0) {
            binding.Var1.error = getString(R.string.difcero)
            binding.Var1.requestFocus()
            valido = false
         }

        // Validar Var2
        if (binding.Var2.visibility == View.VISIBLE && binding.Var2.text.toString().isEmpty()) {
            binding.Var2.error = getString(R.string.requierenum)
            binding.Var2.requestFocus()
            valido = false
        } else if (binding.Var2.visibility == View.VISIBLE && binding.Var2.text.toString().toIntOrNull() == 0) {
            binding.Var2.error = getString(R.string.difcero)
            binding.Var2.requestFocus()
            valido = false
         }

        // Validar Var3
        if (numCampos == 3 && binding.Var3.visibility == View.VISIBLE && binding.Var3.text.toString().isEmpty()) {
            binding.Var3.error = getString(R.string.requierenum)
            binding.Var3.requestFocus()
            valido = false
        } else if (numCampos == 3 && binding.Var3.visibility == View.VISIBLE && binding.Var3.text.toString().toIntOrNull() == 0) {
            binding.Var3.error = getString(R.string.difcero)
            binding.Var3.requestFocus()
            valido = false
         }
        return valido

    }


    //Función para calcular fórmula general
    private fun formGeneral(a: Int, b: Int, c: Int): String {
        val discriminante = b * b - 4 * a * c
        return if (discriminante < 0) getString(R.string.solucionesreales)
        else {
            val raiz1 = (-b + sqrt(discriminante.toDouble())) / (2 * a)
            val raiz2 = (-b - sqrt(discriminante.toDouble())) / (2 * a)
            getString(R.string.displayChicarronera, raiz1, raiz2)
        }
    }
    //Función para calcular el área del triángulo
    private fun areaTriangulo(base: Int, altura: Int) = (base * altura) / 2.0
    //Función para calcular velocidad
    private fun calcularVelocidad(distancia: Int, tiempo: Int) = distancia / tiempo.toDouble()
    //Función para perímetro
    private fun perimetroRectangulo(lado1: Int, lado2: Int) = 2 * (lado1 + lado2)

}
