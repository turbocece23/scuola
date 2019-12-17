# **<span style="color:#0496FF">13/12/2019</span>**

CGI

Quano installiamo Apache, installiamo anche il modulo del PHP ma non solo, all'interno vi è solitamente anche un linguaggio detto "ospitato" che è quello del MySQL (creazione di database). Quando questi sono uniti il PHP viene istruito a dialogare

|Server|OS nativo|
|:---|---:|
|Apache|Linux|
|Internet Information Service|Windows|
|Tomcat (usato per Serverlet)|Macchina Virtuale|

Le CGI forniscono come output un documento HTML e dialogano direttamente con il sistema operativo, il PHP invece no. Quando dialoghiamo con PHP non andiamo a interagire direttamente con il sistema operativo, ho un tramite che è l'Apache o IIS, infatti gli script vengono interpretati.

Rimangono due tipi di tracce:
- Variabili di sessione: variabili che io tengo sul server
- Cookie: tenuti su una minima parte del disco del client

Queste variabili hanno un tempo di validità, standard è 20 minuti, ma questo limite è alterabile via PHP.

Un socket funziona in tale modo:
1. Un client fa una richiesta su una porta di un server
2. Un server, ricevuta una richiesta su una porta, crea un processo figlio che gestisce la richiesta
3. Il server libera la porta, la comunicazione viene spostata su una porta non standard, il client rimane in ascolto sulla stessa porta
4. Client e processo figlio del server dialogano

## <span style="color:#39AD27">HTTPS</span>

"Se voglio fare HTTPS, devo installare un modulo che si chiama *Guarda che voglio fare HTTPS*" D. Napolitano 13/12/2019

All'interno di Apache ho due cartelle molto importanti

```c
/var/www/html/
/etc/apache2/
```

In cui si trovano diversi file (come 000-default.conf) i quali gesticono la disponbilità e la raggiungibilità dei siti di default, in HTTPS o HTTP

<b>
	<span style="font-size:64px;">
		<span style="color:#FFBA49">H</span>
		<span style="color:#FCAA67">T</span>
		<span style="color:#EF5B5B">T</span>
		<span style="color:#DB3A34">P</span>
		<span style="color:#084C61">S</span>
	</span>
</b>