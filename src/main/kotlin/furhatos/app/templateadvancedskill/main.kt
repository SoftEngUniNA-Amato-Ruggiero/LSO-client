package furhatos.app.templateadvancedskill

import furhatos.app.templateadvancedskill.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class MyAdvancedSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    routine()
    Skill.main(args)
}