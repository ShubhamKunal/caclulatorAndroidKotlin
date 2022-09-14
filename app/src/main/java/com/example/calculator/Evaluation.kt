package com.example.calculator

private val parensExpression = Regex("""^(.*)\(([^()]+)\)(.*)$""")
private val mulDivExpression = Regex("""^(.+)([*/])(.+)$""")
private val plusMinusExpression = Regex("""^(.+)([+-])(.+)$""")

fun eval(expr:String):Double? = evalBraces(expr)

private fun evalBraces(expr: String):Double?{
    val match = parensExpression.find(expr)?: return evalplusMinusExpression(expr)
    return eval(match.groupValues[1] + eval(match.groupValues[2]) + match.groupValues[3])
}
private fun evalplusMinusExpression(expr: String): Double? {
    val match = plusMinusExpression.find(expr) ?: return evalmulDivExpression(expr)

    val a = eval(match.groupValues[1]) ?: return null
    val b = eval(match.groupValues[3]) ?: return null

    return when (match.groupValues[2]) {
        "+" -> a + b
        "-" -> a - b
        else -> null
    }
}
private fun evalmulDivExpression(expr: String): Double? {
    val match = mulDivExpression.find(expr) ?: return evalNumberExpr(expr)

    val a = eval(match.groupValues[1]) ?: return null
    val b = eval(match.groupValues[3]) ?: return null

    return when (match.groupValues[2]) {
        "*" -> a * b
        "/" -> {
//            if(b==0.0){null} else
            a / b}
        else -> null
    }
}

private fun evalNumberExpr(expr: String) = expr.toDoubleOrNull()
