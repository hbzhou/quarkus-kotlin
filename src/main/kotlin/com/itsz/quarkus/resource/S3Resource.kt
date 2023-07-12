package com.itsz.quarkus.resource

import io.smallrye.mutiny.Uni
import io.vertx.core.json.Json
import io.vertx.mutiny.core.Vertx
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.core.async.AsyncResponseTransformer
import software.amazon.awssdk.services.s3.S3AsyncClient
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

const val s3_file_key = "test/upload"
const val filename = "upload.txt"

@ApplicationScoped
@Path("/api/s3")
class S3Resource(val vertx: Vertx, val s3Client: S3AsyncClient) {

    @ConfigProperty(name="quarkus.s3.devservices.buckets")
    lateinit var bucket: String


    @POST
    @Path("/upload")
    fun upload(): Uni<Response> {
        return vertx.fileSystem()
            .readFile("book.txt")
            .chain { buffer ->
                Uni.createFrom().completionStage {
                    s3Client.putObject(
                        { it.key(s3_file_key).bucket(bucket) },
                        AsyncRequestBody.fromBytes(buffer.bytes)
                    )
                }
            }.onItem()
            .transform { Response.ok(it.sdkHttpResponse()).build() }
    }


    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun download(): Uni<Response> {
        return Uni.createFrom().completionStage {
            s3Client.getObject(
                { it.key(s3_file_key).bucket(bucket) },
                AsyncResponseTransformer.toBytes()
            )
        }
            .map { it.asByteArray() }
            .onItem()
            .transform { Response.ok(it).header("content-disposition", "attachment; filename = $filename").build() }
    }
}