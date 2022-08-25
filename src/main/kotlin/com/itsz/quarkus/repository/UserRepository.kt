package com.itsz.quarkus.repository

import com.itsz.quarkus.model.User
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepository<User>

