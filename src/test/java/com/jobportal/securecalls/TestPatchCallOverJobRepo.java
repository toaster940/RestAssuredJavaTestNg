package com.jobportal.securecalls;

import constants.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import reporting.ExtentLogger;

public class TestPatchCallOverJobRepo {

    @Test
    public void updateAnExistingJob() {

        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .queryParam("id", 0)
                .queryParam("jobTitle", "SDET")
                .queryParam("jobDescription", "Security")
                .headers("Content-Type", "application/json", "Accept", "application/json")
                .auth().basic(Constants.USERNAME, Constants.PASSWORD)
                .when()
                .patch(Constants.UPDATE_A_JOB_DETAILS_ENDPOINT)
                .then()
                .extract()
                .response();
        ExtentLogger.logResponseInReport("Updated the job", response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
