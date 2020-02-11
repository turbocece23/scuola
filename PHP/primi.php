<?php
//http://www.phponwebsites.com/2015/08/php-program-find-prime-numbers-between-1-and-100.html
function primi($low,$high)
{
	$count=0;
	for($i=$low;$i<=$high;$i++)
	{
		$mm=0;
		for($j=2;$j<=$i/2;$j++)
		{
			if($i%$j==0)
			{
				$mm++;
				break;
			}
		}
		if ($mm == 0)
		{
				echo"$i è un numero primo.<br>";
				$count++;
		}
	}
	echo "\nHo trovato ".$count." numeri primi\n";
}
?>

<!DOCTYPE html>
<html>

<head>
	<title>Numeri primi</title>
</head>

<body>
	<?php
		$val1=10;
		$val2=100;
		primi($val1,$val2);
	?>
</body>

</html>
