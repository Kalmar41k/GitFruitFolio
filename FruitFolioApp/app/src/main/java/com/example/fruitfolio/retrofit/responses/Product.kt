package com.example.fruitfolio.retrofit.responses

import java.io.Serializable

data class Product(
    val id: Int,
    val enumClass: String,
    val type: String,
    val sort: String,
    val description: String,
    val meanGrade: Double
) : Serializable {
    override fun toString(): String {
        return "Product(id=$id, " +
                "enumClass=$enumClass, " +
                "type=$type, sort=$sort, " +
                "description=$description, " +
                "meanGrade=$meanGrade)"
    }
}
