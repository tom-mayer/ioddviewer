package com.tinkerdesk.ioddviewer.model.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class DeviceIdentityDTO {

    @JacksonXmlProperty(isAttribute = true)
    private String deviceId;

    @JacksonXmlProperty(isAttribute = true)
    private String vendorName;

    @JacksonXmlProperty(localName = "DeviceName")
    private DeviceNameDTO deviceName;

}
