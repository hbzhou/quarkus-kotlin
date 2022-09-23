package com.itsz.quarkus.model

import org.bson.codecs.pojo.annotations.BsonDiscriminator

@BsonDiscriminator(value = "PASSPORT_INFO", key = "type")
data class PassportInfo(var passportNo: String? = null) : Info {
    override var type: InfoType = InfoType.PASSPORT_INFO
}