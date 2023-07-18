package com.itsz.quarkus.resource

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.nio.file.FileVisitOption
import java.nio.file.Files
import java.nio.file.Paths
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@ApplicationScoped
@Path("/api/nio")
class NIOFileResource {

    @GET
    @Path("/readDir")
    @Produces(MediaType.APPLICATION_JSON)
    fun readDir():Response {
        val records = Files.walk(Paths.get("D:\\git-code\\quarkus-department-system\\src\\main\\resources\\securities"), FileVisitOption.FOLLOW_LINKS)
            .filter(Files::isRegularFile).map {
            Files.readAllLines(it)
        }.flatMap { it.stream() }
            .toList()
        return Response.ok(records).build()
    }

    @GET
    @Path("/download")
    fun download(): Response {
        val bytes = Files.readAllBytes(Paths.get("D:\\git-code\\quarkus-department-system\\src\\main\\resources\\book.txt"))
        val inputStream = ByteArrayInputStream(bytes)
        return Response.ok(inputStream).header("content-disposition", "attachment; filename = download.txt").build()
    }

}