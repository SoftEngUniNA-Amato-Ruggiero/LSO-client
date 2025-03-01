package furhatos.app.clientlso.flow.main

import furhatos.app.clientlso.Client
import furhatos.app.clientlso.flow.Parent
import furhatos.app.clientlso.personality.Personality
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.State
import furhatos.app.clientlso.personality.PersonalityTest
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse

val test = PersonalityTest()
val iterator = PersonalityTest.QUESTIONS.iterator()

//THIS IS HORRIBLE, BUT IT WORKS FOR NOW
var attribute = PersonalityTest.Attributes.SYMPATHETIC
var question = ""

val personalityTestRunner: State = state(Parent) {
    onEntry {
        val (attr, quest) = iterator.next()
        attribute = attr
        question = quest
        furhat.say(PersonalityTest.TEST_DESCRIPTION)
        furhat.ask(question)
    }

    onResponse {
        try {
            val score = it.text.toInt()
            test.setAnswer(attribute, score)
        } catch (e: Exception) {
            furhat.say("Hai detto " + it.text + "?")
            furhat.say("Scusa, non ho capito.")
            delay(500)
            furhat.say(PersonalityTest.TEST_DESCRIPTION)
            furhat.ask(question)
        }

        if (iterator.hasNext()) {
            reentry()
        } else {
            val personality = getPersonalityFromServer(test.getAnswers())
            furhat.say("Grazie per aver risposto!")
            goto(conversation(personality))
        }
    }
}

private fun getPersonalityFromServer(answers: Map<PersonalityTest.Attributes, Int>): Personality {
    Client().use {
        val personalityJson = it.getPersonalityJson(answers)
        return Personality(personalityJson)
    }
}