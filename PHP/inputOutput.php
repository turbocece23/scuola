<!DOCTYPE html>

<!--
Con Javascript è possibile utilizzare la funzione document.write per
generare output all'interno del body: testo, tag html, contenuto di variabili.
Dopo avere testato e sperimentato il funzionamento del file allegato,
svolgere i seguenti esercizi:

1) generare la successione di Fibonacci separando i valori con uno spazio

2) generare la tavola Pitagorica con ampiezza ricevuta in input dall'utente,
inserendo i valori in celle di una tabella (eventualmente aggiungere anche
le celle di intestazione per il numero di riga e il numero di colonna)
-->

<html>
	<head>
		<title>Successione di Fibonacci</title>
	</head>
	
	<style type="text/css">
		table{ border:none; }
		
		td{ padding:10px; }
		
		/* Riga dispari e cella pari */
		table tr:nth-child(odd) td:nth-child(even) {
			color:white;
			background: #910E0E;
		}
		/* Riga dispari e cella dispari */
		table tr:nth-child(odd) td:nth-child(odd) {
			color:white;
			background: #B10300;
		}
		
		/* Riga pari e cella dispari */
		table tr:nth-child(even) td:nth-child(odd) {
			color:white;
			background: #910E0E;
		}
		/* Riga pari e cella pari */
		table tr:nth-child(even) td:nth-child(even) {
			color:white;
			background: #B10300;
		}
		
		h3
		{
			color:white;
		}
	</style>
	
	<body style="background-color:#440100">
		<center>
			<form action="inputOutput.php" method="post">
				<h3>Tavola Pitagorica</h3>
				<p style="color:white">Righe: </p><input type="text" name="ascisse" placeholder="x" value="1">
				<br>
				<p style="color:white">Colonne: </p><input type="text" name="ordinate" placeholder="y" value="1">
				<br>
				<?php
					//Controlla che l'utente abbia inviato dei dati
					//Se si usa quelli dell'utente, altrimenti imposta un valore di default
					if(isset($_POST["submit"]))
					{
						$x=$_POST["ascisse"];
						$y=$_POST["ordinate"];
					}else
					{
						$x=1;
						$y=1;
					}
					
					echo "<table border=\"1\">";
					
					for($x=1;$x<=$_POST["ascisse"];$x++)
					{
						//Scrivo il tag di apertura riga
						echo "<tr>";
						for($y=1;$y<=$_POST["ordinate"];$y++)
						{
							//Calcolo e scrivo il numero nella sua cella
							$num=$x*$y;
							echo "<td>".$num."</td>";
						}
						echo "</tr>";
					}
					echo"</table>";
				?>
				<h3>Successione di Fibonacci</h3>
				<p style="color:white">Elementi da visualizzare: </p><input type="text" name="elementi" placeholder=" " value="1">
				<br>
				<?php
					if(isset($_POST["submit"]))
					{
						$nelementi=$_POST["elementi"];
					}else
					{
						$nelementi=1;
					}
					
					$fib = array();
					
					$fib[0] = 0;
					$fib[1] = 1;
					echo "<p style=\"color:white\">";
					for ($i = 2; $i <= $nelementi; $i++)
					{
						$fib[$i] = $fib[$i-2] + $fib[$i-1];
						echo $fib[$i]." ";
					}
					echo "</p>";
				?>
				<input type="submit" value="Genera">
			</form>
		</center>
	</body>
</html>
