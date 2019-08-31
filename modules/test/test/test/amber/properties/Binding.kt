package test.amber.properties

import amber.properties.StringProperty
import amber.properties.exceptions.IllegalSetPropertyException
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrowExactlyUnit
import io.kotlintest.specs.StringSpec

class Binding : StringSpec({

    "basic binding" {

        val property1 = StringProperty("1")
        val property2 = StringProperty("2")

        property2 bind property1

        property2.value shouldBe "1"

        property1.value = "3"

        property2.value shouldBe "3"

        shouldThrowExactlyUnit<IllegalSetPropertyException> {
            property2.value = "4"
        }

    }

    "bidirectional binding" {

        val property1 = StringProperty("1")
        val property2 = StringProperty("2")

        property2 bind property1
        property1 bind property2

        property2.value shouldBe "1"

        property1.value = "3"

        property2.value shouldBe "3"

        property2.value = "4"

        property1.value shouldBe "4"

    }
})
