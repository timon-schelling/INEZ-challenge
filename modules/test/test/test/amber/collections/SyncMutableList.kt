package test.amber.collections

import amber.coroutines.joinAllJobs
import amber.sync.SyncMode
import amber.sync.Synchronized
import io.kotlintest.TestContext
import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.forAll
import io.kotlintest.tables.headers
import io.kotlintest.tables.row
import io.kotlintest.tables.table
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import amber.collections.SyncMutableList as SyncList

class SyncMutableList : StringSpec({
                                       "async add and remove SyncMode.EXECUTOR".invoke {
                                           table(headers("factor", "syncMode"),
                                                  row(arrayOf(10, 20, 50, 100, 350), SyncMode.EXECUTOR)
                                           ).forAll{ a, b ->
                                               forAll(a) {
                                                   testWith(it, b)
                                               }
                                           }
                                       }
                                       "async add and remove SyncMode.MUTEX".invoke {
                                           table(headers("factor", "syncMode"),
                                                  row(arrayOf(10, 20, 50, 100, 350), SyncMode.MUTEX)
                                           ).forAll{ a, b ->
                                               forAll(a) {
                                                   testWith(it, b)
                                               }
                                           }
                                       }
                                   })

private fun TestContext.testWith(it: Int, b: SyncMode) {
    val factor = it
    val list = SyncList<String>(synchronized = Synchronized(b))
    runBlocking {
        putMetaData("time $it", measureTimeMillis {
            coroutineScope<Unit> {
                this.joinAllJobs {
                    repeat(factor) {
                        GlobalScope.launch {
                            repeat(factor) {
                                list.add("test")
                            }
                        }
                    }
                }
            }
            list.size shouldBe factor * factor
            coroutineScope<Unit> {
                this.joinAllJobs {
                    repeat(factor) {
                        GlobalScope.launch {
                            repeat(factor) {
                                list.synchronizedSafe<Unit> {
                                    list.remove(list.last())
                                }
                            }
                        }
                    }
                }
            }
            list.size shouldBe 0

        })
    }
}


