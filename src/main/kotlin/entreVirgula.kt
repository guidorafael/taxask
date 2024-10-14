//fun main() {
//    // ler numero com vírgula no decimal e ponto delimitar milhar
//    val lido = readln()
//    //var lidoDouble = lido.toDouble()
//    //println(lidoDouble)
//
//    //var result = lido.replace(Regex("."), "")
//    // var result = lido.replace(Regex(","), ".")
//    var result = lido.replace(Regex("[a-z]"), "")
//    result = result.replace(Regex("""\."""), "")
//    result = result.replace(Regex(""","""), ".")
//
//    println(tiravirgula(lido))
//
//    val tt = "tt"
//    println(validar(tt))
//}

fun tiravirgula(lido: String): Double {
    var result = lido.replace(Regex("[a-z]"), "")
    result = result.replace(Regex("""\."""), "")
    result = result.replace(Regex(""","""), ".")
    return result.toDouble()

}


fun validar(param: String): String {
    var isValid = false
    var number: String

    do {
        print("Please enter a number: ")
        number = readLine() ?: ""

        // Check if the input is numeric using regex
        if (number.matches(Regex("\\d+"))) {
            isValid = true
        } else {
            println("Invalid input. Please enter a valid number.")
        }
    } while (!isValid)

    println("You entered the valid number: $number")
    return param
}


fun validar2(){
    var isValid = false
    var number: String

    do {
        print("Please enter a number: ")
        number = readLine() ?: ""

        // Check if the input is a valid number format, allowing commas
        if (number.matches(Regex("^\\d{1,3}(,\\d{3})*(\\.\\d+)?$"))) {
            isValid = true
        } else {
            println("Invalid input. Please enter a valid number format.")
        }
    } while (!isValid)

    // Replace commas with dots for consistent formatting, if needed
    val normalizedNumber = number.replace(",", "")
    println("You entered the valid number: $normalizedNumber")
}