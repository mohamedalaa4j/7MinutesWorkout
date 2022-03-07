package com.mido.a7minutesworkout

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel>{

        ///// ArrayList of exercises
        val exerciseList = ArrayList<ExerciseModel>()

        //region Exercises

        ///// #1
        val jumpingJacks = ExerciseModel (1, "Jumping Jacks", R.drawable.ic_jumping_jacks, isCompleted = false, isSelected = false)
        exerciseList.add(jumpingJacks)

        ///// #2
        val wallSit = ExerciseModel (2, "Wall Sit", R.drawable.ic_wall_sit, isCompleted = false, isSelected = false)
        exerciseList.add(wallSit)

        ///// #3
        val pushUp = ExerciseModel (3, "Push Up", R.drawable.ic_push_up, isCompleted = false, isSelected = false)
        exerciseList.add(pushUp)

        ///// #4
        val abdominalCrunch = ExerciseModel (4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, isCompleted = false, isSelected = false)
        exerciseList.add(abdominalCrunch)

        ///// #5
        val setUpOnChair = ExerciseModel (5, "Set-Up Onto Chair", R.drawable.ic_step_up_onto_chair, isCompleted = false, isSelected = false)
        exerciseList.add(setUpOnChair)

        ///// #6
        val squat = ExerciseModel (6, "Squat", R.drawable.ic_squat, isCompleted = false, isSelected = false)
        exerciseList.add(squat)

        ///// #7
        val tricepDipOnChair = ExerciseModel (7, "Tricep Dip On Chair", R.drawable.ic_triceps_dip_on_chair, isCompleted = false, isSelected = false)
        exerciseList.add(tricepDipOnChair)

        ///// #8
        val plank = ExerciseModel (8, "Plank", R.drawable.ic_plank, isCompleted = false, isSelected = false)
        exerciseList.add(plank)

        ///// #9
        val highKneesRunningInPlace = ExerciseModel (9, "High Knees Running In Place", R.drawable.ic_high_knees_running_in_place, isCompleted = false, isSelected = false)
        exerciseList.add(highKneesRunningInPlace)

        ///// #10
        val lunges = ExerciseModel (10, "Lunges", R.drawable.ic_lunge, isCompleted = false, isSelected = false)
        exerciseList.add(lunges)

        ///// #11
        val pushupAndRotation = ExerciseModel (11, "Push Up And Rotation", R.drawable.ic_push_up_and_rotation, isCompleted = false, isSelected = false)
        exerciseList.add(pushupAndRotation)

        ///// #12
        val sidePlank = ExerciseModel (12, "Side Plank", R.drawable.ic_side_plank, isCompleted = false, isSelected = false)
        exerciseList.add(sidePlank)

        //endregion

        return exerciseList
    }
}