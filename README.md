# LSO Client
Dialogare coi robot

## Description
Lato client del progetto di LSO anno 2024/2025.
Il client Ha il compito di fare da intermediario tra un robot ed una persona, permettendo al robot di fare domande per stabilire quale personalità ha l'interlocutore (livello di estroversione, amicalità, coscienziosità, stabilità emotiva, apertura a nuove esperienze).
Ottenuta una stima della personalitá dal server, il client usa openai per la gestione dei dialoghi.

Il questionario di personalitá utilizzato è il [TIPI](https://gosling.psy.utexas.edu/scales-weve-developed/ten-item-personality-measure-tipi/).
Il robot utilizzato è [Furhat](https://docs.furhat.io/).

Per informazioni sulle skill di furhat si rimanda alla [documentazione](https://docs.furhat.io/skills/#the-contents-of-a-skill). 
Per il template di partenza utilizzato si rimanda a  [FurhatRobotics Github](https://github.com/FurhatRobotics/)

## Usage
È necessaria una key per le API di OpenAI, ottenibile registrandosi su [OpenAI API Platform](https://openai.com/api/). 
La key puó essere salvata tra le variabili d'ambiente, oppure nella root della repository, in un file `.env`, il cui corpo deve contenere la seguente riga:

  `OPENAI_API_KEY=<api-key>`
  
Sostituendo `<api-key>` con la chiave ottenuta.

  ### Eseguire localmente:
  `chmod +x gradlew && ./gradlew clean shadowJar`

  `java -jar build/libs/lso-client-all.skill`

  ### Eseguire su container:
  `docker build -t lso-client .`

  `docker run -it --rm --name running-lso-client lso-client`
  
