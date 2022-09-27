package com.itsz.quarkus.config

import org.eclipse.microprofile.config.inject.ConfigProperties

@ConfigProperties(prefix = "bank.support")
data class BankSupportConfig(var phone: String?=null, var email: String?=null)
