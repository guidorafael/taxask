package org.example
import kotlin.math.abs

const val sversion = "v0.06"  // 04-06-2023 - v0.06 data entry - method for data entry cabec


class linhaType(
    // linha de uma nota de corretagem
    var cvlin: String = "C",      // 0  "C" ou "V" compra ou venda na linha         - compra (débito) sera negativo  venda (crédito) sera positivo
    var pplin: String = "XXXX11", // 1  ativo ("PaPel") transacionado ns linha
    var qtlin: Int = 1,           // 2  quantidade do ativo na linha                - herda o sinal de C/V "-" na compra e "+" na venda
    var unlin: Double = 100.11,   // 3  preço medio da aquisição (unitario da linha)- sempre positivo
    var eplin: Double = -4.50,    // 4  coRRetagem específica deste papel na linha  - SEMPRE NEGATIVA conhece o valor - fora do calculo ponderado
    var txlin: Double = 0.0,      // 5  taxa desta transac. deste ativo             - SEMPRE NEGATIVA a PRÓPRIA INCOGNITA a obter através de ponderação
    var ttlin: Double = 0.0,      // 6  custo deste ativo na linha                  - positivo (credito) na venda   negativo (débito) na compra
) {
    override fun toString() =
        "C/V: $cvlin  " + "Papel: $pplin  " + "Quantidade: $qtlin  " +
                "Valor Unitário: $unlin  " + "Corretagem Necton: $eplin  " +
                "Taxa ponderada: $txlin  " + "Preço  $ttlin"
}

class cabecType(
    var corretora: String = "....",
    var notaCorr: String = "000",
    var dataPregao: String = "ontem",
    var ttCredDeb: String = "C",
    var ttVlFmAbs: Double = 0.0,
    var ttVlFmSnl: Double = 0.0,
    var quantosItens: Int = 2,
    var ttAbsCompras: Double = 0.0,
    var ttAbsVendas: Double = 0.0,
    var tBruto: Double = 0.0,
    var ttTaxas: Double = 0.0,
    var fatorTx: Double = 0.0,
) {
    override fun toString() =
        "Corretora:$corretora  " +
                "Nota:$notaCorr  " +
                "Pregão:$dataPregao  " +
                "Cred./Déb.:$ttCredDeb  " +
                "TtNota:${ttVlFmAbs}  " +
                "TtNtsnl:${ttVlFmSnl}  " +
                "\nItens:$quantosItens  " +
                "Compras:$ttAbsCompras  " +
                "Vendas:$ttAbsVendas  " +
                "\nTt.Bruto:$tBruto  " +
                "Tt.Taxas:$ttTaxas  " +
                "fator Tx.:$fatorTx  "

    fun prepara() {
        tBruto = ttAbsVendas - ttAbsCompras
        ttTaxas = -1 * abs((abs(ttVlFmAbs) - abs(tBruto)))
        fatorTx = if (ttAbsVendas + ttAbsCompras != 0.0) (ttTaxas) / (ttAbsVendas + ttAbsCompras) else 0.0  // sempre negativo
        println("guido prepara tBruto $tBruto   ttTaxas $ttTaxas     fatorTx $fatorTx")
    }
}


fun enterHead(): cabecType {
    println("=============================================")
    println("corretora:")
    val corretora = readln().uppercase()

    println("nota de corretagem:")
    val numNotaCorr = readln()

    println("data do pregão dd/mm/aaaa:")
    val dataPregao = readln()

    println("para o Valor Final da Nota indique Crédito (receber \$) ou  Débito (vai pagar \$) ")
    println("'C' para crédito (vai receber \$) ou 'D' para débito:")
    var ttCredDeb =
        readln().uppercase()  // "C" ou "D" Crédito ou Débito respectivamente (recupera dinheiro ou investe dinheiro na operação desta linha)
    while (ttCredDeb !in "CD") {
        println("A T E N Ç Ã O - entre com  'C'  ou  'D'  para indicar recebimento ou investimento respectivamente:")
        ttCredDeb = readln().uppercase()
    }

    println("entre o Valor Final da Nota (desnecessário o sinal):")
    val ttVlFmAbs = abs(readln().toDouble())
    val ttVlFmSnl = if (ttCredDeb == "D") -1 * ttVlFmAbs else ttVlFmAbs

    println("quantos itens a lançar?")
    val quantosItens = readln().toInt()

    return cabecType(corretora, numNotaCorr, dataPregao, ttCredDeb, ttVlFmAbs, ttVlFmSnl, quantosItens)
}

