package dataProviders;

import org.testng.annotations.DataProvider;

public class AccountSummaryDataProvider {
    @DataProvider(name = "my-data-provider")
    public static Object[][] dataProviderMethod() {
        return new Object[][] {
            {"Cash Accounts"},
            {"Investment Accounts"},
            {"Credit Accounts"},
            {"Loan Accounts"}
        };
    }

    @DataProvider(name = "my-model-data-provider")
    public static Object[][] dataProviderModelsMethod() {
        return new Object[][] {
            {"1","1","3","Savings", "$1000"},
            {"2","1","3","Savings", "$1548"},
        };
    }
}
