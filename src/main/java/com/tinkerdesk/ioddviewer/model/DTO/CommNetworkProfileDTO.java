package com.tinkerdesk.ioddviewer.model.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class CommNetworkProfileDTO {

    @JacksonXmlProperty(isAttribute = true)
    private String iolinkRevision;

    @JacksonXmlProperty(localName = "TransportLayers")
    private TransportLayersDTO transportLayers;
}
