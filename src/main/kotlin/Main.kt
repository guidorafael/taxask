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
    if (theHead.corretora != "INTER") {
        println("Se NECTON e se ação entao 4.50 senão zero:")
        eplin =
            readln().toDouble()   // coRRetagem específica deste papel na linha  - SEMPRE NEGATIVA conhece o valor - fora do calculo ponderado
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


} // fim do main ! ==================================================================================================


/*

	/************************************************************************************************************
	*                             total de nota só de compras é negativo
	*                   A T E N Ç Ã O === A T E N Ç Ã O === A T E N Ç Ã O === A T E N Ç Ã O ===
	*                            liquido para daqui a dois dias úteis COM SINAL ! ! ! ! ! ! ! ! !
	*
	*             CREDITO(+venda) ou DEBITO(-compra) líquido a ser lançado na conta de investimento -
	*
	*         vlLiqFinalNota é o ultimo valor da nota canto inferior direito - é o que entra ou sai da conta.
	*                                   trecho para carga do slice nota
	*
	*************************************************************************************************************/
*/


/*
=============================================
corretora:
INTER
nota de corretagem:
5555
data do pregão dd/mm/aaaa:
11/11/2024
total com sinal '-' para débito
-100
quantos itens a lançar?
2
Corretora:INTER  Nota:5555  Pregão:11/11/2024  Total c/sinal:-100.0  Itens:2
   próximo ============< 1 / 2 >===================
entre com 'C' compra ou 'V' venda:
c
entre com o ativo / papel negociado:
tt1
entre com a quantidade negociada - inteiro:
(quantidade leva o sinal algébrico de "C" "-" ou "V" "+")
-40
entre com o valor unitario - ponto decimal e não virgula - duas casas:
1
Se NECTON e se ação entao 4.50 senão zero:
0
   próximo ============< 2 / 2 >===================
entre com 'C' compra ou 'V' venda:
c
entre com o ativo / papel negociado:
tt2
entre com a quantidade negociada - inteiro:
(quantidade leva o sinal algébrico de "C" "-" ou "V" "+")
-40
entre com o valor unitario - ponto decimal e não virgula - duas casas:
1
Se NECTON e se ação entao 4.50 senão zero:
0
C/V: C  Papel: TT1  Quantidade: -40  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: -0.1  Custo ponderado  99.99
C/V: C  Papel: TT2  Quantidade: -40  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: -0.1  Custo ponderado  99.99
C  TT1  -40  1.0  0.0  -0.1  99.99
C  TT2  -40  1.0  0.0  -0.1  99.99

========================= taxa de cada papel: ==============v0.06=======
Corretora:INTER  Nota:5555  Pregão:11/11/2024  Total c/sinal:-100.0  Itens:2
lin ativo    C/V  qtd  unitario  qtd X unt especifica    taxa      preço
1 TT1  C  -40  1.0  -40.0 0.0  -40.0  -80.0
2 TT2  C  -40  1.0  -40.0 0.0  -40.0  -80.0


Process finished with exit code 0

 */
