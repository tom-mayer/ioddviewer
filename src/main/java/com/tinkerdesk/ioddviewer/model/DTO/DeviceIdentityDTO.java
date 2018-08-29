package com.tinkerdesk.ioddviewer.model.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeviceIdentityDTO {

    @JacksonXmlProperty(isAttribute = true)
    private String deviceId;

    @JacksonXmlProperty(isAttribute = true)
    private String vendorName;

    @JacksonXmlProperty(localName = "DeviceName")
    private DeviceNameDTO deviceName;

    @JacksonXmlProperty(localName = "DeviceVariantCollection")
    private DeviceVariantDTO[] deviceVariantCollection;

}
