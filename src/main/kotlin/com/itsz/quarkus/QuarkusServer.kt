package com.itsz.quarkus

import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication

fun main(args: Array<String>) {
    Quarkus.run(QuarkusServer::class.java, *args)

}

class QuarkusServer : QuarkusApplication {
    override fun run(vararg args: String?): Int {
        println("waiting for quarkus to start up .....")
        Quarkus.waitForExit();
        return 0
    }

}