package com.tinkerdesk.ioddviewer.model.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class NameDTO {
    @JacksonXmlProperty(isAttribute = true)
    private String textId;
}
