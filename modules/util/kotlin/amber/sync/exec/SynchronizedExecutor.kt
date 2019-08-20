package amber.sync.exec

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SynchronizedExecutor(private val scope: SynchronisationScope) {

    internal fun contains(thread: Thread) = thread.name.contains(scope.scopeIdentifier)

    private val receive = Channel<SynchronizedExecutable<*>>()

    private lateinit var job: Job

    init {
        job = scope.launch {
            try {
                while (isActive && !receive.isClosedForReceive && !receive.isClosedForSend) {
                    receive.receive().execute()
                }
            }
            finally {
                receive.close()
            }
        }
    }

    suspend fun stop() = job.cancelAndJoin()

    suspend fun execute(synchronizedExecutable: SynchronizedExecutable<*>) {
        receive.send(synchronizedExecutable)
    }
}
