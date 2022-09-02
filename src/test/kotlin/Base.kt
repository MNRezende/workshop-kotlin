import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class Base {

    @BeforeAll
    fun messageAll() {
        println("Run all tests!")
    }
    @BeforeEach
    fun messageEach() {
        println("Call me")
    }

    fun specBase() : RequestSpecification{
       return RequestSpecBuilder()
            .setBaseUri("https://reqres.in/")
            .setBasePath("api")
            .build()
    }

    fun specBaseToken () : RequestSpecification {
        val token = getToken()

            return RequestSpecBuilder()
                .addRequestSpecification(specBase())
                .addHeader("Authorization", "Bearer $token")
                .build()
    }

    fun getToken() : String {
        var loginJson: HashMap<String, String> = HashMap()
        loginJson.put("email", "eve.holt@reqres.in")
        loginJson.put("password", "cityslicka")

        return Given {
            spec(specBase())
            contentType(ContentType.JSON)
            body(loginJson)
        }When {
            post("login")
        }Then  {
            log().all()
        } Extract {
            response().jsonPath().getString("token")
        }
    }
}