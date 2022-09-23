package com.itsz.quarkus.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.bson.codecs.pojo.annotations.BsonDiscriminator

@BsonDiscriminator(value = "NONE", key = "type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = BirthInfo::class, name = "BIRTH_INFO"),
    JsonSubTypes.Type(value = PassportInfo::class, name = "PASSPORT_INFO"),
    JsonSubTypes.Type(value = JobInfo::class, name = "JOB_INFO")
)
interface Info {
    var type: InfoType
}