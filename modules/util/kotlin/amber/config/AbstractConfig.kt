package amber.config

abstract class AbstractConfig : Config {

    protected var _isFinal = false

    override fun isFinal(): Boolean = _isFinal

    override fun final() {
        _isFinal = true
    }

}
