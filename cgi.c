/* La directory standard dei file CGI è
 * /usr/bin/cgi-bin/
 * 
 * Bisogna attivarli perchè apache li disattiva di default
 * 
 * Creare un programma, spostare la versione compilata fra le cgi-bin
 * in modo tale che produca del codice html visualizzabile dall'esterno
 * 
 * per attivare le cgi: https://code-maven.com/set-up-cgi-with-apache
 * 
 * Per controllar i log, sono posizionati in /var/log/apache/
 * all'interno sono ripostati i file per gli errori del server e gli
 * accessi
 * 
 * per attivare le cgi:
 * (https://noviello.it/come-avviare-cgi-scripts-in-apache2-su-ubuntu-16-04-17-04/)
 * 
 * Abilitiamo il modulo per le cgi
 * sudo a2enmod cgi
 * 
 * Riavviamo il server
 * service apcahe2 restart
 * 
 * Modifichiamo il file di configurazione di apache2
 * sudo nano /etc/apache2/apache2.conf
 * 
 * Alla fine del file aggiungere le seguenti righe
 * ServerName localhost
 * Options +ExecCGI
 * AddHandler cgi-script .cgi .pl .py
 * 
 * Spostiamo ora il nostro file eseguibile
 * che sia sh o c in uds@172.30.4.117:/usr/lib/cgi-bin/data.cgi
 * 
 * per vedere se funziona visitare il sito
 * 172.30.4.117/cgi-bin/data.cgi
 * */

#include <stdio.h>
#include <string.h>
#include <time.h>

int main()
{
	printf("Content: text/html\n\n");
	printf("<html>\n\t<head>\n\t\t<title>CGI Brunello</title>\n\t</head>\n\t<body>\n\t\n\t");
	
	time_t t = time(NULL);
	struct tm tm = *localtime(&t);
	
	printf("<h1>Data e ora del server:</h1>\n\t<br>\n");
	
	printf("\t<h2>\n\t\t");
	//Data
	printf("\tData: <span style=\"color:#1d3461\">%d/%d/%d</span>\n",tm.tm_mday,tm.tm_mon,tm.tm_year+1900);
	printf("\t\t\t<br>\n");
	//Ora
	printf("\t\t\tOra: <span style=\"color:#ff7700\">%d:%d:%d</span>\n",tm.tm_hour,tm.tm_min,tm.tm_sec);
	printf("\t\t</h2>\n");
	printf("\t</body>\n</html>");
	
	return 0;
}
