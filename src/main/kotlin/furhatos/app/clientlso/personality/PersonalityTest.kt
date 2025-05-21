package furhatos.app.clientlso.personality

import java.util.*

class PersonalityTest {
    enum class Attributes {
        EXTROVERTED, CRITICAL, DEPENDABLE, ANXIOUS, COMPLEX, RESERVED, SYMPATHETIC, DISORGANIZED, CALM, CONVENTIONAL
    }

    companion object {
        const val MINIMUM_SCORE = 1
        const val MAXIMUM_SCORE = 7

        const val TEST_DESCRIPTION = "Per favore, rispondi assegnando un punteggio da $MINIMUM_SCORE a $MAXIMUM_SCORE"

        val QUESTIONS: Map<Attributes, String> = mapOf (
            Attributes.EXTROVERTED to ("Estroversa, esuberante"),
            Attributes.CRITICAL to ("Polemica, litigiosa"),
            Attributes.DEPENDABLE to ("Affidabile, auto-disciplinata"),
            Attributes.ANXIOUS to ("Ansiosa, che si agita facilmente"),
            Attributes.COMPLEX to ("Aperta alle nuove esperienze e con molti interessi"),
            Attributes.RESERVED to ("Riservata, silenziosa"),
            Attributes.SYMPATHETIC to ("Comprensiva, affettuosa"),
            Attributes.DISORGANIZED to ("Disorganizzata, distratta"),
            Attributes.CALM to ("Tranquilla, emotivamente stabile"),
            Attributes.CONVENTIONAL to ("Tradizionalista, abitudinaria")
        )
    }

    private val answers: MutableMap<Attributes, Int> = EnumMap(Attributes::class.java)

    fun getAnswers(): Map<Attributes, Int> = answers

    fun setAnswer(attribute: Attributes, score: Int) {
        answers[attribute] = validateScore(score)
    }

    private fun validateScore(score: Int): Int {
        require(score in MINIMUM_SCORE..MAXIMUM_SCORE) {
            "Score must be between $MINIMUM_SCORE and $MAXIMUM_SCORE."
        }
        return score
    }
}