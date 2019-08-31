package amber.config.impl

import amber.config.AbstractConfig
import amber.mapquery.getByPath
import amber.mapquery.query
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonLiteral
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.parse

open class JsonConfig(private var jsonObject: JsonObject = JsonObject(emptyMap())) : AbstractConfig() {

    constructor(string: String) : this() {
        load(string)
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun load(string: String) {
        jsonObject = Json.parse<JsonObject>(string)
    }

    override fun _get(path: String): Any? {
        return jsonObject.query().getByPath(path).getAs<JsonLiteral>().content
    }
}
