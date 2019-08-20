package amber.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class JoinAllJobsScope(val coroutineScope: CoroutineScope, val jobManger: JobManger) {
    fun launch(block: suspend CoroutineScope.() -> Unit) {
        jobManger.add(coroutineScope.launch(coroutineScope.coroutineContext, CoroutineStart.DEFAULT, block))
    }
}

fun CoroutineScope.joinAllJobsNonSuspending(block: CoroutineScopeJobManager.() -> Unit) {
    val jobManger = CoroutineScopeJobManager(this)
    jobManger.block()
    runBlocking { jobManger.joinAll() }
}

suspend fun CoroutineScope.joinAllJobs(block: suspend CoroutineScopeJobManager.() -> Unit) {
    val jobManger = CoroutineScopeJobManager(this)
    jobManger.block()
    jobManger.joinAll()
}
