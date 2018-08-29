package com.tinkerdesk.ioddviewer.model.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class PhysicalLayerDTO {

    @JacksonXmlProperty(isAttribute = true)
    private String bitrate;

    @JacksonXmlProperty(isAttribute = true)
    private String baudrate;
}
