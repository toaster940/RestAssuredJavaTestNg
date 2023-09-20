package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExtentReport {

    private static final ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + getCurrentDateTime() + "index.html");
    private static ExtentReports extentReports;

    /**
     * This method is to initialize the Extent Report
     */
    public static void initExtentReport() {
        try {
            if (Objects.isNull(extentReports)) {
                extentReports = new ExtentReports();
                extentReports.attachReporter(extentSparkReporter);
                InetAddress ip = InetAddress.getLocalHost();
                String hostname = ip.getHostName();
                extentReports.setSystemInfo("Host Name", hostname);
                extentReports.setSystemInfo("Environment", "API Automation - RestAssured");
                extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
                extentSparkReporter.config().setDocumentTitle("HTML Report");
                extentSparkReporter.config().setReportName("API Automation Test");
                extentSparkReporter.config().setTheme(Theme.DARK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTest(String testCaseName) {
        ExtentManager.setExtentTest(extentReports.createTest(testCaseName));
    }

    public static void flushExtentReport() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
        ExtentManager.unload();
        try {
            Desktop.getDesktop().browse(new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss");
        return dateTimeFormatter.format(LocalDateTime.now());
    }

}
