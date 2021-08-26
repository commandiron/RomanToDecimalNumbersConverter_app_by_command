package com.demirli.a27romantodecimalnumbersconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var flagForMistake = false
    private lateinit var romanNumeral: String
    private lateinit var romanNumeralMap: Map<Char,Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roman_et.filters = arrayOf(object: InputFilter.AllCaps() {
        })

        convert_btn.setOnClickListener {
            flagForMistake = false
            romanNumeral = roman_et.text.toString()
            romanNumeralMap = mapOf<Char,Int>(
                Pair('I',1),
                Pair('V',5),
                Pair('X',10),
                Pair('L',50),
                Pair('C',100),
                Pair('D',500),
                Pair('M',1000)
            )

            controlRomanNumeral()

            calculateToDecimal()

        }
    }

    fun controlRomanNumeral(){
        val reversedromanNumeral = romanNumeral.reversed()
        for (i in 0 until romanNumeral.length){
            if(i + 1 < romanNumeral.length && romanNumeralMap[reversedromanNumeral[i]]!! > romanNumeralMap[reversedromanNumeral[i+1]]!!
                && i + 2 < romanNumeral.length && romanNumeralMap[reversedromanNumeral[i+1]]!! == romanNumeralMap[reversedromanNumeral[i+2]]!!){
                Toast.makeText(this,"Wrong roman numeral", Toast.LENGTH_SHORT).show()
                decimal_tv.setText("")
                flagForMistake = true
            }
        }
        val regexForRomanNumeral = "^(?=.*([I]{4}|[V]{4}|[X]{4}|[L]{4}|[C]{4}|[D]{4}|[M]{4}|VX{1}|LC{1}|DM{1}|VV{1}|LL{1}|DD{1}))".toRegex()
        if (regexForRomanNumeral.containsMatchIn(romanNumeral)){
            Toast.makeText(this,"Wrong roman numeral", Toast.LENGTH_SHORT).show()
            decimal_tv.setText("")
            flagForMistake = true
        }
    }

    fun calculateToDecimal(){
        if(flagForMistake == false){
            var toDecimal = 0
            if (romanNumeral.length != 0){
                for (i in 0 until romanNumeral.length){
                    if (i + 1 < romanNumeral.length && romanNumeralMap[romanNumeral[i]]!! < romanNumeralMap[romanNumeral[i+1]]!!){
                        toDecimal -= romanNumeralMap[romanNumeral[i]]!!
                    }else{
                        toDecimal += romanNumeralMap[romanNumeral[i]]!!
                    }
                }
            }else{
                Toast.makeText(this,"Wrong roman numeral", Toast.LENGTH_SHORT).show()
            }
            if (toDecimal == 0){
                decimal_tv.setText("")
            }else{
                decimal_tv.setText(toDecimal.toString())
            }
        }
    }
}
