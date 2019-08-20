package amber.sync.exec

import amber.sync.Synchronized
import kotlinx.coroutines.runBlocking

interface ExecutorSynchronized : Synchronized {

    val executor: SynchronizedExecutor

    override fun <T> synchronizedUnsafe(block: () -> T): T {
        val executable = LambdaSynchronizedExecutable<T>(block)
        runBlocking {
            executor.execute(executable)
            executable.join()
        }
        return executable.result
    }

    override fun <T> synchronizedSafe(block: () -> T): T {
        val currentThread = Thread.currentThread()
        if (executor.contains(currentThread)) return block()
        return synchronizedUnsafe(block)
    }

    override suspend fun <T> synchronized(block: () -> T): T {
        val currentThread = Thread.currentThread()
        if (executor.contains(currentThread)) return block()
        val executable = LambdaSynchronizedExecutable<T>(block)
        executor.execute(executable)
        executable.join()
        return executable.result
    }
}
