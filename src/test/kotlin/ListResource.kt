import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ListResource {
    val url = "https://reqres.in/api"

    fun ListResource(page: Int, per_page: Int): Response {
        return Given {
            baseUri(url)
            queryParam("page", page)
            queryParam("per_page", per_page)
        } When {
            get("/unknown")
        } Extract {
            response()
        }
    }

    fun ListResource(id: Int) : Response {
        return Given {
            baseUri(url)
        } When {
            get("$id")
        } Then {
            log().all()
        }Extract {
            response()
        }
    }

    @Test
    @DisplayName("Busca por tipo de pantone")
    fun getListResource(){
        val response = ListResource(1, 6)
        assertNotNull(response)
        assertEquals(200, response.statusCode())
        assertEquals(1, response.jsonPath().getInt("page"))
        assertEquals("15-4020",
        response.jsonPath().getString("data[0].pantone_value"))
    }

    @Test
    @DisplayName("Lista varios usuários")
    fun getSeveralUserTest(){
        val response = ListResource(1,6)

        val size = response.jsonPath().getInt("data.size") - 1
        for (i in 0 .. size) {
            assertEquals(i+1, response.jsonPath().getInt("data[$i].id"))
        }
    }

    @Test
    @DisplayName("Busca ID por lista")
    fun getIdList() {
        val response = ListResource(2)
        assertNotNull(response)
        assertEquals(200, response.statusCode())
        assertEquals(2, response.jsonPath().getInt("data[1].id"))
        assertEquals(2001,
            response.jsonPath().getInt("data[1].year"))
    }

}