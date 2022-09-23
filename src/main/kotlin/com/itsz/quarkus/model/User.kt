package com.itsz.quarkus.model

import io.quarkus.mongodb.panache.common.MongoEntity
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


@MongoEntity(collection = "user")
 data class User (

   @BsonId
    var id: ObjectId?=null,

   var username: String?=null,

   var password: String?=null,

   var age: Int=0,

   var address: String?=null,

   var sex: Int = 0,

   var info: Info? = null,
){
    override fun toString(): String {
        return "User(id=$id, username=$username, password=$password, age=$age, address=$address, sex=$sex, info=$info)"
    }
}









