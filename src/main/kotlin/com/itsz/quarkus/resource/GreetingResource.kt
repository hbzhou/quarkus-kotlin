package com.itsz.quarkus.resource

import com.itsz.quarkus.config.BankSupportConfig
import com.itsz.quarkus.config.BankSupportConfigMapping
import org.eclipse.microprofile.config.ConfigProvider
import org.eclipse.microprofile.config.inject.ConfigProperties
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path("/api")
class GreetingResource {

    @ConfigProperty(name = "quarkus.greetings")
    lateinit var greetings: String
    
    @ConfigProperties(prefix = "bank.support")
    lateinit var bankSupportConfig: BankSupportConfig

    @Inject
    lateinit var bankSupportConfigMapping: BankSupportConfigMapping

    @GET
    @Path("/greetings")
    @Produces(MediaType.APPLICATION_JSON)
    fun greetings(): String {
        val quarkus = ConfigProvider.getConfig().getConfigValue("quarkus.hello")
        return "Hello, $greetings, $quarkus"
    }


    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    fun hello(@QueryParam(value = "username") username: String?): String {
        val config = ConfigProvider.getConfig()
        val value = config.getValue("quarkus.hello", String::class.java)
        return "Hello, $value"
    }

    @GET
    @Path("/config")
    @Produces(MediaType.APPLICATION_JSON)
    fun getBankConfig(): BankSupportConfig = bankSupportConfig


    @GET
    @Path("/configMapping")
    @Produces(MediaType.APPLICATION_JSON)
    fun getBankConfigMapping(): Map<String, Any> {
        val mapping = HashMap<String, Any>()
        mapping["phone"] = bankSupportConfigMapping.phone()
        mapping["email"] = bankSupportConfigMapping.email()
        mapping["business"] = bankSupportConfigMapping.business()
        return mapping
    }


}