package com.itsz.quarkus.model

import org.bson.codecs.pojo.annotations.BsonDiscriminator

@BsonDiscriminator(value = "BIRTH_INFO", key = "type")
data class BirthInfo(var birthday: String? = null) : Info {
    override var type: InfoType = InfoType.BIRTH_INFO
}