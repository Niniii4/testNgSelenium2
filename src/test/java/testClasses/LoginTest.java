package testClasses;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners({testListeners.LoginListener.class})
public class LoginTest {
    private WebDriver driver;

    @BeforeTest
    public void beforeWholeTest() {
        ChromeDriverManager.getInstance().version("2.40").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://zero.webappsecurity.com/");
    }

    @Parameters({"user", "pass"})
    @Description("Insert Credentials for signing in and submit")
    @Test(priority = 1, description = "Insert credentials for signing in and submit", groups = {"loginTestGroup"})
    public void loginTest(@Optional("username") String username, @Optional("password") String password)
        throws InterruptedException {
        WebElement btnSignIn = driver.findElement(By.id("signin_button"));
        btnSignIn.click();
        WebElement inputUsername = driver.findElement(By.id("user_login"));
        inputUsername.click();
        inputUsername.sendKeys(username);
        WebElement inputPass = driver.findElement(By.id("user_password"));
        inputPass.click();
        inputPass.sendKeys(password);
        WebElement btnSubmit = driver.findElement(By.className("btn-primary"));
        btnSubmit.click();
        Thread.sleep(300);
        boolean userLoggedIn = driver.findElement(By.className("icon-user")).isDisplayed();
        Assert.assertTrue(userLoggedIn);
//        Assert.assertFalse(userLoggedIn); //for scenario of failed test
    }

    @Test(priority = 2, dependsOnMethods = {"loginTest"})
    public void homePageTest(ITestContext context) {
        String pageSlogan = driver.findElement(By.className("brand")).getText();
        context.setAttribute("ZeroBank", pageSlogan);
        Assert.assertEquals(pageSlogan, "Zero Bank");
    }

    @Test(priority = 3, dependsOnMethods = {"homePageTest"})
    public void myMoneyMapNavigationTest(ITestContext context) {
        WebElement myMoneyMap= driver.findElement(By.id("money_map_tab"));
        myMoneyMap.click();
        String contextAttribute = (String) context.getAttribute("ZeroBank");
        System.out.println(contextAttribute);
        Assert.assertEquals(contextAttribute, "Zero Bank");
    }

    @AfterTest
    public void afterWholeTest() {
        driver.close();
        driver.quit();
    }
}