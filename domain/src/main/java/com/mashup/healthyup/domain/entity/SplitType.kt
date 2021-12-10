/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.domain.entity

enum class SplitType(val value: String) : Entity {
    FULL_BODY_WORKOUT("무분할"),
    SPLIT_3_DAY_WORKOUT("3분할"),
    SPLIT_5_DAY_WORKOUT("5분할")
}
