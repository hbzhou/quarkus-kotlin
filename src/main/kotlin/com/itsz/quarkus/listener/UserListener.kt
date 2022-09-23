package com.itsz.quarkus.listener

import com.itsz.quarkus.model.User
import io.quarkus.logging.Log
import io.quarkus.vertx.ConsumeEvent
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserListener {

    @ConsumeEvent("USER_CREATED")
    fun onUserCreated(user: User){
        Log.info(user)
    }
}