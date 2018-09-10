package com.handlerservice.handlerservice.controller;

import com.handlerservice.handlerservice.HandlerServiceApplication;
import com.handlerservice.handlerservice.model.WebSite;
import com.handlerservice.handlerservice.payload.UploadFileResponse;
import com.handlerservice.handlerservice.service.FileStorageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@RestController
public class ScreenshootController {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


        File file1 = new File( "uploads\\" + fileName);
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(WebSite.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            WebSite customer = (WebSite) jaxbUnmarshaller.unmarshal(file1);

    //RABBİTMQ GAZLA
            customer.getUrl()
                    .parallelStream()
                    .forEach(
                        url ->
                            rabbitTemplate.convertAndSend("jsa.direct1", "jsa.routingkey1",url)

                    );

            System.out.println(customer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }


}

