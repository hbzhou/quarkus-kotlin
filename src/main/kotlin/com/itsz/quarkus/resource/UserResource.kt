package com.itsz.quarkus.resource

import com.itsz.quarkus.model.User
import com.itsz.quarkus.service.UserService
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@ApplicationScoped
@Path("/api")
class UserResource(val userService: UserService) {

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    fun findAll(): Response {
        return Response.ok(userService.findAll()).build()
    }

    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    fun save(user: UserDto): Response {
        val userEntity = User(
            username = user.username,
            password = user.password,
            age = user.age,
            sex = user.sex,
            address = user.address
        )
        userService.save(userEntity)
        return Response.accepted().build()
    }

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun findById(@PathParam("id") id: Long): Response {
        return Response.ok(userService.findById(id)).build()
    }

    @PUT
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    fun update(userDto: UserDto): Response {
        val user = userService.findById(userDto.id)
        user.username = userDto.username
        user.password = userDto.password
        user.sex = userDto.sex
        user.age = userDto.age
        user.address = userDto.address
        userService.save(user)
        return Response.accepted().build()
    }

    @DELETE
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteUserById(id: Long): Response {
        userService.deleteById(id)
        return Response.ok().build()
    }

    @GET
    @Path("/users/export/csv")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun download(): Response {
        val byteStream = userService.exportCSV()
        return Response.ok(byteStream).header("content-disposition",
            "attachment; filename = users.csv").build();
    }

}

data class UserDto(

    val id: Long,
    val username: String,
    val password: String,
    val age: Int,
    val sex: Int,
    val address: String,

    )