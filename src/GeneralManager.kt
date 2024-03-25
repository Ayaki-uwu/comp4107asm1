import GeneralManager.list

object GeneralManager {
    init {
        println("Initializing...")
        // Optional: Additional initialization logic
    }
    val list: MutableList<General> = mutableListOf()
    fun addGeneral(general: General){
        list.add(general)
        println("General ${general.name} is created.")
    }
    fun removeGeneral(general: General){
        list.remove(general)
    }
    fun getGeneralCount(): Int{
        return list.size
    }
    fun createGenerals(nums:Int) {
        if (nums<=1)
            list.add(LordFactory.createRandomGeneral(nums))
        if(nums>1)
            list.add(NonLordFactory.createRandomGeneral(nums))
    }
}

fun main(){
    val generalManager = GeneralManager
    val playercount:Int=7
    for(i in 0..playercount){
        generalManager.createGenerals(i+1)
        list.get(i).create()
        list.get(i).displayMaxHp()
    }

    println("Total number of players: ${list.size}")

//    list.random().TimeDelaysFunction("Acedia")
//    for(i in 0..playercount){
//        list.get(i).gameStart()
//    }
    list.get(0).beingAttacked()
}
