package furhatos.app.clientlso.flow

import furhatos.app.clientlso.flow.main.Idle
import furhatos.app.clientlso.flow.main.Greeting
import furhatos.app.clientlso.setting.DISTANCE_TO_ENGAGE
import furhatos.app.clientlso.setting.MAX_NUMBER_OF_USERS
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.util.Language

val Init: State = state {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(DISTANCE_TO_ENGAGE, MAX_NUMBER_OF_USERS)
        furhat.setInputLanguage(Language.ITALIAN)
//        furhat.setVoice(Voice("Bianca"))
        furhat.setVoice(PollyNeuralVoice("Bianca"))
    }
    onEntry {
        /** start interaction */
        when {
            furhat.isVirtual() -> goto(Greeting) // Convenient to bypass the need for user when running Virtual Furhat
            users.hasAny() -> {
                furhat.attend(users.random)
                goto(Greeting)
            }
            else -> goto(Idle)
        }
    }

}
