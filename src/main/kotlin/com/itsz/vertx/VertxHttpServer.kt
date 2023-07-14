package com.itsz.vertx

import io.vertx.mutiny.core.Vertx


fun main() {
    Vertx.vertx().createHttpServer()
        .requestHandler { request ->
            request.response().endAndForget("hello, from http server....")
        }
        .listen(8080)
        .subscribe()
        .with {
            println("${Thread.currentThread().name} --> http server started at ${it.actualPort()}")
        }
}