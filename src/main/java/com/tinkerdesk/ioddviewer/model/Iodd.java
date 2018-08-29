package com.tinkerdesk.ioddviewer.model;

import lombok.Data;

@Data
public class Iodd {
    private String id;

    private String vendorName;
    private String deviceName;

    private String ioLinkRevision;
    private String bitrate;

    private String filePath;
}
