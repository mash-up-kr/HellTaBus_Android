/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.domain.entity

data class Exercise(
    val id: Long,
    val name: String,
    val priority: Int,
    val part: String,
    val baseCount: Int,
    val setCount: Int,
    val startWeight: Int,
    val changeWeight: Int,
    val setBreakTime: Int,
    val breakTime: Int,
    val imageLink: String,
    val placeHolderImage: String,
    val description: String,
) : Entity {

    companion object {
        val EMPTY = Exercise(
            id = 0,
            name = "",
            priority = 0,
            part = "",
            baseCount = 0,
            setCount = 0,
            startWeight = 0,
            changeWeight = 0,
            setBreakTime = 0,
            breakTime = 0,
            imageLink = "",
            placeHolderImage = "",
            description = ""
        )
    }
}
