package com.itsz.quarkus.resource

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import io.vertx.core.file.OpenOptions
import io.vertx.mutiny.core.Vertx
import io.vertx.mutiny.core.file.AsyncFile
import io.vertx.mutiny.core.parsetools.RecordParser
import java.nio.charset.StandardCharsets
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

private const val SECURITIES_DIR = "securities"

@ApplicationScoped
@Path("/api/vertx")
class VertxResource(val vertx: Vertx) {

    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun download(): Uni<Response> {
        return vertx.fileSystem().open("book.txt", OpenOptions().setRead(true))
            .onItem().transform {
                Response.ok(it).header("content-disposition", "attachment; filename = war-and-peace.txt").build()
            }
    }

    @GET
    @Path("/readDir")
    @Produces(MediaType.APPLICATION_JSON)
    fun readDirWithRawMutiny(): Uni<Response> {
        return vertx.fileSystem().readDir(SECURITIES_DIR)
            .map { convertToAsyncFileMulti(it) }
            .flatMap{ it.parseFile()}
            .onItem()
            .transform { Response.ok(it).build() }
    }

    @GET
    @Path("/readDirSuspend")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun readDirWithSuspend(): Response {
        val fileNames = vertx.fileSystem().readDir(SECURITIES_DIR).awaitSuspending()
        val recordList = readFiles(fileNames).awaitSuspending()
        return Response.ok(recordList).build()
    }

    private fun readFiles(fileNames: MutableList<String>) = convertToAsyncFileMulti(fileNames).parseFile()

    private fun Multi<AsyncFile>.parseFile(): Uni<MutableList<String>> =
        this.concatMap { RecordParser.newDelimited("\r\n", it).toMulti() }
            .map { it.toString(StandardCharsets.UTF_8) }
            .collect().asList()

    private fun convertToAsyncFileMulti(fileNames: List<String>): Multi<AsyncFile> =
        Multi.createFrom().iterable(fileNames)
            .concatMap { filename ->
                println("reading data from file $filename")
                vertx.fileSystem().open(filename, OpenOptions().setRead(true)).toMulti()
            }
}