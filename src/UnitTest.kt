import GeneralManager.list
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WeiTest{
    @Test
    fun testEntourage(){
        WeiOnlyNonLordFactory.createRandomGeneral()
        list.get(0).beingAttacked()
        // Create an instance of the component
        val component = GeneralManager

        // Call the function being tested
        val result = component.list.get(0).lordskill()
        // Assert the expected result
        assertTrue(result)
    }
}

object WeiOnlyNonLordFactory{
    fun createRandomGeneral(){
        val generalManager = GeneralManager
        val playercount:Int=3
        var player:Int=1

        generalManager.list.add(LordFactory.createRandom(1,"CaoCao"))
        if (list.get(0).country =="WEI"){
            NonLordFactory.lastWei=list.get(0)}

        LordFactory.lord=list.get(0)

        while (player<=playercount){
            generalManager.createGenerals(player+1)
            list.get(player).create()
            list.get(player).displayMaxHp()
            player+=1
            if(list.get(player-1).country!="WEI"){
                println("${list.get(player-1).name} is discarded as he/she is not a Wei")
                list.removeAt(player-1)
                player-=1
            }
        }
        println("${(list.get(0)as WeiGeneral).nextWei}")
    }

}