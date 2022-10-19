package com.itsz.quarkus.resource

import com.itsz.quarkus.service.ExtensionService
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response


@ApplicationScoped
@Path("/api/extensions")
class ExtensionResource {

    @Inject
    @RestClient
    lateinit var extensionsService: ExtensionService

    @GET
    fun getById(@QueryParam(value = "id") id: String): Response {
        return Response.ok(extensionsService.getById(id)).build()
    }


}