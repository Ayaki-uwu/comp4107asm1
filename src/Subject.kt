interface Subject {
    fun attach(observer: Observer)
    fun detach(observer: Observer)
    fun notifyObservers(dodged: Boolean)

}