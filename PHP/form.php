<?php
/*
 * Realizzare un form dove si chiedono nome, ore mensili lavorate e paga oraria di un lavoratore e calcolare lo stipendio, visualizzandolo assieme al riepilogo di tutti i dati inviati. 
 */

?>
<!DOCTYPE html>
<html>

<head>
	<title>Ore di lavoro</title>
</head>

<body>
	<form action="form.php" method="post">
		<h1 style="color:#d34b39;">Ore di lavoro</h1>
		<p>Nome: </p><input type="text" name="nome" placeholder="Nome" value="" autocomplete="off">
		<br>
		<p>Ore mensili: </p><input type="text" name="ore" placeholder="Ore mensili" value="" autocomplete="off">
		<br>
		<p>Paga oraria: </p><input type="text" name="paga" placeholder="Paga oraria" value="1" autocomplete="off">
		<br><br>
		<?php
			//Controlla che l'utente abbia inviato dei dati
			//Se si usa quelli dell'utente, altrimenti imposta un valore di default
			if(isset($_POST["submit"]))
			{
				$nome=$_POST["nome"];
				$ore=$_POST["ore"];
				$paga=$_POST["paga"];
			}else
			{
				$nome="Mario";
				$ore=30;
				$paga="7";
			}
			
			echo "<table border=\"1\">\n";
			echo "\t\t\t<tr style=\"color:#57a8ba;\">\n";
			echo "\t\t\t\t<th>Nome</th>\n";
			echo "\t\t\t\t<th>Ore</th>\n";
			echo "\t\t\t\t<th>Paga</th>\n";
			echo "\t\t\t\t<th>Paga totale</th>\n";
			echo "\t\t\t</tr>\n";

			echo "\t\t\t<tr>\n";
			echo "\t\t\t\t<td>".$_POST["nome"]."</td>\n";
			echo "\t\t\t\t<td>".$_POST["ore"]."</td>\n";
			echo "\t\t\t\t<td>".$_POST["paga"]."</td>\n";
			echo "\t\t\t\t<td>".($_POST["ore"]*$_POST["paga"])."</td>\n";
			echo "\t\t\t</tr>\n";

			echo "\t\t</table>\n";
		?>
		<br>
		<input type="submit" value="Genera">
	</form>
</body>

</html>
