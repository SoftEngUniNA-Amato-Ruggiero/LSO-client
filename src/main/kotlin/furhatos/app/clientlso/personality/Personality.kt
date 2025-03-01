package furhatos.app.clientlso.personality

import org.json.JSONObject

class Personality(behaviorJson: String) {
    enum class Traits {
        EXTROVERSION, AGREEABLENESS, CONSCIENTIOUSNESS, EMOTIONAL_STABILITY, OPENNESS
    }

    private val traits: Map<Traits, Int>

    init {
        val json = JSONObject(behaviorJson)
        traits = Traits.values().associateWith { trait ->
            json.getInt(trait.name)
        }
    }

    fun get(trait: Traits): Int {
        return traits[trait] ?: throw IllegalArgumentException("Trait $trait not found")
    }
}