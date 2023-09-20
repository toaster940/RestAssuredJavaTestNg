package com.jobportal.securecalls;

import constants.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import reporting.ExtentLogger;

import java.io.File;

public class TestPostCallOverJobRepo {

    @Test(description = "Create a new job in repo")
    public void verifyThatUserCanCreateJob() {

        File file = new File(Constants.RESOURCES_FOLDER_PATH + "/json/create-new-job.json");
        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .body(file)
                .headers("Content-Type", "application/json", "Accept", "application/json")
                .auth().basic(Constants.USERNAME, Constants.PASSWORD)
                .when()
                .post(Constants.CREATE_A_JOB_ENDPOINT)
                .then()
                .extract()
                .response();

        ExtentLogger.logResponseInReport("Created a Job", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 201);

    }

    @Test(description = "User should be unable to create job without authorization")
    public void verifyThatUserUnAuthorisedToCreateJob() {

        File file = new File(Constants.RESOURCES_FOLDER_PATH + "/json/create-new-job.json");
        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .body(file)
                .headers("Content-Type", "application/json", "Accept", "application/json")
                .auth().basic("wrongUsername", "WrongPassword")
                .when()
                .post(Constants.CREATE_A_JOB_ENDPOINT)
                .then()
                .extract()
                .response();

        ExtentLogger.logResponseInReport("User is unauthorised to create a job", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 401);

    }
}
