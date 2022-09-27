package com.itsz.quarkus.resource

import com.itsz.quarkus.model.User
import io.kotest.matchers.collections.shouldHaveSize
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test

@QuarkusTest
class UserResourceTest {


    @Test
    fun testFindAllEndpoint() {
        RestAssured.given()
            .`when`().get("/api/users")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .run {
                val userList: List<User> = this.jsonPath().getList("$")
                userList shouldHaveSize 3
            }
2
    }
}