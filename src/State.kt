interface State {
    fun playNextCard(general:General)
}

class HealthyState: State {
    override fun playNextCard(general:General) {
        if(general.currentHP<general.maxHP){
            general.stage=UnhealthyState()
            general.stage.playNextCard(general)
        }
        else
            general.strategy?.attack()
    }

}

class UnhealthyState: State{
    override fun playNextCard(general:General) {
        if (general.currentHP==general.maxHP){
            general.stage=HealthyState()
            general.stage.playNextCard(general)}
        else
            general.roundskill()
    }
}

