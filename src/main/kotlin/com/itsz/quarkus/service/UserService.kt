package com.itsz.quarkus.service

import com.itsz.quarkus.model.User
import com.itsz.quarkus.repository.UserRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.PrintWriter
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional


@ApplicationScoped
class UserService(val userRepository: UserRepository) {


    fun findAll(): List<User> = userRepository.findAll().list()

    @Transactional
    fun save(user: User) = userRepository.persist(user)

    fun findById(id: Long): User = userRepository.findById(id)

    @Transactional
    fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }

    fun exportCSV(): ByteArrayInputStream {
        val format: CSVFormat = CSVFormat.DEFAULT.withHeader("Name", "Password", "Address")
        val users = userRepository.findAll().list<User>()
        try {
            ByteArrayOutputStream().use { out ->
                CSVPrinter(PrintWriter(out), format).use { printer ->
                    for (user in users) {
                        val data: List<String?> = listOf(user.username, user.password, user.address)
                        printer.printRecord(data)
                    }
                    printer.flush()
                }
                return ByteArrayInputStream(out.toByteArray())
            }
        } catch (e: IOException) {
            throw RuntimeException(
                "fail to import data to CSV file: " + e.message
            )
        }
    }

}