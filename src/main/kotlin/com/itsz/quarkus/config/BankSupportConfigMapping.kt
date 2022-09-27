package com.itsz.quarkus.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "bank.support.mapping")
interface BankSupportConfigMapping {
    fun phone(): String

    fun email(): String

    fun business(): Business

    interface Business {
        fun phone(): String
        fun email(): String
    }
}



