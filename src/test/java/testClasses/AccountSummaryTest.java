package testClasses;

import dataProviders.AccountSummaryDataProvider;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.qameta.allure.Description;
import java.util.List;
import models.AccountSummaryModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners({testListeners.LoginListener.class})
public class AccountSummaryTest {
    private WebDriver driver;

    @BeforeTest
    public void beforeWholeTest() {
        ChromeDriverManager.getInstance().version("2.40").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://zero.webappsecurity.com/");
    }

    @Parameters({"user", "pass"})
    @BeforeClass
    public void beforeAllTestCases(@Optional("username") String username, @Optional("password") String password) {
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
    }

    @Description("Verify account summary")
    @Test(priority = 1, description = "Verify account summary", dataProvider = "my-data-provider", dataProviderClass = AccountSummaryDataProvider.class)
    public void accountSummaryTest(String account) {
        List<WebElement> accountActivityHeader = driver.findElements(By.className("board-header"));
        boolean accountExists =
            accountActivityHeader.stream().map(WebElement::getText).anyMatch(accText -> accText.equals(account));
        Assert.assertTrue(accountExists);
    }

    @Description("DDT and Models")
    @Test(priority = 2, description = "Work with models", dependsOnMethods = {"accountSummaryTest"}, dataProvider = "my-model-data-provider",
    dataProviderClass = AccountSummaryDataProvider.class)
    public void accountSummaryModelTest(String tableRow, String tdAccountName, String tdBalance, String accountName, String balance){
        WebElement accountNameField = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div/div[1]/div/table/tbody/tr["+tableRow+"]/td["+tdAccountName+"]"));
        WebElement balanceField = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div/div[1]/div/table/tbody/tr["+tableRow+"]/td["+tdBalance+"]"));
        AccountSummaryModel actualAccountActivity = AccountSummaryModel.builder()
            .accountName(accountNameField.getText())
            .balance(balanceField.getText())
            .build();
        AccountSummaryModel expectedCheckingAccountActivity = AccountSummaryModel.builder()
            .accountName(accountName)
            .balance(balance)
            .build();
        Assert.assertEquals(actualAccountActivity,expectedCheckingAccountActivity, "You are doing something wrong");
    }

    @AfterClass
    public void afterAllTestCases() {
        driver.close();
    }

    @AfterTest
    public void afterWholeTest() {
        driver.quit();
    }

    //method for data provider inside test class, attribute dataProviderClass not needed
    @DataProvider(name = "my-data-provider")
    public static Object[][] dataProviderMethod() {
        return new Object[][] {
            {"Cash Accounts"},
            {"Investment Accounts"},
            {"Credit Accounts"},
            {"Loan Accounts"}
        };
    }
}