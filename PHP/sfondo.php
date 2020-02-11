<?php
/*
    Realizzare un form per l'inserimento di un colore di sfondo preferito per la pagina web, alla conferma dell'inserimento del colore nel formato stringa o RGB (es. A13F00) inviare il dato alla pagina ricevente e visualizzarla col colore di sfondo prescelto
 */
?>
<!DOCTYPE html>
<html>

<head>
	<title>Cambia lo sfondo</title>
</head>


<?php
    echo "<body style=\"background-color:#".$_POST["colore"]."\">\n";
?>
	<form action="sfondo.php" method="post">
		<p>Colore di sfondo</p><input type="text" name="colore" placeholder="Colore" value="" autocomplete="off">
		<br><br>
		<br>
		<input type="submit" value="Genera">
	</form>
</body>

</html>
