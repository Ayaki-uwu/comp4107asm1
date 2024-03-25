import GeneralManager.list

abstract class Strategy(val general:General){
    open fun playNextCard(){
        attack()
    }
    abstract fun attack()

}

open class LoyalistStrategy(general: General) : Strategy(general) {
    override fun attack() {
       // val attacked:Boolean =false
        if (general.hasAttackCard()){
            println("${general.name} attacked ${list.get(2).name}")
            general.cardstates(-1)
            list.get(2).beingAttacked()
        }
//
//            while (!attacked){
//                val generals = list.random()
//                if (generals.identity!= "Lord" && generals.identity!= "Loyalist")
//                {
//                    generals.beingAttacked()
//                    val attacked=true
//                    break
//                }
//    }
    }
}
open class LiuBeiStrategy(general: General) : LoyalistStrategy(general) {
    override fun playNextCard() {
        println("test")
        general.stage.playNextCard(general)
    }
//    override fun attack() {
//        // val attacked:Boolean =false
//        if (general.hasAttackCard()) {
//            println("${general.name} attacked ${list.get(2).name}")
//            general.cardstates(-1)
//            list.get(2).beingAttacked()
//        }
//    }
}

class RebelStrategy(general: General) : Strategy(general) {

    override fun attack() {
        if (general.hasAttackCard()){
            println("${general.name} attacked ${list.get(0).name}")
            general.cardstates(-1)
            list.get(0).beingAttacked()
        }
    }

}