package amber.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlin.coroutines.CoroutineContext

open class OptimalCoroutineScope(private val workerThreadNamePreset: String) : CoroutineScope {
    final override val coroutineContext: CoroutineContext =
            newFixedThreadPoolContext(parallelism, workerThreadNamePreset)
}
