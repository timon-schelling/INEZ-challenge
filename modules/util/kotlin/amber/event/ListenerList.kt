package amber.event

class ListenerList<T>(private val impl: MutableList<Listener<T>> = ArrayList<Listener<T>>()) :
        MutableList<Listener<T>> by impl
