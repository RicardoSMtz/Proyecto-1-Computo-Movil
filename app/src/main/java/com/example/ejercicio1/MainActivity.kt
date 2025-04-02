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
                        binding.tvResult.text = "Área: $resultado"
                    }
                }
                getString(R.string.velocidad) -> {
                    if (validarCamposVacios(2)) {
                        val distancia = binding.Var1.text.toString().toInt()
                        val tiempo = binding.Var2.text.toString().toInt()
                        val resultado = calcularVelocidad(distancia, tiempo)
                        binding.tvResult.text = "Velocidad: $resultado"
                    }
                }
                getString(R.string.perimetro_rectangulo) -> {
                    if (validarCamposVacios(2)) {
                        val lado1 = binding.Var1.text.toString().toInt()
                        val lado2 = binding.Var2.text.toString().toInt()
                        val resultado = perimetroRectangulo(lado1, lado2)
                        binding.tvResult.text = "Perímetro: $resultado"
                    }
                }
            }
        }
    }

    private fun mostrarCampos(position: Int) {
        binding.LayoutChicarronera.visibility = View.VISIBLE

        when (position) {
            0 -> { // Fórmula general
                binding.Var1.hint = "Coeficiente a"
                binding.Var2.hint = "Coeficiente b"
                binding.Var3.hint = "Coeficiente c"
                binding.a.text = "a:"
                binding.b.text = "b:"
                binding.Var3.visibility = View.VISIBLE
                binding.c.visibility = View.VISIBLE
                binding.Var3.isEnabled = true // Deshabilitar Var3 ya que no es necesario
                // Deshabilitar campos no necesarios
            }
            1 -> { // Área del triángulo
                binding.Var1.hint = "Base"
                binding.Var2.hint = "Altura"
                binding.a.text = "b:"
                binding.b.text = "h:"
                binding.Var3.visibility = View.INVISIBLE
                binding.c.visibility = View.INVISIBLE
                binding.Var3.isEnabled = false // Deshabilitar Var3 ya que no es necesario
            }
            2 -> { // Velocidad
                binding.Var1.hint = "Distancia"
                binding.Var2.hint = "Tiempo"
                binding.a.text = "d:"
                binding.b.text = "t:"
                binding.Var3.visibility = View.INVISIBLE
                binding.c.visibility = View.INVISIBLE
                binding.Var3.isEnabled = false // Deshabilitar Var3 ya que no es necesario
            }
            3 -> { // Perímetro del rectángulo
                binding.Var1.hint = "Lado 1"
                binding.Var2.hint = "Lado 2"
                binding.a.text = "l1:"
                binding.b.text = "l2:"
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
            binding.Var1.error = "Se requiere un número "
            binding.Var1.requestFocus()
            valido = false
        } else if (binding.Var1.visibility == View.VISIBLE && binding.Var1.text.toString().toIntOrNull() == 0) {
            binding.Var1.error = "Debe ser diferente de 0"
            binding.Var1.requestFocus()
            valido = false
        }

        // Validar Var2
        if (binding.Var2.visibility == View.VISIBLE && binding.Var2.text.toString().isEmpty()) {
            binding.Var2.error = "Se requiere un número"
            binding.Var2.requestFocus()
            valido = false
        } else if (binding.Var2.visibility == View.VISIBLE && binding.Var2.text.toString().toIntOrNull() == 0) {
            binding.Var2.error = "Debe ser diferente de 0"
            binding.Var2.requestFocus()
            valido = false
        }

        // Validar Var3
        if (numCampos == 3 && binding.Var3.visibility == View.VISIBLE && binding.Var3.text.toString().isEmpty()) {
            binding.Var3.error = "Se requiere un número"
            binding.Var3.requestFocus()
            valido = false
        } else if (numCampos == 3 && binding.Var3.visibility == View.VISIBLE && binding.Var3.text.toString().toIntOrNull() == 0) {
            binding.Var3.error = "Debe ser diferente de 0"
            binding.Var3.requestFocus()
            valido = false
        }

        return valido
    }


    private fun formGeneral(a: Int, b: Int, c: Int): String {
        val discriminante = b * b - 4 * a * c
        return if (discriminante < 0) "No hay soluciones reales"
        else {
            val raiz1 = (-b + sqrt(discriminante.toDouble())) / (2 * a)
            val raiz2 = (-b - sqrt(discriminante.toDouble())) / (2 * a)
            "Raíz 1: ${"%.2f".format(raiz1)}, Raíz 2: ${"%.2f".format(raiz2)}"
        }
    }

    private fun areaTriangulo(base: Int, altura: Int) = (base * altura) / 2.0
    private fun calcularVelocidad(distancia: Int, tiempo: Int) = distancia / tiempo.toDouble()
    private fun perimetroRectangulo(lado1: Int, lado2: Int) = 2 * (lado1 + lado2)
}
