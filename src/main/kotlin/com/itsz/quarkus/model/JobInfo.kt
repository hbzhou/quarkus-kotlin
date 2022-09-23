package com.itsz.quarkus.model

import org.bson.codecs.pojo.annotations.BsonDiscriminator

@BsonDiscriminator(value = "JOB_INFO", key = "type")
data class JobInfo(var jobTitle: String? = null) : Info {
    override var type: InfoType = InfoType.JOB_INFO
}