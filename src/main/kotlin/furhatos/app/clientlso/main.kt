package furhatos.app.clientlso

import furhatos.app.clientlso.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

var serverAddress: String? = null

class ClientlsoSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        serverAddress = args[0]
    }
    Skill.main(args)
}
