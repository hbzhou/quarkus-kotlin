package com.itsz.quarkus.repository

import com.itsz.quarkus.model.User
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: ReactivePanacheMongoRepository<User>

