package furhatos.app.clientlso.personality

import java.util.*

class PersonalityTest {
    enum class Attributes {
        EXTROVERTED, CRITICAL, DEPENDABLE, ANXIOUS, COMPLEX, RESERVED, SYMPATHETIC, DISORGANIZED, CALM, CONVENTIONAL
    }

    companion object {
        const val MINIMUM_SCORE = 1
        const val MAXIMUM_SCORE = 7

        const val TEST_DESCRIPTION = "Da $MINIMUM_SCORE a $MAXIMUM_SCORE, quanto pensi di essere..." //TODO: Cosí è al femminile, rendere gender neutral

        val QUESTIONS: Map<Attributes, String> = EnumMap<Attributes, String>(Attributes::class.java).apply {
            put(Attributes.EXTROVERTED, "Estroversa, esuberante.")
            put(Attributes.CRITICAL, "Polemica, litigiosa.")
            put(Attributes.DEPENDABLE, "Affidabile, auto-disciplinata.")
            put(Attributes.ANXIOUS, "Ansiosa, che si agita facilmente.")
            put(Attributes.COMPLEX, "Aperta alle nuove esperienze, con molti interessi.")
            put(Attributes.RESERVED, "Riservata, silenziosa.")
            put(Attributes.SYMPATHETIC, "Comprensiva, affettuosa.")
            put(Attributes.DISORGANIZED, "Disorganizzata, distratta.")
            put(Attributes.CALM, "Tranquilla, emotivamente stabile.")
            put(Attributes.CONVENTIONAL, "Tradizionalista, abitudinaria.")
        }
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