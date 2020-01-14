<?php
	function isPrimo($n)
	{
		for($i=2;$i<$n;$i++)
		{
			if($n % $i == 0)
			{
				return FALSE;
			}else
			{
				return TRUE;
			}
		}
	}
?>

<!DOCTYPE html>
<html>

<head>
	<title>Numeri primi</title>
</head>

<body>
	<?php
		for($j=1;$j<50;$j++)
		{
			if(isPrimo($j)==TRUE)
			{
				echo $j." ";
			}
		}
	?>
</body>

</html>
