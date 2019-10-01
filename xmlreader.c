/*
 * Brunello Cesare 01/10/2019
 * 
 * Deserializzatore di un file XML in C
 * in ingresso il file xml di configurazione della macchina virtuale
 * output la scheda tecnica del pc come file di testo
 * o file html o rtf
 * nome, memoria, partizioni, dischi, interfacce di rete, dati simili
 * 
 * roba aggiuntiva
 * leggere argomenti argc argv
 * opzioni -i -v -vv del caso
 * 
 * cercare librerie per deserializzare da xml
*/

#include <stdio.h>
#include <string.h>

void help()
{
	printf("Comandi disponibili:\n");
}

void verbose()
{
	printf("Entro in modalità verbose:\n");
}

void file()
{
	printf("Specifica un file");
}

// argc parameter is the count of total command line arguments passed to executable on execution
// argv parameter is the array of character string of each command line argument passed to executable on execution
int main(int argc, char *argv[])
{
	int i=0;
	printf("\nArgomenti inseriti = %d\n\n", argc-1);

	/* First argument argv[0] is executable name only */
	for(i=1; i<argc; i++)
	{
		//Stampa ongi comando e l'ordine in cui compare
		printf("\nARGUMENT No %d=%s\n", i, argv[i]);
		
		//Inizio a controllare quali comandi vengono impostati
		if(strcmp(argv[i],"-h")==0)
		{
			help();
		}
		
		//Se viene scelta la modalità verbose, si sblocca una flag che
		//farà apparire delle stampe mentre il programma prende in input
		//il file e lo elabora, esempio:
		/* if(flag==1)
		 * { prinf("Ora apro il file e lo leggo"); } */
		if(strcmp(argv[i],"-v")==0)
		{
			verbose();
		}
		
		
	}

	printf("\n");
	return 0;
}
