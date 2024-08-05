import com.yunbao.kotlin.schema.annotations.Description
import com.yunbao.kotlin.schema.annotations.ExtraProperty
import com.yunbao.kotlin.schema.annotations.Schema
import com.yunbao.kotlin.schema.json.JsonType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlin.test.Test

@OptIn(ExperimentalSerializationApi::class)
class JsonTest {
    @Serializable
    @ExtraProperty("\$id", "114514", JsonType.NUMBER)
    @ExtraProperty("\$name", "test class", JsonType.STRING)
    @Schema
    data class TestClass(
        @Description("this is a description")
        val name: String
    )

    private var _id: Int? = null

    var id: Int
        get() = _id?.let { return it }
            ?: 114514.also { _id = it }
        set(value) { _id = value }

    @Test
    fun test() {

    }

    // common
    fun call(any: Any? = null) {
        println("call any, any: $any")
    }

    // platform
    fun call() {
        println("call nothing")
    }
}