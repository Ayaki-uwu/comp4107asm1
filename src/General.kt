import GeneralManager.list
abstract class General(player: Player): Player {
    open val name: String=""
    open var maxHP:Int=4
    open var currentcard:Int=4
    open var country:String="" //勢力

    var player= player
    var strategy :Strategy ?=null
    var stage:State = HealthyState()

    open var RoundStartSkill:Boolean=false
    open var RoundEndSkill: Boolean=false
    open var RoundEndDraw:Boolean=false
    open var RoundSkill :Boolean=false
    open var PassiveSkill:Boolean=false

    open var LordSkill:Boolean=false
    open var TimeDelay:Boolean=false
    open var Acedia:Boolean=false

    override var identity: String = player.identity

    fun create(){
        print("$identity $name has been created")
    }
    fun displayHP(){
        println("current HP: $currentHP/$maxHP")
    }
    fun displayMaxHp(){
        println(", has $maxHP HP")
    }

    fun gameStart() {
        // Common steps
        //Acedia()
        if (timedelays()){ //true = dodged
            cardstates(2)
            usecard()
            dropcard()
        }
        else {
            cardstates(2)
            //usecard()
            dropcard()
        }
        // Hook method

    }
    open fun roundstartskill(){

    }
    open fun roundendskill(){

    }
    open fun roundskill(){

    }
    open fun lordskill ():Boolean
    {
        return false
    }
    fun roundactiveskill(){

    }
    fun TimeDelaysFunction(DelaysCard: String) {
        this.TimeDelay=true
        println("$name being placed the $DelaysCard card")
        return when (DelaysCard) {
            "Acedia" -> { this.Acedia = true }
//            "-" -> { a, b -> a - b } wait for adding other card
//            "*" -> { a, b -> a * b }
//            "/" -> { a, b -> a / b }
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }

    fun Acedia(){ //樂不思蜀
        TimeDelay=true
        Acedia=true
        println("$name being placed the Acedia card")
    }
    fun timedelays():Boolean{
        val list: List<Boolean> = listOf(false,true,false,false)
        if (TimeDelay && Acedia){
            val success:Boolean=list.random()
            println("judgment: $success")
            if (success){
                println("$name dodged the Acedia card")
                return true
            }
            else{
                println("$name can't dodge the Acedia card. Skipping one round of Play Phase.")
                return false
            }
        }
        else{
            return true
        }
    }

    fun cardstates(Draw:Int) {
        var Drawcard:Int=Draw
        currentcard+=Drawcard
        if (Drawcard>0){
        println("$name draws $Drawcard cards and now has $currentcard card(s).")}
        else{
            println("$name used a cards and now has $currentcard card(s).")}
    }
    fun usecard(){
        strategy?.playNextCard()
    }
    open fun dropcard(){
        println("$name has $currentcard card(s), current HP is $currentHP.")
        var numToDrop:Int=currentcard-currentHP
        if (numToDrop>0){
            currentcard=currentcard-numToDrop
            println("$name discards $numToDrop card(s), now has $currentcard card(s).")
            if (RoundEndSkill)
                roundendskill()
        }
    }
    open fun beingAttacked() {
        var Dodged: Boolean = false
        println("$name is being attacked")
        if (hasDodgeCard()) {
            //if (identity == "Lord") {
                Dodged = true
                cardstates(-1)
            //}
            println("$name dodged attack by spending a dodge card.")
        } else {
            if (PassiveSkill) {
                Dodged=lordskill()
            }
            if (!Dodged){
            currentHP -= 1
            println("$name can't dodge the attack, 1HP lost")
            displayHP()}
        }
        if (this.player is Lord){
            (player as Subject).notifyObservers(Dodged)
        }}

    fun hasDodgeCard(): Boolean {
        val list: List<Boolean> = listOf(false,false,true,false)
        val success:Boolean=list.random()
        return success
    }

    fun hasAttackCard():Boolean{
    val list: List<Boolean> = listOf(false,false,true,true,false,false)
        val success:Boolean = list.random()
        if (success)
            println("$name has attack card")
        else
            println("$name does not have attack card")
        return success
    }
}

class LiuBei(player: Player) : General(player=player) {
    override var currentHP:Int=3
    override val name: String="LIU Bei"
    override var maxHP: Int=5
    override var country: String="SHU"
    override var RoundSkill = true

