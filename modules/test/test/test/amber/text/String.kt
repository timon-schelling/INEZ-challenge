package test.amber.text

import amber.text.cut
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class String : StringSpec({

                              "String.cut function" {
                                  "~oifjdg|isef65$1*foo/bar?param=value&foo=bar§text/json;{key:value}".cut(
                                          '#',
                                          '~',
                                          '|',
                                          '%',
                                          '$',
                                          '*',
                                          '!',
                                          '§',
                                          ';'
                                  ).size shouldBe 7
                              }
                          })
