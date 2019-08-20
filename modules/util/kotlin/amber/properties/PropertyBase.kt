package amber.properties

import amber.properties.value.InternalValueAccess
import amber.properties.value.PropertyValue

abstract class PropertyBase<T>(initValue: PropertyValue<T>) {

    @Volatile
    private var _value: PropertyValue<T> = initValue
    //TODO REDO internal value access
    private val internalValueAccess by lazy { InternalValueAccess<T> { setInternalValue(it) } }

    private fun setInternalValue(value: PropertyValue<T>) = synchronized(this) {
        _value = value
    }

    private fun getInternalValue(): PropertyValue<T> = synchronized(this) {
        return _value
    }


    protected fun setSave(value: PropertyValue<T>) = synchronized(this) {
        checkSet(value)
        if (value.value !== getInternalValue().value) changeValue(value, internalValueAccess)
    }

    //todo add annotation don't call external
    protected open fun changeValue(value: PropertyValue<T>, internalValueAccess: InternalValueAccess<T>) =
            synchronized(this) {
                internalValueAccess.setInternalValue(value)
            }

    protected open fun checkSet(newValue: PropertyValue<T>) {}

    protected open fun getSave(): PropertyValue<T> = synchronized(this) {
        return getInternalValue()
    }
}
