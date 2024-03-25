import sun.awt.geom.AreaOp.CAGOp

abstract class Factory {
    abstract fun createPlayer(nums:Int):Player
    abstract fun createRandomGeneral(nums:Int):General


    companion object{
        val GeneralCreated: MutableList<String> = mutableListOf()
        val GeneralCountry: MutableList<String> = mutableListOf()
        fun create(name:String){
            GeneralCreated.add(name)

        }
        fun addcountry(name:String){
            GeneralCountry.add(name)
        }
        fun isCreated(name:String):Boolean{
            return GeneralCreated.contains(name)
        }
    }
}
object LordFactory :Factory(){
    var lord:General ?= null
    private val generals: List<String> = listOf(
        "LiuBei", "CaoCao", "SunQuan"
    )

    override fun createPlayer(nums:Int) :Player{
//        val randomLord = PlayerCreated.random()
        return when (nums){
            1 -> Lord()
            else -> throw IllegalArgumentException("Invalid number")
        }
    }
    override fun createRandomGeneral(nums:Int): General {
        val availableGenerals = generals.filter { !isCreated(it) }
        val randomGeneral = availableGenerals.random()
        create(randomGeneral)

        lord= createRandom(1, randomGeneral)
        if (randomGeneral == "LiuBei"){
            lord!!.strategy=LiuBeiStrategy(lord!!)
            println("${lord!!.strategy}")
        }
        else{
            lord!!.strategy=LoyalistStrategy(lord!!)}
        if (lord!!.country =="WEI"){
            NonLordFactory.lastWei=lord}
        return lord as General
    }
    open fun createRandom(nums:Int, Name:String): General {
        return when (Name) {
            "LiuBei" -> LiuBei(Lord())
            "CaoCao" -> {
                CaoCao(Lord())}
            "SunQuan" -> SunQuan(Lord())
            else -> throw IllegalArgumentException("Invalid general name")
        }
    }
}

object NonLordFactory : Factory(){
    var lastWei:General ?= null
    override fun createPlayer(nums:Int) :Player{
        return when (nums){
            2 -> Rebel()
            3 -> Spy()
            4 -> Loyalist()
            5 -> Rebel()
            6 -> Rebel()
            7 -> Loyalist()
            8 -> Rebel()
            9 -> Loyalist()
            10 -> Spy()

            else -> throw IllegalArgumentException("Invalid number")
        }
    }
    private val generals: List<String> = listOf(
        "MAChao", "LVMeng", "LVBu", "XUChu","ZHAOYun","DIAOChan","DAQiao",
        "DAQiao","ZHOUYu","HUANGYueying","SIMAYi","GUOJia","XiahouDun"
    )
    override fun createRandomGeneral(nums:Int): General {
        val availableGenerals = generals.filter { !isCreated(it) }
        val randomGeneral = availableGenerals.random()
        create(randomGeneral)
        println("$lastWei")
        val identiy:Player = createPlayer(nums)

        createGeneral(randomGeneral,identiy)

        val general:General = createGeneral(randomGeneral,identiy)

        if(lastWei != null && general.country=="WEI"){
            (lastWei as WeiGeneral).nextWei = general as WeiGeneral
            println("${(lastWei as WeiGeneral).nextWei} test123")
            lastWei=general
            println("$lastWei is added in Wei Chain $general")
        }


        if (general.identity == "Spy"){
            (LordFactory.lord!!.player as Lord as Subject).attach(general.player as Observer)
            general.strategy=RebelStrategy(general)
        }

        if (general.identity == "Rebel"){
            general.strategy=RebelStrategy(general)
        }

        if (general.identity == "Loyalist"){
            general.strategy=LoyalistStrategy(general)
        }

        return general
    }

    fun createGeneral(Name:String, identiy:Player):General{
        return when (Name) {
            "MAChao" -> MAChao(identiy)
            "LVMeng" -> LVMeng(identiy)
            "LVBu" -> LVBu(identiy)
            "XUChu" -> XUChu(identiy) as General
            "ZHAOYun" ->ZHAOYun(identiy)
            "DIAOChan"->DIAOChan(identiy)
            "DAQiao" -> DAQiao(identiy)
            "ZHOUYu"-> ZHOUYu(identiy)
            "HUANGYueying"->HUANGYueying(identiy)
            "GUOJia" -> GUOJia(identiy) as General
            "SIMAYi" -> SIMAYi(identiy) as General
            "XiahouDun"->XiahouDun(identiy) as General
            else -> throw IllegalArgumentException("Invalid general name")
        }
    }

}

fun main(){
    val test = GeneralManager
    test.createGenerals(10)

}