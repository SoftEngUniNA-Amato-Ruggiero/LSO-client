# LSO Client
Dialogare coi robot

## Description
Lato client del progetto di LSO anno 2024/2025.
Il client ha il compito di fare da intermediario tra un robot ed una persona, permettendo al robot di fare domande per stabilire quale personalità ha l'interlocutore (livello di estroversione, amicalità, coscienziosità, stabilità emotiva, apertura a nuove esperienze).
Ottenuta una stima della personalitá dal server, il client usa openai per la gestione dei dialoghi.

Il questionario di personalitá utilizzato è il [TIPI](https://gosling.psy.utexas.edu/scales-weve-developed/ten-item-personality-measure-tipi/).
Il robot utilizzato è [Furhat](https://docs.furhat.io/).

Per informazioni sulle skill di Furhat si rimanda alla loro [documentazione](https://docs.furhat.io/). 
Il template di partenza utilizzato è `Blank skill` di [FurhatRobotics](https://github.com/FurhatRobotics/).

## Usage
### Requirements
- [Java SDK versione 8](https://adoptium.net/temurin/releases/?package=jdk&version=8) (per eseguire localmente).
- [Docker](https://www.docker.com/) (per eseguire su container).
- Emulatore di Furhat, ottenibile registrandosi su [Furhat.io](https://furhat.io/).
- Key per le API di OpenAI, ottenibile registrandosi su [OpenAI API Platform](https://openai.com/api/). 
-- La key puó essere salvata tra le variabili d'ambiente, oppure nella root della repository, in un file `.env`, il cui corpo deve contenere la seguente riga:

  `OPENAI_API_KEY=<api-key>`
  
Sostituendo `<api-key>` con la key ottenuta.

  ### Eseguire localmente:
  1. Build del jar eseguibile con gradlew:
     ```sh
     chmod +x gradlew && ./gradlew clean shadowJar
  2. Esecuzione del jar su JVM:
     ```sh
     java -jar build/libs/lso-client-all.skill`

  ### Eseguire su container:
  1. Build del container:
     ```sh
      docker build -t lso-client .
  2. Esecuzione del container:
     ```sh
     docker run -it --rm --name running-lso-client lso-client 
