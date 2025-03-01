package furhatos.app.clientlso.flow.main

import furhatos.app.clientlso.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Greeting: State = state(Parent) {
    onEntry {
        furhat.ask("Ciao! Posso farti qualche domanda per conoscerti meglio?")
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
}

