interface Player {
    var currentHP:Int
    var identity:String
    open fun shouldHelpLord():Boolean{
        return false
    }
}

class Lord:Player, Subject{
    private val observers = mutableListOf<Observer>()


    override var currentHP:Int=0
    override var identity: String="Lord"

    override fun attach(observer: Observer) {
        observers.add(observer)
    }

    override fun detach(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers(dodged: Boolean) {
        observers.forEach {it.update(dodged)}
    }
}

class Loyalist:Player{
    override var currentHP:Int=0
    override var identity: String="Loyalist"
    override fun shouldHelpLord():Boolean{
        return true
    }
}
class Spy:Player ,Observer{
    var RiskLevel:Int = 0
    override var currentHP:Int=0
    override var identity: String="Spy"

    override fun shouldHelpLord(): Boolean {
        if (RiskLevel>=3){
            return true
        }
        else return false
    }

    override fun update(dodged: Boolean) {
        if (!dodged)
            RiskLevel+=1

        println("current risk level: $RiskLevel")
    }
}
class Rebel:Player{
    override var currentHP:Int=0
    override var identity: String="Rebel"

}