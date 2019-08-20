package amber.repository

class ListRepository<T>(private val list: MutableList<T>) : Repository<T> {
    override fun put(element: T) {
        list.add(element)
    }

    override fun contains(element: T): Boolean {
        return list.contains(element)
    }

    override fun remove(element: T) {
        list.remove(element)
    }

    override val elements: List<T>
        get() = list.toList()

    override fun iterator(): Iterator<T> = list.iterator()
}
