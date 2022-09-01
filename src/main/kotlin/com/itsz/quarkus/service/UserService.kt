package com.itsz.quarkus.service

import com.itsz.quarkus.model.User
import com.itsz.quarkus.repository.UserRepository
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
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
    fun findById(id: String): Uni<User> = userRepository.findById(ObjectId(id))
    fun save(user: User): Uni<User> = userRepository.persist(user)
    fun update(user: User): Uni<User> = userRepository.update(user)

    suspend fun export(): ByteArrayInputStream {
        val format: CSVFormat = CSVFormat.DEFAULT.builder().setHeader("USERNAME", "PASSWORD", "ADDRESS", "SEX", "AGE", "ID").build()
        val users = userRepository.findAll().list<User>().awaitSuspending()
        ByteArrayOutputStream().use { out ->
            CSVPrinter(PrintWriter(out), format).use { printer ->
                users.forEach { user ->
                    printer.printRecord(
                        listOf(
                            user.username,
                            user.password,
                            user.address,
                            user.sex,
                            user.age,
                            user.id
                        )
                    )
                }
                printer.flush()
            }
            return ByteArrayInputStream(out.toByteArray())
        }
    }

}