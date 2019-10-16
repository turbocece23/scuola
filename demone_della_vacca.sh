#!/bin/bash
#indica l'interprete che utilizzo per eseguire lo script

#Modi d'uso
#Una volta lanciato lo script ./demone_della_vacca.sh
#su un altro temrinale lanciamo nc localhost PORTA
#o un indirizzo ip che conosciamo al posto del localhost
#e immettiamo un input se il programma lo richiede

PORTA=9970
ESEGUIBILE="sh"
#ESEGUIBILE="eject; sleep 1; eject -t"
while true
do
	nc.traditional -lv -p ${PORTA} -c "$ESEGUIBILE"
	printf "\e[1;34mIl demone \e[1;31m$ESEGUIBILE\e[1;34m è stato eseguito\e[0;0m\n"
done
