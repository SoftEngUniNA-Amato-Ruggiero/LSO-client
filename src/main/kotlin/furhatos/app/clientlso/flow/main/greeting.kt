package furhatos.app.clientlso.flow.main

import furhatos.app.clientlso.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Greeting: State = state(Parent) {
    onEntry {
        furhat.ask("Ciao! Posso farti qualche domanda per conoscerti meglio?")
    }

    onReentry {
        furhat.listen()
    }

    onResponse<Yes> {
        furhat.say("Bene, iniziamo!")
        goto(personalityTestRunner)
    }

    onResponse<No> {
        furhat.say("Ok.")
        goto(conversation(null))
    }

    onResponse {
        furhat.say("Non ho capito, puoi ripetere?")
        reentry()
    }

    onNoResponse {
        furhat.say("Scusa, hai detto qualcosa? Non ho sentito nulla.")
        reentry()
    }
}

