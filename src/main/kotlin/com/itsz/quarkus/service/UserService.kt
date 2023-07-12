package com.itsz.quarkus.service

import com.itsz.quarkus.model.User
import com.itsz.quarkus.repository.UserRepository
import io.smallrye.mutiny.Uni
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.bson.types.ObjectId
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class UserService(val userRepository: UserRepository) {

    fun findAll(): Uni<List<User>> = userRepository.findAll().list()
    fun findById(id: String): Uni<User> {
        val objectId = ObjectId(id)
        return userRepository.findById(objectId)
    }

    fun save(user: User): Uni<User> {
        return userRepository.persist(user)
    }

    fun update(user: User): Uni<User> = userRepository.update(user)

    fun export(): Uni<ByteArrayInputStream> {
        val format: CSVFormat = CSVFormat.DEFAULT.builder().setHeader("USERNAME", "PASSWORD", "ADDRESS", "SEX", "AGE", "ID").build()
        return userRepository.findAll().list<User>().map { users ->
            ByteArrayOutputStream().use { out ->
                CSVPrinter(PrintWriter(out), format).use { printer ->
                    printer.printRecords(users.map {
                        listOf(
                            it.username,
                            it.password,
                            it.address,
                            it.sex,
                            it.age,
                            it.id
                        )
                    })
                    printer.flush()
                }
                ByteArrayInputStream(out.toByteArray())
            }
        }
    }
}