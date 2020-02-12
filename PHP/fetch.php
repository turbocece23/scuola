<?php
	include "ascona.php";
?>
<!--
	connessione al db
	lettura dei dati
	(in caso non ci siano righe stampare che la ricerca non ha prodotto risultati)
	restituzione dei dati
	chiusura della connessione
	visualizzazione di un risultato
-->

<!DOCTYPE html>
<html>
	<head>
		<title>Fetch array e fetch object</title>
	</head>

	<body>
		<h1 style="color:#e84629; text-align:center;">Contenuto della tabella NAVI</h1>
		<table align="center">
			<tr>
				<th bgcolor="#ffa45a" style="color:gray">Codice</th>
				<th bgcolor="#ffa45a" style="color:gray">Nome nave</th>
				<th bgcolor="#ffa45a" style="color:gray">Bandiera</th>
			</tr>
			<?php
				$db =mysqli_connect($contabile,$utente,$psw,$database);
				if(!$db)
				{
					echo "Errore di connessione numero: ".$db->connect_errno;
					die();
				}

				$query="Select * from navi";
				$result = $db -> query($query);
				if(!$result)
				{
					die("Errore nella query");
				}

				while($riga=mysqli_fetch_object($result))
				{
					$id=$riga->Id_Nave;
					$nave=$riga->Nave;
					$bandiera = $riga->Bandiera;
					echo "\t\t\t<tr>\n";
					echo "\t\t\t\t<td style=\"color:#80b6cc; text-align:center;\"><b>".$id."</b></td>\n";
					echo "\t\t\t\t<td>".$nave."</td>\n";
					echo "\t\t\t\t<td style=\"text-align:right;\">".$bandiera."</td>\n";
					echo "\t\t\t</tr>\n";
				}
				echo "\t\t</table>\n";
			?>
	</body>
</html>

