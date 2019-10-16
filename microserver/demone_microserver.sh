#!/bin/bash
#Questo è lo script che rappresenta il server
#Una volta messo in esecuzione gestisce più richieste consecutive
#da parte dei client

PORTA=2080
ESEGUIBILE="./microserver"

while true
do
	nc.traditional -lv -p ${PORTA} -c "$ESEGUIBILE"
	printf "\e[1;34mIl demone \e[1;31m$ESEGUIBILE\e[1;34m è stato eseguito\e[0;0m\n"
done
