package test.amber.sync

import amber.coroutines.joinAllJobs
import amber.sync.SyncMode
import amber.sync.Synchronized
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlinx.coroutines.GlobalScope

class SyncList : StringSpec({

                                "concurrent add" {
                                    val factor = 300
                                    val list = mutableListOf<String>()
                                    val synchronized = Synchronized(SyncMode.MUTEX)
                                    joinAllJobs {
                                        repeat(factor) {
                                            GlobalScope.launch {
                                                repeat(factor) {
                                                    synchronized.synchronizedSafe {
                                                        list.add((it).toString())
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    list.size shouldBe factor * factor
                                    joinAllJobs {
                                        repeat(factor) {
                                            GlobalScope.launch {
                                                repeat(factor) {
                                                    synchronized.synchronizedSafe {
                                                        list.removeAt(list.size - 1)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    list.size shouldBe 0
                                }
                            })

