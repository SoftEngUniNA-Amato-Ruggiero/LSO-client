package furhatos.app.clientlso.flow.main

import furhatos.flow.kotlin.*

val Idle: State = state {
    onEntry {
        furhat.attendNobody()
        furhat.listen()
    }

    onResponse {
        furhat.say("Oh, ciao! Scusa, mi ero addormentata.")
        goto(Greeting)
    }

    onUserEnter {
        furhat.attend(it)
        goto(Greeting)
    }

}
