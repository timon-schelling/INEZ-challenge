package amber.repository

open class MapRepository<K, V> : Repository<Map.Entry<K, V>> {

    private val data = mutableMapOf<K, V>()

    override val elements: List<Map.Entry<K, V>>
        get() = synchronized(this) {
            data.entries.toList()
        }

    override fun contains(element: Map.Entry<K, V>) = synchronized(this) {
        data.contains(element.key) && data.containsValue(element.value)
    }

    fun containsKey(key: K) = synchronized(this) {
        data.contains(key)
    }

    override fun remove(element: Map.Entry<K, V>): Unit = synchronized(this) {
        data.remove(element.key)
    }

    open fun removeKey(key: K) {
        synchronized(this) {
            data.remove(key)
        }
    }

    open fun remove(key: K, value: V) {
        synchronized(this) {
            data.remove(key, value)
        }
    }

    override fun put(element: Map.Entry<K, V>) = synchronized(this) {
        data[element.key] = element.value
    }

    open fun put(key: K, value: V) {
        synchronized(this) {
            data.put(key, value)
        }
    }

    fun get(key: K) = synchronized(this) {
        data[key]
    }

    override fun iterator(): Iterator<Map.Entry<K, V>> = synchronized(this) {
        elements.listIterator()
    }

}
