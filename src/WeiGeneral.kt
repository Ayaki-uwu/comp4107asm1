import GeneralManager.list

abstract class WeiGeneral(player: Player) : General(player){
    var head: WeiGeneral? = null
    var nextWei: WeiGeneral? = null
}


