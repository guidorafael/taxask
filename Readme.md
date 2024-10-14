
	"TAXAS" is an application to help with the Annual
	Income Tax Return (Declaração Anual de Imposto sobre Renda)
	and also for tracking investments.

	Calculation of fees and taxes on brokerage note
	with multiple purchases and sales items.
	(Multiple buying and sailling assets).

	In this case, a proportional division (weighted) of the total
	rate of the note must be made by each transacted asset.

	The individual fees are printed and then incorporated into
	the ".pdf" of the corresponding Brokerage Note
	(document which the Federal Revenue can request).

	In the accounting view of the algorithm:
	Purchase of assets represents debit ("-" sign).
	Fees and taxes represent debits ("-" sign)
	Sale of assets represents credit ("+" sign).
	Although they do not appear here, remember:
	dividends and earnings represent credits ("+" sign).

Corretora:INTER  Nota:1  Pregão:1  Cred./Déb.:D  Tt.abs:126.0  Tt.snl:-126.0  Itens:2  Compras:120.0  Vendas:0.0  Tt.Bruto:-120.0  Tt.Taxas:6.0  fator Tx.:0.05  
C/V: C  Papel: T1  Quantidade: -40  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: 2.0  Custo ponderado  -38.0
C/V: C  Papel: T2  Quantidade: -80  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: 4.0  Custo ponderado  -76.0
==== acima esta correto ? ====

========================= taxa de cada papel: ==============v0.06=======
inter - Nota de Corretagem: 11 - Pregão: 11 Tot.:  -126.00
lin ativo    C/V  qtd  unitario  qtd X unt especifica    taxa      preço
1      T1    C   -40      1.00    -40.00      0.00     -2.00     -42.00
2      T2    C   -80      1.00    -80.00      0.00     -4.00     -84.00
tot.taxas:  6.00   sumtx: -6.00   vl.liq.operac.:   -126.00


Process finished with exit code 0


Corretora:INTER  Nota:1  Pregão:1  Cred./Déb.:C  Tt.abs:114.0  Tt.snl:114.0  Itens:2  Compras:0.0  Vendas:120.0  Tt.Bruto:120.0  Tt.Taxas:-6.0  fator Tx.:-0.05  
C/V: V  Papel: T1  Quantidade: 40  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: -2.0  Custo ponderado  38.0
C/V: V  Papel: T2  Quantidade: 80  Valor Unitário: 1.0  Corretagem Necton: 0.0  Taxa ponderada: -4.0  Custo ponderado  76.0
==== acima esta correto ? ====

Process finished with exit code 0
========================= taxa de cada papel: ==============v0.06=======
inter - Nota de Corretagem: 22 - Pregão: 22 Tot.:  114.00
lin ativo    C/V  qtd  unitario  qtd X unt especifica    taxa      preço
1      T1    V    40      1.00     40.00      0.00     -2.00      38.00
2      T2    V    80      1.00     80.00      0.00     -4.00      76.00
tot.taxas: -6.00   sumtx: -6.00   vl.liq.operac.:    114.00
ssdm2 @ ssdm2-X99-F8 ~/github.com/taxas (main)
