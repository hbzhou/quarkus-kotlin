package com.itsz.quarkus.resource

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.itsz.quarkus.model.User
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class UserResourceTest {

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Test
    fun testFindAllEndpoint() {
        RestAssured.given()
            .`when`().get("/api/users")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .run {
                val users: List<User> = objectMapper.readValue(body().asString())
                users shouldContainExactlyInAnyOrder listOf(User(), User())
                val json: List<Map<String, Any>> = objectMapper.readValue(body().asString())
                json shouldContainExactlyInAnyOrder listOf()
            }

    }
}