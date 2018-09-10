package com.screenshotservice.screenshotservice.controller;


import com.screenshotservice.screenshotservice.service.WebPageScreenShotTaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenshootController {



    @Autowired
    private WebPageScreenShotTaker webPageScreenShotTaker;

    @GetMapping("/screenShoot")
    public void screenshoot() {
        webPageScreenShotTaker.capture("http://mybakirci.com/projeler/");
    }


}

