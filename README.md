# subito-cart-service

Come avviare il tutto.

## Step 1 Eseguire i test 
Bisogna andare con la shell nel root del progetto.

Da li bisogna rendere eseguibile **test.sh** quindi avviare il seguente comando: *chmod +x scripts/test.sh*

Successivamente scrivere il comando *./scripts/test.sh*

Se i test ok possiamo andare con il prossimo comando.

## Step 2 Eseguire la run del progetto
Anche qui rendiamo eseguitbile **run.sh** con il comando: *chmod +x scripts/run.sh*
Poi sempre dopo scrivere il comando *./scripts/run.sh*
L'applicativo verrà avvitato al seguente indirizzo 
[http://localhost:8080/](http://localhost:8080/)

## Come Testare il Servizio
Il servizio viene avviato con i dati vuoti per cui ci viene incontro lo **swaggwer** per capire come creare, modificare, ottenere e cancellare un prodotto.
Inoltre si potrà creare, ottenere ed eliminare un ordine. 

## Dove sono i Dati?
All'indirizzo [http://localhost:8080/h2-console](http://localhost:8080/h2-console) si potrà accedere al db H2.
L'host da inserire è *jdbc:h2:mem:mydb* e cliccare direttamente su "connect".

