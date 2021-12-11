package com.mashup.healthyup.features.exercise

sealed class ExerciseException {

    class ExerciseListEmptyException(override val message: String? = "exerciseList is Empty") :
        Exception(message)
}
