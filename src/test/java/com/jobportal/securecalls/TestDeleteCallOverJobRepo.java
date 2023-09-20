package com.jobportal.securecalls;

import constants.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import reporting.ExtentLogger;

public class TestDeleteCallOverJobRepo {

    @Test(description = "Delete a job by its id")
    public void deleteAJobById() {

        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .headers("Content-Type", "application/json", "Accept", "application/json")
                .pathParams("id", 20)
                .auth().basic(Constants.USERNAME, Constants.PASSWORD)
                .when()
                .delete(Constants.DELETE_A_JOB_ENDPOINT + "{id}")
                .then()
                .extract()
                .response();

        ExtentLogger.logResponseInReport("Item Deleted response", response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(description = "Unable to delete as ID not found")
    public void verifyCanNotDeleteIdNotFound() {

        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .headers("Content-Type", "application/json", "Accept", "application/json")
                .pathParams("id", 2000)
                .auth().basic(Constants.USERNAME, Constants.PASSWORD)
                .when()
                .delete(Constants.DELETE_A_JOB_ENDPOINT + "{id}")
                .then()
                .extract()
                .response();

        ExtentLogger.logResponseInReport("Item Deleted response", response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.jsonPath().get("message"), "Entry with id = 2000 not found");

    }
}