fun entLinDet(theHead: cabecType): linhaType {

    println("entre com 'C' compra ou 'V' venda:")
    var cvlin =
        readln().uppercase()  // "C" ou "V" compra ou venda na linha         - compra (débito) sera negativo  venda (crédito) sera positivo
    while (cvlin !in "CV") {
        println("ATENÇÃO - entre com  'C'  ou  'V'  para o indicador de Compra ou Venda respectivamente:")
        cvlin = readln().uppercase()
    }

    println("entre com o ativo / papel negociado:")
    val pplin = readln().uppercase()  // ativo ("PaPel") transacionado ns linha

    println("entre com a quantidade negociada - inteiro:")
    //println("(quantidade leva o sinal algébrico de \"C\" \"-\" ou \"V\" \"+\")")   sintaxe pode ser útil
    var qtlin =
        abs(readln().toInt()) // quantidade do ativo na linha                - herda o sinal de C/V "-" na compra e "+" na venda
    if (cvlin == "C") qtlin = -1 * qtlin

    println("entre com o valor unitario - ponto decimal e não virgula - duas casas:")
    val unlin = abs(readln().toDouble())   // preço medio da aquisição (unitario da linha)- sempre positivo

    var eplin = 0.0
    var eplinTEMP: String? = ""
    if (theHead.corretora != "INTER") {
        println("Se NECTON e se ação entao 4.50 senão zero (<enter>):")

        /**
         * The value null represents the absence of any object,
         * while the empty string is an object of type String with zero characters.
         * If you try to compare the two, they are not the same.
         *
         * The !! operator is particularly useful when you are confident that a value
         * is not null and there’s no chance of getting an NPE,
         * but the compiler cannot guarantee this due to certain rules.
         * In such cases, you can use the !! operator to explicitly
         * tell the compiler that the value is not null.
         *
         * Análise do código alertou para o seguinte:
         *     eplinTEMP = "" é diferente (sempre true) de perguntar
         *     eplinTEMP.isEmpty()
         *
         * https://kotlinlang.org/docs/null-safety.html#elvis-operator
         */

        //    eplin =
        //        readln().toDouble()   // coRRetagem específica deste papel na linha  - SEMPRE NEGATIVA conhece o valor - fora do calculo ponderado

        //    eplin = readln().toDouble()
        //    eplin = if(eṕlin != null) eplin else 0  //val l: Int = if (b != null) b.length else 0

        eplinTEMP = readln()
        eplin = if(eplinTEMP == null || eplinTEMP.isEmpty()) 0.0 else eplinTEMP.toDouble()

    }

    /*  "Compras:$ttAbsCompras  " +
        "Vendas:$ttAbsVendas  " +    */

    if (cvlin == "C") theHead.ttAbsCompras += abs(qtlin * unlin) else theHead.ttAbsVendas += abs(qtlin * unlin)

    return linhaType(cvlin, pplin, qtlin, unlin, eplin)
}


fun main() {
    val theHead: cabecType = enterHead()
    println(theHead)

    var listLinDet = mutableListOf<linhaType>()

    var i = 0
    repeat(theHead.quantosItens) {
        println("============< ${++i} / ${theHead.quantosItens} >============")
        listLinDet.add(entLinDet(theHead))
    }

    theHead.prepara()
    println(theHead)

    listLinDet.forEach {
        it.txlin = abs(it.qtlin) * it.unlin * theHead.fatorTx // fatorTx é negativo e fracionario
        it.ttlin = it.qtlin * it.unlin + it.txlin
        println(it)
    }

    println("==== acima esta correto ? ====")

    println("============================= taxa de cada papel: ==================$sversion=======") // a versão
    println("Corretora:${theHead.corretora}  Nota:${theHead.notaCorr}  Pregão:${theHead.dataPregao}  Total c/sinal:${theHead.ttVlFmSnl}  Itens:${theHead.quantosItens}")

    println("lin ativo    C/V  qtd  unitario  qtd X unt especifica    taxa      preço")
    for (i in 0 until listLinDet.size) {

        println(String.format("%2d %7s %4s %5d %9.2f %9.2f %9.2f %9.2f %10.2f",
            (i + 1),
            listLinDet[i].pplin,
            listLinDet[i].cvlin,
            listLinDet[i].qtlin,
            listLinDet[i].unlin,
            listLinDet[i].qtlin*listLinDet[i].unlin,
            listLinDet[i].eplin,
            listLinDet[i].txlin,
            listLinDet[i].ttlin,
        ))
    }
    println(" ")

    var somataxas = 0.0
    var sumttlnota = 0.0
    for (i in 0 until listLinDet.size) {
        somataxas += listLinDet[i].txlin
        sumttlnota += listLinDet[i].ttlin
    }
    println(String.format("tot.taxas: %5.2f   sumtx: %5.2f   vl.liq.operac.: %9.2f\n", theHead.ttTaxas, somataxas, sumttlnota))

    println("guido sumttlnota $sumttlnota  ttVlFmSnl ${theHead.ttVlFmSnl}  > 0.005")
    if (abs(sumttlnota - theHead.ttVlFmSnl) > 0.005) {
        println("* * * * * algo errado - sumttlnota: $sumttlnota  * * * * * ")
        println(theHead)
    }


} // fim do main

