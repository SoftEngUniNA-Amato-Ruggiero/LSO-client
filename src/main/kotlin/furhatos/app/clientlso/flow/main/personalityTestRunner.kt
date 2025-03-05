package furhatos.app.clientlso.flow.main

import furhatos.app.clientlso.Client
import furhatos.app.clientlso.flow.Parent
import furhatos.app.clientlso.personality.Personality
import furhatos.app.clientlso.personality.PersonalityTest
import furhatos.flow.kotlin.*

val test = PersonalityTest()
val iterator = PersonalityTest.QUESTIONS.iterator()
var iteratorCurr: Map.Entry<PersonalityTest.Attributes, String> = PersonalityTest.QUESTIONS.entries.first()

val personalityTestRunner: State = state(Parent) {
    onEntry {
        furhat.say(PersonalityTest.TEST_DESCRIPTION)
        reentry()
    }

    onReentry {
        iteratorCurr = iterator.next()
        furhat.ask(getQuestion())
    }

    onResponse {
        try {
            val score = it.text.toInt()
            test.setAnswer(iteratorCurr.key, score)
        } catch (e: Exception) {
            furhat.ask("Hai detto " + it.text + "? Scusa, puoi ripetere?")
        }

        if (iterator.hasNext()) {
            furhat.say {
                random {
                    +"Ok! e..."
                    +"Poi..."
                    +"Ah, sì? E..."
                    +""
                }
            }
            reentry()
        } else {
            val personality = getPersonalityFromServer(test.getAnswers())
            furhat.say("Grazie per aver risposto!")
            goto(conversation(personality))
        }
    }

    onNoResponse {
        furhat.ask(
            "Hai provato a dire qualcosa? Scusami, non ho sentito. Ti avevo chiesto: " +
                    getQuestion()
        )
    }
}

fun getQuestion(): String {
    val question = iteratorCurr.value
    return "Quanto ritieni di essere una persona $question?"
}

private fun getPersonalityFromServer(answers: Map<PersonalityTest.Attributes, Int>): Personality {
    Client().use {
        val personalityJson = it.getPersonalityJson(answers)
        return Personality(personalityJson)
    }
}