package com.tanishkej.onerep

import com.tanishkej.onerep.data.model.Workout
import com.tanishkej.onerep.data.util.WorkoutUtil.getOneRep
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class OneRepTests {

    @Test
    fun oneRep_116by6_Test_isCorrect() {
        assertWorkOneRep(100, 6, 116)
    }

    @Test
    fun oneRep_200by6_Test_isCorrect() {
        assertWorkOneRep(200, 6, 232)
    }

    @Test
    fun oneRep_200by4_Test_isCorrect() {
        assertWorkOneRep(200, 4, 218)
    }

    @Test
    fun oneRep_30by2_Test_isCorrect() {
        assertWorkOneRep(30, 2, 31)
    }

    /***
     * When values are both 0, app should not crash and return 0
     */
    @Test
    fun oneRep_0by0_Test_isCorrect() {
        assertWorkOneRep(0, 0, 0)
    }

    /***
     * Divide by 0, test case covered, should return 0
     */
    @Test
    fun oneRep_0by1_Test_isCorrect() {
        assertWorkOneRep(0, 1, 0)
    }

    /***
     * 100lb trying to lift it but doing 0 reps, should be 0
     */
    @Test
    fun oneRep_1by0_Test_isCorrect() {
        assertWorkOneRep(100, 0, 0)
    }

    private fun assertWorkOneRep(weightInLbs:Int, reps: Int, expectedResult: Int){
        val workout = Workout(
            1,
            Date(),
            "Deadlift",
            reps,
            weightInLbs).apply {
            oneRep = getOneRep()
        }
        assertEquals(expectedResult, workout.oneRep)
    }
}