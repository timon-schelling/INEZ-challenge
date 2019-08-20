package amber.sync.mutex

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MutexSynchronizedImpl(private val mutex: Mutex = Mutex()) : MutexSynchronized {

    override fun <T> synchronizedUnsafe(block: () -> T): T {
        var result: T? = null
        runBlocking {
            mutex.withLock(Thread.currentThread()) {
                result = block()
            }
        }
        return result!!
    }

    override fun <T> synchronizedSafe(block: () -> T): T {
        return if (mutex.holdsLock(Thread.currentThread())) block()
        else {
            var result: T? = null
            runBlocking {
                mutex.withLock(Thread.currentThread()) {
                    result = block()
                }
            }
            result!!
        }
    }

    override suspend fun <T> synchronized(block: () -> T): T {
        return if (mutex.holdsLock(Thread.currentThread())) block()
        else {
            var result: T? = null
            mutex.withLock(Thread.currentThread()) {
                result = block()
            }
            result!!
        }
    }
}