    override fun roundskill() {
        println("$name use his Skill Benevolence")
        cardstates(-2)
        list.get(3).cardstates(+2)
        if (currentHP<maxHP){
            currentHP+=1
            println("$name gives 2 cards out and recover 1HP, now has $currentHP HP")
        }
    }

}
class CaoCao(player: Player) :WeiGeneral(player=player) {
    override var currentHP:Int=5
    override val name: String="Cao Cao"
    override var maxHP: Int=5
    override var country: String="WEI"
    override var LordSkill: Boolean=true
    override var PassiveSkill:Boolean=true
    var dodged:Boolean =false

    override fun lordskill():Boolean{
        println("$name used lord skill")

        var next = this.nextWei
        println(this.nextWei)
        while (next != null){
            //next.shouldHelpLord() &&
            if (next.hasDodgeCard()){
                println("${next.name} helped $name dodged attack by spending a dodge card.")
                dodged=true
                return true}
            else {
                println("${next.name} didn't use a dodge card.")
                next=next.nextWei
            }
        }
        return dodged
    }
}

class SunQuan (player: Player) :General(player=player){
    override var currentHP:Int=5
    override val name: String="Sun Quan"
    override var maxHP: Int=5
    override var country: String="WU"

    override fun lordskill():Boolean{
        return false
    }
}

class MAChao(player: Player) :General(player=player){
    override var currentHP:Int=4
    override val name: String="MA Chao"
    override var maxHP: Int=4
    override var country: String="SHU"

}

class LVMeng(player: Player) :General(player=player){
    override var currentHP:Int=4
    override val name: String="LV Meng"
    override var maxHP: Int=4
    override var country: String="WU"
    override var RoundEndSkill: Boolean=false
}

class LVBu(player: Player) :General(player=player){
    override var currentHP:Int=4
    override val name: String="LV Bu"
    override var maxHP: Int=4
    override var country: String="NEUTRAL"

}

class XUChu(player: Player) :WeiGeneral(player=player){
    override var currentHP:Int=4
    override val name: String="XU Chu"
    override var maxHP: Int=4
    override var country: String="WEI"
    override var RoundStartSkill: Boolean=true

}
class ZHAOYun(player: Player) :General(player=player){
    override var currentHP:Int=4
    override val name: String="ZHAO Yun"
    override var maxHP: Int=4
    override var country: String="SHU"

}

class DIAOChan(player:Player):General(player=player){
    override var currentHP:Int=3
    override val name: String="DIAO Chan"
    override var maxHP: Int=3
    override var country: String="NEUTRAL"
    override var RoundEndSkill: Boolean=true
    val skill1:String="閉月"
    override fun roundendskill() {
        currentcard+=1
        println("[$skill1] now $name has $currentcard card(s).")
    }
}
class DAQiao(player:Player):General(player=player){
    override var currentHP:Int=3
    override val name: String="DA Qiao"
    override var maxHP: Int=3
    override var country: String="WU"
}
class ZHOUYu(player:Player):General(player=player){
    override var currentHP:Int=3
    override val name: String="ZHOU Yu"
    override var maxHP: Int=3
    override var country: String="WU"
    val skill1:String="英姿"
    override var RoundStartSkill: Boolean=true
    override fun roundstartskill() {
        super.cardstates(3)
    }
}
class HUANGYueying(player:Player):General(player=player){
    override var currentHP:Int=3
    override val name: String="HUANG Yueying"
    override var maxHP: Int=3
    override var country: String="SHU"
}
class GUOJia(player:Player):WeiGeneral(player=player){
    override var currentHP:Int=3
    override val name: String="GUO Jia"
    override var maxHP: Int=3
    override var country: String="WEI"
}
class SIMAYi(player:Player):WeiGeneral(player=player){
    override var currentHP:Int=3
    override val name: String="SIMA Yi"
    override var maxHP: Int=3
    override var country: String="WEI"
}

class XiahouDun(player:Player):WeiGeneral(player=player){
    override var currentHP:Int=4
    override val name: String="Xiahou Dun"
    override var maxHP: Int=4
    override var country: String="WEI"
}

