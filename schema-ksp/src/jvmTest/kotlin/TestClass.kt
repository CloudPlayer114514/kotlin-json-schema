import com.yunbao.kotlin.schema.annotations.Description
import com.yunbao.kotlin.schema.annotations.ExtraProperty
import com.yunbao.kotlin.schema.annotations.MetaAnnotation
import com.yunbao.kotlin.schema.annotations.Schema
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Schema
@Serializable
@ExtraProperty("extra1", "homo114514")
@ExtraProperty("extra2", "homo114514")
@TestAnnotation
@Description("This is a test class")
data class TestClass(val str: String, val arr: List<Int>)

@MetaAnnotation
annotation class TestAnnotation(
    val enum: DeprecationLevel = DeprecationLevel.ERROR,
    val annotation: Schema = Schema(),
    val kClass: KClass<out Any> = ArrayList::class,
    val short: Short = 1,
    val long: Long = 1
)