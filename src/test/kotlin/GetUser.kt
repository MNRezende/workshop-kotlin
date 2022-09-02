import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class GetUser {

    val url = "https://reqres.in/api/"

    fun getUser(page: Int, per_page: Int): Response {
        return Given {
            baseUri(url)
            queryParam("page", page)
            queryParam("per_page", per_page)
        } When {
            get("/users")
        }Then {
            log().all()
        }Extract {
            response()
        }
    }
    fun getUser(id: Int) : Response {
        return Given {
            baseUri(url)
        } When {
            get("/users/$id")
        } Then {
            log().all()
        }Extract {
            response()
        }
    } //finalizamos aqui.

    @Test // continuar daqui.
    @DisplayName("Lista apenas um usuário")
    fun getUserTest() {
        val response = getUser(1, 1)
        assertNotNull(response)
        assertEquals(200, response.statusCode())
        assertEquals(1, response.jsonPath().getInt("page"))
        assertEquals("George",
        response.jsonPath().getString("data[0].first_name"))
}

    @Test
    @DisplayName("Lista varios usuários")
    fun getSeveralUserTest(){
        val response = getUser(1,12)

        val size = response.jsonPath().getInt("data.size") - 1
        for (i in 0 .. size) {
            assertEquals(i+1, response.jsonPath().getInt("data[$i].id"))
        }
    }

    @Test
    @DisplayName("Recupera usuário")
    fun getSingleUser() {
        val response = getUser(12)
        assertNotNull(response)
        assertEquals(200, response.statusCode())
        assertEquals(12, response.jsonPath().getInt("data.id"))
        assertEquals("Rachel",
            response.jsonPath().getString("data.first_name"))
    }

    @Test
    @DisplayName("Usuário Inexistente")
    fun getSingleUserNotFound() {
        val response = getUser(13)
        assertNotNull(response)
        assertEquals(404, response.statusCode())
    }
}