/*
corretora:
inter
nota de corretagem:
1
data do pregão dd/mm/aaaa:
1
para Valor Final da Nota entre 'C' para crédito (vai receber $) ou 'D' para débito (vai pagar $):
d
entre o Valor Final da Nota (desnecessário o sinal):
330
quantos itens a lançar?
2
Corretora:INTER  Nota:1  Pregão:1  Cred./Déb.:D  Tt.abs:330.0  Tt.snl:-330.0  Itens:2  Compras:0.0  Vendas:0.0  Tt.Bruto:0.0  Tt.Taxas:0.0  fator Tx.:0.0
 próximo ============< 1 / 2 >===================
entre com 'C' compra ou 'V' venda:
c
entre com o ativo / papel negociado:
t1
entre com a quantidade negociada - inteiro:
100
entre com o valor unitario - ponto decimal e não virgula - duas casas:
1
 próximo ============< 2 / 2 >===================
entre com 'C' compra ou 'V' venda:
c
entre com o ativo / papel negociado:
t2
entre com a quantidade negociada - inteiro:
200
entre com o valor unitario - ponto decimal e não virgula - duas casas:
1
Corretora:INTER  Nota:1  Pregão:1  Cred./Déb.:D  Tt.abs:330.0  Tt.snl:-330.0  Itens:2  Compras:300.0  Vendas:0.0  Tt.Bruto:-300.0  Tt.Taxas:30.0  fator Tx.:0.1
C/V: C  Papel: T1  Quantidade: -100  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: 10.0  Custo ponderado  -90.0
C/V: C  Papel: T2  Quantidade: -200  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: 20.0  Custo ponderado  -180.0
==== acima esta correto ? ====

Process finished with exit code 0

GABARITO:
========================= taxa de cada papel: ==============v0.06=======
inter - Nota de Corretagem: 1 - Pregão: 1 Tot.:  -330.00
lin ativo    C/V  qtd  unitario  qtd X unt especifica    taxa      preço
 1      T1    C  -100      1.00   -100.00      0.00    -10.00    -110.00
 2      T2    C  -200      1.00   -200.00      0.00    -20.00    -220.00
   tot.taxas: 30.00   sumtx: -30.00   vl.liq.operac.:   -330.00

 */
/*  erro 02/09/2024
data do pregão dd/mm/aaaa:
02/08/2024
para o Valor Final da Nota indique Crédito (receber $) ou  Débito (vai pagar $)
'C' para crédito (vai receber $) ou 'D' para débito:
d
entre o Valor Final da Nota (desnecessário o sinal):
96
quantos itens a lançar?
2
Corretora:INTER  Nota:1234567  Pregão:02/08/2024  Cred./Déb.:D  Tt.abs:96.0  Tt.snl:-96.0
Itens:2  Compras:0.0  Vendas:0.0
Tt.Bruto:0.0  Tt.Taxas:0.0  fator Tx.:0.0
============< 1 / 2 >============
entre com 'C' compra ou 'V' venda:
v
entre com o ativo / papel negociado:
t1
entre com a quantidade negociada - inteiro:
100
entre com o valor unitario - ponto decimal e não virgula - duas casas:
1
============< 2 / 2 >============
entre com 'C' compra ou 'V' venda:
c
entre com o ativo / papel negociado:
t2
entre com a quantidade negociada - inteiro:
200
entre com o valor unitario - ponto decimal e não virgula - duas casas:
1
guido prepara tBruto -100.0   ttTaxas -4.0     fatorTx -0.013333333333333334
Corretora:INTER  Nota:1234567  Pregão:02/08/2024  Cred./Déb.:D  Tt.abs:96.0  Tt.snl:-96.0
Itens:2  Compras:200.0  Vendas:100.0
Tt.Bruto:-100.0  Tt.Taxas:-4.0  fator Tx.:-0.013333333333333334
C/V: V  Papel: T1  Quantidade: 100  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: -1.3333333333333335  Preço  98.66666666666667
C/V: C  Papel: T2  Quantidade: -200  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: -2.666666666666667  Preço  -202.66666666666666
==== acima esta correto ? ====
============================= taxa de cada papel: ==================v0.06=======
Corretora:INTER  Nota:1234567  Pregão:02/08/2024  Total c/sinal:-96.0  Itens:2
lin ativo    C/V  qtd  unitario  qtd X unt especifica    taxa      preço
 1      T1    V   100      1,00    100,00      0,00     -1,33      98,67
 2      T2    C  -200      1,00   -200,00      0,00     -2,67    -202,67

tot.taxas: -4,00   sumtx: -4,00   vl.liq.operac.:   -104,00

guido sumttlnota -103.99999999999999  ttVlFmSnl -96.0  > 0.005
* * * * * algo errado - sumttlnota: -103.99999999999999  * * * * *
Corretora:INTER  Nota:1234567  Pregão:02/08/2024  Cred./Déb.:D  Tt.abs:96.0  Tt.snl:-96.0
Itens:2  Compras:200.0  Vendas:100.0
Tt.Bruto:-100.0  Tt.Taxas:-4.0  fator Tx.:-0.013333333333333334

Process finished with exit code 0

 */