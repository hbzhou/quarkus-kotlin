package com.itsz.quarkus.resource

import io.smallrye.mutiny.Uni
import io.vertx.core.file.OpenOptions
import io.vertx.mutiny.core.Vertx
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@ApplicationScoped
@Path("/api/vertx")
class VertxResource(val vertx: Vertx) {

    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun download (): Uni<Response>{
       return vertx.fileSystem().open("book.txt",OpenOptions().setRead(true))
            .onItem().transform {
                Response.ok(it).header("content-disposition", "attachment; filename = war-and-peace.txt").build()
            }
    }
}