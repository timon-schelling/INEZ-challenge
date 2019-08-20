package amber.properties

import amber.properties.buffer.PropertyValueBuffer
import amber.properties.value.PropertyValue

abstract class BufferedPropertyBase<T>(initValue: PropertyValue<T>) : AccessiblePropertyBase<T>(initValue) {

    private val valueBuffer by lazy { PropertyValueBuffer<T>() }

    override fun set(value: T) {
        val propertyValue = PropertyValue(value)
        synchronized(this) {
            valueBuffer.add(propertyValue)
        }
        updateValue()
    }

    override fun get(): T {
        updateValue()
        return getSave().value
    }

    private fun updateValue() {
        synchronized(this) {
            valueBuffer.values.forEach {
                setSave(it)
            }
        }
    }
}