/**
 * =============================================
 * corretora:
 * inter
 * nota de corretagem:
 * 777777
 * data do pregão dd/mm/aaaa:
 * 10/10/2024
 * para o Valor Final da Nota indique Crédito (receber $) ou  Débito (vai pagar $)
 * 'C' para crédito (vai receber $) ou 'D' para débito:
 * c
 * entre o Valor Final da Nota (desnecessário o sinal):
 * 590
 * quantos itens a lançar?
 * 2
 * Corretora:INTER  Nota:777777  Pregão:10/10/2024  Cred./Déb.:C  TtNota:590.0  TtNtsnl:590.0
 * Itens:2  Compras:0.0  Vendas:0.0
 * Tt.Bruto:0.0  Tt.Taxas:0.0  fator Tx.:0.0
 * ============< 1 / 2 >============
 * entre com 'C' compra ou 'V' venda:
 * v
 * entre com o ativo / papel negociado:
 * hglg11
 * entre com a quantidade negociada - inteiro:
 * 10
 * entre com o valor unitario - ponto decimal e não virgula - duas casas:
 * 30.0
 * ============< 2 / 2 >============
 * entre com 'C' compra ou 'V' venda:
 * v
 * entre com o ativo / papel negociado:
 * mxrf11
 * entre com a quantidade negociada - inteiro:
 * 30
 * entre com o valor unitario - ponto decimal e não virgula - duas casas:
 * 10.0
 * guido prepara tBruto 600.0   ttTaxas -10.0     fatorTx -0.016666666666666666
 * Corretora:INTER  Nota:777777  Pregão:10/10/2024  Cred./Déb.:C  TtNota:590.0  TtNtsnl:590.0
 * Itens:2  Compras:0.0  Vendas:600.0
 * Tt.Bruto:600.0  Tt.Taxas:-10.0  fator Tx.:-0.016666666666666666
 * C/V: V  Papel: HGLG11  Quantidade: 10  Valor Unitário: 30.0  Corretagem Necton: 0.0  Taxa ponderada: -5.0  Preço  295.0
 * C/V: V  Papel: MXRF11  Quantidade: 30  Valor Unitário: 10.0  Corretagem Necton: 0.0  Taxa ponderada: -5.0  Preço  295.0
 * ==== acima esta correto ? ====
 * ============================= taxa de cada papel: ==================v0.06=======
 * Corretora:INTER  Nota:777777  Pregão:10/10/2024  Total c/sinal:590.0  Itens:2
 * lin ativo    C/V  qtd  unitario  qtd X unt especifica    taxa      preço
 *  1  HGLG11    V    10     30.00    300.00      0.00     -5.00     295.00
 *  2  MXRF11    V    30     10.00    300.00      0.00     -5.00     295.00
 *
 * tot.taxas: -10.00   sumtx: -10.00   vl.liq.operac.:    590.00
 *
 * guido sumttlnota 590.0  ttVlFmSnl 590.0  > 0.005
 *
 * Process finished with exit code 0
 *
 *
 *               outro:
 * corretora:
 * inter
 * nota de corretagem:
 * 777777
 * data do pregão dd/mm/aaaa:
 * 10/10/2024
 * para o Valor Final da Nota indique Crédito (receber $) ou  Débito (vai pagar $)
 * 'C' para crédito (vai receber $) ou 'D' para débito:
 * c
 * entre o Valor Final da Nota (desnecessário o sinal):
 * 600.00
 * quantos itens a lançar?
 * 3
 * guido prepara tBruto 602.4   ttTaxas -2.3999999999999773     fatorTx -0.003984063745019883
 * Corretora:INTER  Nota:777777  Pregão:10/10/2024  Cred./Déb.:C  TtNota:600.0  TtNtsnl:600.0
 * Itens:3  Compras:0.0  Vendas:602.4
 * Tt.Bruto:602.4  Tt.Taxas:-2.3999999999999773  fator Tx.:-0.003984063745019883
 * C/V: V  Papel: HGLG11  Quantidade: 20  Valor Unitário: 10.05  Corretagem Necton: 0.0  Taxa ponderada: -0.8007968127489965  Preço  200.19920318725102
 * C/V: V  Papel: MXRF11  Quantidade: 10  Valor Unitário: 20.04  Corretagem Necton: 0.0  Taxa ponderada: -0.7984063745019845  Preço  199.601593625498
 * C/V: V  Papel: IRDM11  Quantidade: 2  Valor Unitário: 100.5  Corretagem Necton: 0.0  Taxa ponderada: -0.8007968127489965  Preço  200.19920318725102
 * ==== acima esta correto ? ====
 * ============================= taxa de cada papel: ==================v0.06=======
 * Corretora:INTER  Nota:777777  Pregão:10/10/2024  Total c/sinal:600.0  Itens:3
 * lin ativo    C/V  qtd  unitario  qtd X unt especifica    taxa      preço
 *  1  HGLG11    V    20     10.05    201.00      0.00     -0.80     200.20
 *  2  MXRF11    V    10     20.04    200.40      0.00     -0.80     199.60
 *  3  IRDM11    V     2    100.50    201.00      0.00     -0.80     200.20
 *
 * tot.taxas: -2.40   sumtx: -2.40   vl.liq.operac.:    600.00
 *
 * guido sumttlnota 600.0  ttVlFmSnl 600.0  > 0.005
 *
 * Process finished with exit code 0
 *
 */

/**     TODO
 *      1) quantidade com ponto decimal - impedir erro
 *         at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
 *
 *      
 */
