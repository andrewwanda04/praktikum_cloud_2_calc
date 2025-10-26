package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var et1: EditText
    private lateinit var et2: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnSub: Button
    private lateinit var btnMul: Button
    private lateinit var btnDiv: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1 = findViewById(R.id.etNumber1)
        et2 = findViewById(R.id.etNumber2)
        tvResult = findViewById(R.id.tvResult)
        btnAdd = findViewById(R.id.btnAdd)
        btnSub = findViewById(R.id.btnSub)
        btnMul = findViewById(R.id.btnMul)
        btnDiv = findViewById(R.id.btnDiv)

        btnAdd.setOnClickListener { calculate('+') }
        btnSub.setOnClickListener { calculate('-') }
        btnMul.setOnClickListener { calculate('*') }
        btnDiv.setOnClickListener { calculate('/') }
    }

    private fun calculate(op: Char) {
        val num1Text = et1.text.toString()
        val num2Text = et2.text.toString()

        if (num1Text.isEmpty() || num2Text.isEmpty()) {
            Toast.makeText(this, "Masukkan kedua angka terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = num1Text.toDouble()
        val num2 = num2Text.toDouble()
        var result = 0.0

        when (op) {
            '+' -> result = num1 + num2
            '-' -> result = num1 - num2
            '*' -> result = num1 * num2
            '/' -> {
                if (num2 == 0.0) {
                    Toast.makeText(this, "Tidak bisa membagi dengan nol", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    result = num1 / num2
                }
            }
        }

        tvResult.text = "Hasil: $result"
    }
}
