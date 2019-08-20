package amber.pipeline.async

import amber.coroutines.joinAllJobsNonSuspending
import amber.pipeline.Context
import amber.pipeline.Executor
import amber.pipeline.Phase
import kotlinx.coroutines.CoroutineScope

class AsyncExecutor<C : Context>(private val coroutineScope: CoroutineScope) : Executor<C>() {
    override fun execute(phases: List<Phase<C>>, context: C) {
        coroutineScope.joinAllJobsNonSuspending {
            phases.forEach {
                launch {
                    it.run(context)
                }
            }
        }
    }
}
