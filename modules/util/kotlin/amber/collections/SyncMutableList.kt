package amber.collections

import amber.sync.Synchronized

class SyncMutableList<T>(private val impl: MutableList<T> = mutableListOf(), synchronized: Synchronized = Synchronized()) :
        MutableList<T>, Synchronized by synchronized {

    override val size: Int
        get() = synchronizedSafe { impl.size }

    override fun contains(element: T) = synchronizedSafe { impl.contains(element) }

    override fun containsAll(elements: Collection<T>) = synchronizedSafe { impl.containsAll(elements) }

    override fun get(index: Int) = synchronizedSafe { impl.get(index) }

    override fun indexOf(element: T) = synchronizedSafe { impl.indexOf(element) }

    override fun isEmpty() = synchronizedSafe { impl.isEmpty() }

    override fun iterator() = synchronizedSafe { impl.iterator() }

    override fun lastIndexOf(element: T) = synchronizedSafe { impl.lastIndexOf(element) }

    override fun add(element: T) = synchronizedSafe { impl.add(element) }

    override fun add(index: Int, element: T) = synchronizedSafe { impl.add(index, element) }

    override fun addAll(index: Int, elements: Collection<T>) = synchronizedSafe { impl.addAll(index, elements) }

    override fun addAll(elements: Collection<T>) = synchronizedSafe { impl.addAll(elements) }

    override fun clear() = synchronizedSafe { impl.clear() }

    override fun listIterator() = synchronizedSafe { impl.listIterator() }

    override fun listIterator(index: Int) = synchronizedSafe { impl.listIterator(index) }

    override fun remove(element: T) = synchronizedSafe { impl.remove(element) }

    override fun removeAll(elements: Collection<T>) = synchronizedSafe { impl.removeAll(elements) }

    override fun removeAt(index: Int) = synchronizedSafe { impl.removeAt(index) }

    override fun retainAll(elements: Collection<T>) = synchronizedSafe { impl.removeAll(elements) }

    override fun set(index: Int, element: T) = synchronizedSafe { impl.set(index, element) }

    override fun subList(fromIndex: Int, toIndex: Int) = synchronizedSafe { impl.subList(fromIndex, toIndex) }

}
