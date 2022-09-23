package com.itsz.quarkus.service

import com.itsz.quarkus.model.Extension
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam


@Path("/extensions")
@RegisterRestClient
@ApplicationScoped
interface ExtensionService {

    @GET
    fun getById(@QueryParam(value = "id") id: String): Set<Extension>
}