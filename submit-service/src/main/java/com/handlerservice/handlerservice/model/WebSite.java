package com.handlerservice.handlerservice.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@ToString
public class WebSite {

    private List<String> url;

    public List<String> getUrl() {
        return url;
    }

    @XmlElement
    public void setUrl(List<String> url) {
        this.url = url;
    }
}

