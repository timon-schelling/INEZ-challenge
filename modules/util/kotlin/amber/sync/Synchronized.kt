package amber.sync

import amber.sync.exec.ExecutorSynchronized
import amber.sync.exec.ExecutorSynchronizedImpl
import amber.sync.exec.SynchronizedExecutor
import amber.sync.mutex.MutexSynchronizedImpl
import kotlinx.coroutines.sync.Mutex

interface Synchronized {
    fun <T> synchronizedUnsafe(block: () -> T): T
    fun <T> synchronizedSafe(block: () -> T): T
    suspend fun <T> synchronized(block: () -> T): T
}

@Suppress("FunctionName")
fun Synchronized(syncMode: SyncMode = SyncMode.MUTEX, mutex: Mutex? = null, syncExecutor: SynchronizedExecutor? = null) = when(syncMode) {
    SyncMode.MUTEX    -> if (mutex == null) MutexSynchronizedImpl() else MutexSynchronizedImpl(mutex)
    SyncMode.EXECUTOR -> if (syncExecutor == null) ExecutorSynchronizedImpl() else ExecutorSynchronizedImpl(syncExecutor)
}

enum class SyncMode {
    MUTEX,
    EXECUTOR
}
