package testListeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class LoginListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("===Starting "+result.getName().toUpperCase());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("===Test "+result.getName().toUpperCase()+" success");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("!!!--------Test "+result.getName().toUpperCase()+" failed-------!!!");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("===Test "+result.getName().toUpperCase()+" skipped");
    }
}
