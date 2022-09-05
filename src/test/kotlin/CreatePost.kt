import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class CreatePost : Base() {
    private val URL_POST_REGISTER = "register"

    fun register() {
        Given {
            spec(specBaseToken())
            contentType(ContentType.JSON)
            body("{\n" +
                    "    \"email\": \"eve.holt@reqres.in\",\n" +
                    "    \"password\": \"control\"\n" +
                    "}")
            log().all()
        }When{
            post(URL_POST_REGISTER)
        }Then {
            log().all()
        }
    }

    fun newRegister() {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap.put("email", "eve.holt@reqres.io")
        hashMap.put("password", "pistol")

        Given {
            spec(specBase())
            contentType(ContentType.JSON)
            body(hashMap)
        }When {
            post(URL_POST_REGISTER)
        }Then {
            log().all()
        }
    }

    fun newFormRegister() {
        val pojoRegister = PojoRegister()
        pojoRegister.email = "eve.holt@reqres.in"
        pojoRegister.password = "pistol"

        Given {
            spec(specBase())
            contentType(ContentType.JSON)
            body(pojoRegister)
        }When {
            post(URL_POST_REGISTER)
        }Then {
            log().all()
        }
    }

    fun otherRegiter(pojoRegister: PojoRegister) {
        Given {
            spec(specBase())
            contentType(ContentType.JSON)
            body(pojoRegister)
            log().all()
        }When {
            post(URL_POST_REGISTER)
        }Then {
            log().all()
        }
    }

    @Test
    @Tag("success")
    fun loginTest() {
        register()
    }

    private val factoryRegister = FactoryRegister()

    @Test
    @Tag("success")
    fun registerSuccessTag() {
        otherRegiter(factoryRegister.successRegister())
    }

    @Test
    @Tag("fail")
    fun unsuccessRegisterFulTest() {
        otherRegiter(factoryRegister.unsuccessfulRegister())
    }

    @Test
    @Tag("fail")
    fun notFountUserTest() {
        otherRegiter(factoryRegister.userNotFoundRegister())
    }
}