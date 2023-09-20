package com.jobportal.securecalls;

import constants.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import reporting.ExtentLogger;

import java.io.File;

public class TestPutCallOverJobRepo {


    @Test
    public void updateAJobDetails() {

        File file = new File(Constants.RESOURCES_FOLDER_PATH + "/json/update-job-detail.json");
        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .headers("Content-Type", "application/json", "Accept", "application/json")
                .body(file)
                .auth().basic(Constants.USERNAME, Constants.PASSWORD)
                .when()
                .put(Constants.UPDATE_A_JOB_ENDPOINT)
                .then()
                .extract().response();

        ExtentLogger.logResponseInReport("Job details updated", response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
