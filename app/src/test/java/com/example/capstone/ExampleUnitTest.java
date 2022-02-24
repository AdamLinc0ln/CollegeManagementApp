package com.example.capstone;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.capstone.Activities.AssessmentActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void getAssessmentType_PA(){
        String result = AssessmentActivity.getAssessmentType(0);
        assertEquals(result, "PA");
    }
    @Test
    public void getAssessmentType_OA(){
        String result = AssessmentActivity.getAssessmentType(1);
        assertEquals(result, "OA");
    }
}