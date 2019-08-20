package amber.repository

open class FinalizeableMapRepository<K, V> : MapRepository<K, V>() {

    class Definalizer<K, V> internal constructor()

    private val final = mutableMapOf<K, Definalizer<K, V>>()

    fun finalize(key: K): Definalizer<K, V> {
        synchronized(this) {
            val definalizer = Definalizer<K, V>()
            if (!isFinal(key) && super.containsKey(key)) {
                final.put(key, definalizer)
            }
            return definalizer
        }
    }

    fun definalize(key: K, definalizer: Definalizer<K, V>) {
        synchronized(this) {
            if ((isFinal(key)) && final[key] === definalizer) {
                final.remove(key)
            }
        }
    }

    fun isFinal(key: K): Boolean {
        synchronized(this) {
            return final.containsKey(key)
        }
    }

    override fun put(element: Map.Entry<K, V>) = synchronized(this) {
        put(element, null)
    }

    override fun put(key: K, value: V) = synchronized(this) {
        put(key, value, null)
    }

    fun put(element: Map.Entry<K, V>, definalizer: Definalizer<K, V>?) = put(element.key, element.value, definalizer)

    fun put(key: K, value: V, definalizer: Definalizer<K, V>? = null) {
        synchronized(this) {
            if ((!isFinal(key)) || (definalizer != null && final[key] === definalizer)) {
                super.put(key, value)
            }
        }
    }

    override fun remove(element: Map.Entry<K, V>): Unit = synchronized(this) {
        remove(element, null)
    }

    override fun removeKey(key: K) {
        synchronized(this) {
            return remove(key, null)
        }
    }

    override fun remove(key: K, value: V) = synchronized(this) {
        remove(key, value, null)
    }

    fun remove(key: K, definalizer: Definalizer<K, V>? = null) {
        synchronized(this) {
            if ((!isFinal(key)) || (definalizer != null && final[key] === definalizer)) {
                super.removeKey(key)
            }
        }
    }

    fun remove(element: Map.Entry<K, V>, definalizer: Definalizer<K, V>?) =
            remove(element.key, element.value, definalizer)

    fun remove(key: K, value: V, definalizer: Definalizer<K, V>? = null) {
        synchronized(this) {
            if ((!isFinal(key)) || (definalizer != null && final[key] === definalizer)) {
                super.remove(key, value)
            }
        }
    }

}
