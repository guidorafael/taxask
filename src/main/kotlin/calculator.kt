/*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CalculatorTest {

    @Test
    fun testAddition() {
        val calculator = Calculator()
        val result = calculator.add(2, 3)
        assertEquals(5, result)
    }

    @Test
    fun testSubtraction() {
        val calculator = Calculator()
        val result = calculator.subtract(5, 3)
        assertEquals(2, result)
    }

    @Test
    fun testMultiplication() {
        val calculator = Calculator()
        val result = calculator.multiply(2, 3)
        assertEquals(6, result)
    }

    @Test
    fun testDivision() {
        val calculator = Calculator()
        val result = calculator.divide(6, 3)
        assertEquals(2, result)
    }

    @Test
    fun testDivisionByZero() {
        val calculator = Calculator()
        try {
            calculator.divide(6, 0)
        } catch (e: ArithmeticException) {
            assertTrue(e.message!!.contains("divide by zero"))
        }
    }
}


class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun subtract(a: Int, b: Int): Int {
        return a - b
    }

    fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    fun divide(a: Int, b: Int): Int {
        if (b == 0) {
            throw ArithmeticException("Cannot divide by zero")
        }
        return a / b
    }
}

 */