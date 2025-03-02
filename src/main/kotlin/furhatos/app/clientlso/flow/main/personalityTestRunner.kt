package furhatos.app.clientlso.flow.main

import furhatos.app.clientlso.Client
import furhatos.app.clientlso.flow.Parent
import furhatos.app.clientlso.personality.Personality
import furhatos.app.clientlso.personality.PersonalityTest
import furhatos.flow.kotlin.*

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
        furhat.ask(getCustomQuestion())
    }

    onResponse {
        try {
            val score = it.text.toInt()
            test.setAnswer(attribute, score)
        } catch (e: Exception) {
            furhat.say("Hai detto " + it.text + "? Scusa, non ho capito.")
            delay(500)
            furhat.ask(getCustomQuestion())
        }

        if (iterator.hasNext()) {
            reentry()
        } else {
            val personality = getPersonalityFromServer(test.getAnswers())
            furhat.say("Grazie per aver risposto!")
            goto(conversation(personality))
        }
    }

    onNoResponse {
        furhat.ask("Hai provato a dire qualcosa? Scusami, non ho sentito. Ti avevo chiesto: ${PersonalityTest.TEST_DESCRIPTION} $question")
    }
}

// Nota: purtroppo il piano gratuito di OpenAI non permette di fare cosí tante richieste in cosí poco tempo
fun getCustomQuestion(): String {
//    try {
//        val chatRequestBuilder = ChatRequest.builder()
//            .model("gpt-3.5-turbo")
//            .message(ChatMessage.SystemMessage.of("Riformula questa domanda, in maniera sintetica ma con creativitá: " + PersonalityTest.TEST_DESCRIPTION + question))
//
//        val futureChat = openAI.chatCompletions().create(chatRequestBuilder.build())
//        val chatResponse = futureChat.join()
//        return chatResponse.firstContent().toString()
//    } catch (e: Exception) {
    return PersonalityTest.TEST_DESCRIPTION + question
//    }
}

private fun getPersonalityFromServer(answers: Map<PersonalityTest.Attributes, Int>): Personality {
    Client().use {
        val personalityJson = it.getPersonalityJson(answers)
        return Personality(personalityJson)
    }
}