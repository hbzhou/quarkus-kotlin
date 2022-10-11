package com.itsz.quarkus.listener

import com.itsz.quarkus.model.User
import com.itsz.quarkus.repository.UserRepository
import io.quarkus.logging.Log
import io.quarkus.vertx.ConsumeEvent
import io.smallrye.mutiny.Uni
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserListener(val userRepository: UserRepository) {

    val logger: Logger = Logger.getLogger(UserListener::class.java)


    @ConsumeEvent("USER_CREATED")
    fun onUserCreated(user: User) {
       logger.info("receive event from  event bus $user")
    }
}