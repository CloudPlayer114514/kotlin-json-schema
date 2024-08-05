import com.yunbao.kotlin.schema.annotations.ExtraProperty
import com.yunbao.kotlin.schema.annotations.Schema
import com.yunbao.kotlin.schema.json.JsonType
import com.yunbao.kotlin.schema.schema.generator.SchemaGenerator
import com.yunbao.kotlin.schema.schema.info.schemaInfoFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlin.test.Test

class SchemaTest {
    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    @Serializable
    @Schema
    @ExtraProperty("deprecated", "true", JsonType.BOOLEAN)
    data class Teacher(val id: Int, val students: List<Student>)

    @Serializable
    @Schema
    @ExtraProperty("description", "This is a student", JsonType.STRING)
    data class Student(val studentId: Int, val others: List<Other>)

    @Serializable
    data class Other(val otherId: Int)

    @Serializable
    data class OtherContainer(val other: Other)

    fun teacher(id: Int, students: List<Student>) {

    }

    // 通过类解析是全平台的
    @Test
    fun schemaTest() {
        val schema = SchemaGenerator.createSchema<Teacher>()
        println(schema.toPrettyString())
    }

    // 通过函数解析目前只支持了 jvm
    @Test
    fun schemaFunctionTest() {
        val schema = SchemaGenerator.createSchema(::teacher)
        println(schema.toPrettyString())
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun schemaInfoTest() {
        val schemaInfo = schemaInfoFactory().build(Teacher.serializer().descriptor)
        schemaInfo.elements.forEach {
            println(it)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun schemaInfoTest1() {
        val descriptor = serialDescriptor<Teacher>()
        println(descriptor.serialName)
        println(descriptor.elementsCount)
        println(descriptor.annotations)
        val schemaInfo = schemaInfoFactory().build(descriptor)
        println(schemaInfo)
        schemaInfo.elements.forEach {
            println(it)
        }
    }

    private fun JsonObject.toPrettyString(): String {
        return json.encodeToString(this)
    }
}