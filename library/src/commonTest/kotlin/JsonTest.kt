import com.yunbao.kotlin.schema.annotations.Description
import com.yunbao.kotlin.schema.annotations.ExtraProperty
import com.yunbao.kotlin.schema.json.JsonType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlin.test.Test

@OptIn(ExperimentalSerializationApi::class)
class JsonTest {
    @Serializable
    @ExtraProperty("\$id", "114514", JsonType.Number)
    @ExtraProperty("\$name", "test class", JsonType.Number)
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
        val serialDescriptor = TestClass.serializer().descriptor
        println(serialDescriptor.annotations)

    }
}