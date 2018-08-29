package com.tinkerdesk.ioddviewer.model.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class DeviceVariantDTO {

    @JacksonXmlProperty(localName = "ProductName")
    private ProductNameDTO productName;

    @JacksonXmlProperty(localName = "Name")
    private NameDTO name;

}
