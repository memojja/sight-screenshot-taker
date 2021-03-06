package com.screenshotservice.screenshotservice.service;

import com.screenshotservice.screenshotservice.utils.ScreenShotUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class WebPageScreenShotTaker {

    private WebDriver driver;

    @PostConstruct
    public void initDriver() {
        System.setProperty("webdriver.chromcapturee.driver", ScreenShotUtil.webDriverExeUrl);
        driver=new ChromeDriver();
        driver.manage().window().setPosition(new Point(-2000, 0));
    }

    public void capture(String site) {
        driver.get(site);
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String fileName = UUID.randomUUID().toString() + ".png";
        try {
            FileUtils.copyFile(scrFile, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Took Screenshot for " + site + " and saved as" + fileName);
    }

    @PreDestroy
    public void destroy() {
        driver.quit();
    }



}
