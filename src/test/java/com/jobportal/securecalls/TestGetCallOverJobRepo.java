package com.jobportal.securecalls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import constants.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Job;
import pojo.Project;
import reporting.ExtentLogger;
import utils.AssertionUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

public class TestGetCallOverJobRepo {

    private Project project;

    @Test(description = "Get all job from jobs repository")
    public void testGetAllJobs() {

        // Specify the base URL of the API
        String baseURI = Constants.BASE_URI;

        // Define the endpoint
        String endpoint = Constants.GET_ALL_JOB_ENDPOINT;

        Response response = RestAssured.given()
                .baseUri(baseURI)
                .auth().basic("admin", "welcome")
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();

        ExtentLogger.logResponseInReport("Jobs", response.getBody().asPrettyString());
        List<Map<String, Object>> itemRepo = response.jsonPath().getList("$");
        Assert.assertEquals(response.getStatusCode(), 200);
        Map<String, Object> experienceMatch = new HashMap<>();
        List<Object> experienceValues = new ArrayList<>(Arrays.asList("Google", "Apple", "Mobile Iron"));
        experienceMatch.put("experience", experienceValues);
        AssertionUtils.assertListValuesOnIndex(response, 0, "experience", experienceValues);


    }

    @Test(description = "Get a job description by id and job title")
    public void getAJobDescription() {

        Response response = RestAssured.given()
                .baseUri(Constants.BASE_URI)
                .headers("Content-Type", "application/json", "Accept", "application/json")
                .queryParam("id", 1)
                .queryParam("jobTitle", "Software Engg")
                .auth().basic(Constants.USERNAME, Constants.PASSWORD)
                .when()
                .get(Constants.GET_A_JOB_DESCRIPTION_ENDPOINT)
                .then()
                .extract().response();

        ExtentLogger.logResponseInReport("Job description", response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(description = "Validate json schema")
    public void validateJsonSchemaUsingValidators() {
        String baseURI = Constants.BASE_URI;

        // Define the endpoint
        String endpoint = Constants.GET_ALL_JOB_ENDPOINT;

        File file = new File(Constants.RESOURCES_FOLDER_PATH + "/json/json-schema.json");
        Response response = RestAssured.given()
                .baseUri(baseURI)
                .auth().basic("admin", "welcome")
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(file))
                .extract()
                .response();

        ExtentLogger.logResponseInReport("Jobs", response.getBody().asPrettyString());

    }

    @Test(description = "Validate json schema using pojo class")
    public void validateJsonSchemaUsingPojo() {

        String baseURI = Constants.BASE_URI;

        // Define the endpoint
        String endpoint = Constants.GET_ALL_JOB_ENDPOINT;

        File file = new File(Constants.RESOURCES_FOLDER_PATH + "/json/json-schema.json");
        List<Job> job = RestAssured.given()
                .baseUri(baseURI)
                .auth().basic("admin", "welcome")
                .when()
                .get(endpoint)
                .then()
                .extract().response().jsonPath().getList(".", Job.class);

        Assert.assertEquals(1, job.get(0).getJobId());
        Assert.assertEquals("Software Engg", job.get(0).getJobTitle());
        Assert.assertEquals("To develop andriod application", job.get(0).getJobDescription());
        Assert.assertEquals(List.of("Google", "Apple", "Mobile Iron"), job.get(0).getExperience());

        Assert.assertEquals(job.get(0).getProject().get(0).getTechnology(), List.of("Kotlin", "SQL Lite", "Gradle"));
        Assert.assertFalse(job.get(0).getProject().isEmpty());
    }

    @Test(description = "Validate response is in json format")
    public void verifyThatResponseIsInJsonFormat() {
        // Specify the base URL of the API
        String baseURI = Constants.BASE_URI;

        // Define the endpoint
        String endpoint = Constants.GET_ALL_JOB_ENDPOINT;

        Response response = RestAssured.given()
                .baseUri(baseURI)
                .auth().basic("admin", "welcome")
                .when()
                .get(endpoint);

               response.then().statusCode(200);

                response.then().assertThat()
                .contentType(equalTo(("application/json")));

    }
}
