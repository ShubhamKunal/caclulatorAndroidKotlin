package com.example.calculator

import android.annotation.SuppressLint
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonsResultText: Map<Int,String>
    private val resultExpr = mutableListOf<Int>()
    private lateinit var result: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
        result.movementMethod = ScrollingMovementMethod()
        result.setRawInputType(InputType.TYPE_NULL)
        result.setTextIsSelectable(true)
        result.isSingleLine = false

        buttonsResultText = mapOf(
            R.id.button to "1",
            R.id.button2 to "2",
            R.id.button3 to "3",
            R.id.button4 to "4",
            R.id.button5 to "5",
            R.id.button6 to "6",
            R.id.button7 to "7",
            R.id.button8 to "8",
            R.id.button9 to "9",
            R.id.button10 to "0",
            R.id.button11 to "=",
            R.id.buttonAdd to "+",
            R.id.buttonSub to "-",
            R.id.buttonMul to "*",
            R.id.buttonDiv to "/",
            R.id.buttonPeriod to ".",
            R.id.buttonBr1 to "(",
            R.id.buttonBr2 to ")",
            R.id.buttonBack to "<",
        )
        listOf(
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button10,
            R.id.button11,
            R.id.buttonAdd,
            R.id.buttonSub,
            R.id.buttonMul,
            R.id.buttonDiv,
            R.id.buttonPeriod,
            R.id.buttonBr1,
            R.id.buttonBr2,
            R.id.buttonBack,

        ).map<Int, Button?>(this@MainActivity::findViewById).forEach {
                it!!.setOnClickListener(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList("resultExp", resultExpr as ArrayList<Int>)
        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        resultExpr.addAll(savedInstanceState.getIntegerArrayList("resultExp")!!)
    }

    private fun formatDouble(n: Double) = when (n.isFinite()) {
        true -> n.toBigDecimal().stripTrailingZeros().toPlainString()
        false -> n.toString()
    }

    @SuppressLint( "SetTextI18n")
    private fun renderResult(){
        val exprText = resultExpr
            .map(buttonsResultText::get)
            .joinToString(separator = "")
            .trim()

        val resultText = when (val result = evalResult()) {
            null -> exprText
            else -> "$exprText = ${formatDouble(result)}"
        }

        result.setText(resultText)
        result.setSelection(resultText.length)
    }

    private fun mapResultIdToExpr(id:Int) = when(id){
        R.id.button -> "1"
        R.id.button2 -> "2"
        R.id.button3 -> "3"
        R.id.button4 -> "4"
        R.id.button5 -> "5"
        R.id.button6 -> "6"
        R.id.button7 -> "7"
        R.id.button8 -> "8"
        R.id.button9 -> "9"
        R.id.button10 -> "0"
        R.id.buttonAdd -> "+"
        R.id.buttonSub -> "-"
        R.id.buttonMul -> "*"
        R.id.buttonDiv -> "/"
        R.id.buttonPeriod -> "."
        R.id.buttonBr1 -> "("
        R.id.buttonBr2 -> ")"
        else-> ""
    }

    private fun resultToExpr() =resultExpr.joinToString(separator = "", transform = this::mapResultIdToExpr)

    private fun evalResult() = eval(resultToExpr())

    override fun onClick(v: View?) {
        val id = v?.id ?: return
        when(id){
            R.id.buttonBack ->resultExpr.removeLastOrNull()
            else -> resultExpr.add(id)
        }
        renderResult()
    }
}