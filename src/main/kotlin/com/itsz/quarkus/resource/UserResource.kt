package com.itsz.quarkus.resource

import com.itsz.quarkus.model.User
import com.itsz.quarkus.service.UserService
import io.quarkus.logging.Log
import io.smallrye.mutiny.coroutines.awaitSuspending
import io.vertx.core.eventbus.EventBus
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@ApplicationScoped
@Path("/api")
class UserResource(
    val userService: UserService,
    val bus: EventBus
) {

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findAll(): Response {
        val userList: List<User> = userService.findAll().awaitSuspending()
        return Response.ok(userList).build()
    }


    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun findById(@PathParam("id") id: String): Response {
        val user: User = userService.findById(id).awaitSuspending()
        return Response.ok(user).build()
    }

    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun saveUser(user: User): Response {
        val persistedUser = userService.save(user).awaitSuspending().apply {
            Log.info("Omit an event with address USER_CREATED")
            bus.send("USER_CREATED", this)
        }
        return Response.ok(persistedUser).build()
    }

    @PUT
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun updateUser(user: User): Response {
        val updatedUser = userService.update(user).awaitSuspending()
        return Response.ok(updatedUser).build()
    }

    @GET
    @Path("/users/export/csv")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    suspend fun export(@QueryParam("fileName") fileName: String): Response {
        val inputStream = userService.export()
        return Response.ok(inputStream).header("content-disposition", "attachment; filename = $fileName").build()
    }
}
