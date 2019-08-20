package amber.sync.exec

import amber.collections.randomItem
import amber.coroutines.parallelism
import amber.sync.SyncMode
import amber.sync.Synchronized

private val synchronizedImpl = Synchronized(syncMode = SyncMode.EXECUTOR, syncExecutor = SynchronizedExecutor(SynchronisationScope()))

private val syncScopes by lazy { mutableListOf<SynchronisationScope>() }

val SyncScope
    get() = synchronizedImpl.synchronizedSafe {
        if (syncScopes.size < parallelism) {
            val newScope = SynchronisationScope()
            syncScopes.add(newScope)
            newScope
        }
        else syncScopes.randomItem()
    }
