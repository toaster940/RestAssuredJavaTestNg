package constants;

import utils.FileReader;

import java.io.File;

public final class Constants {
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String BASE_URI = getBaseUri();
    public static final String USERNAME = getUsername();
    public static final String PASSWORD = getPassword();
    public static final String GET_ALL_JOB_ENDPOINT = "/secure/webapi/all";
    public static final String CREATE_A_JOB_ENDPOINT = "/secure/webapi/add";
    public static final String UPDATE_A_JOB_DETAILS_ENDPOINT = "/secure/webapi/update/details";
    public static final String UPDATE_A_JOB_ENDPOINT = "/secure/webapi/update/";
    public static final String DELETE_A_JOB_ENDPOINT = "/secure/webapi/remove/";
    public static final String GET_A_JOB_DESCRIPTION_ENDPOINT = "/secure/webapi/find";
    public static final String RESOURCES_FOLDER_PATH = PROJECT_PATH + File.separator + "/src/test/resources";


    private static String getBaseUri() {
        return new FileReader().getProperty("base_uri");
    }

    private static String getUsername() {
        return new FileReader().getProperty("username");
    }

    private static String getPassword() {
        return new FileReader().getProperty("password");
    }

}
