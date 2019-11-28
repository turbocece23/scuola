---
title: GIT
created: '2019-11-21T07:58:37.665Z'
modified: '2019-11-28T08:24:21.589Z'
---

# GIT

## <span style="color:#fca40c">Introduzione</span>

Un sistema di controllo di versione dei programmi (VCS, Version Control System), tiene traccia di ciascun cambiamento che avviene nel progetto.<br>
Prima dell'avvento di GIT vi erano solo sistemi basati su file e cartelle (e bestemmie). è il sistema di version control più semplice ma per grandi progetti potrebbe risultare difficile vedere i cambiamenti applicati.<br>
Di VCS ne esistono versioni distribuite e locali, Git e un DVCS (Distributed VCS).<br>
Git nasce come riga di comando, ma alcuni editor sofisticati hanno alcune funzionalità Git già costruite al loro interno, come tool grafici e scorciatorie varie.

Inizializzare git su una repository (locale sul pc)
```
git init
```

Per vedere lo stato della repository
```
git status
```

Aggiungiamo i file che dobbiamo caricare su git
```
git add <nomefile>
```

Creiamo il commit che porterà le nostre modifiche su git
```
git commit -m "Messaggio di commit"
```

Per tornare ad aggiornamenti precedenti del file

Per vedere i commit
```
git log --oneline
```

Per controllare un commit che è stato fatto in precedenza
```
git checkout <HASH del commit>
```

Per tornare alla versione corrente
```
git checkout master
```

Per taggare un programma con la sua versione
```
git tag -a v1.0 -m "Prima versione del programma"
```

Per tornare ad un commit precedente ANNULLANDO i cambiamenti successivi crea un commit uguale a quello specificato, basta che sia avvenuto prima dei o del commit che voglio annullare
```
git revert
```
