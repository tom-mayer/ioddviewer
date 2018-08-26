package com.tinkerdesk.ioddviewer.model.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.tinkerdesk.ioddviewer.model.Iodd;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "IODevice")
public class IoddDTO {

    @JacksonXmlProperty(localName = "ProfileBody")
    private ProfileBodyDTO profileBody;

    @JacksonXmlProperty(localName = "ExternalTextCollection")
    private ExternalTextCollectionDTO externalTextCollection;

    @JacksonXmlProperty(localName = "CommNetworkProfile")
    private CommNetworkProfileDTO commNetworkProfile;

    public Iodd toIodd(){
        Iodd iodd = new Iodd();
        try{
            iodd.setId(this.getProfileBody().getDeviceIdentity().getDeviceId());
        }catch (Exception ex){
            iodd.setId("NOT FOUND");
        }
        try{
            iodd.setVendorName(this.getProfileBody().getDeviceIdentity().getVendorName());
        }catch (Exception ex){
            iodd.setVendorName("NOT FOUND");
        }
        try{
            iodd.setDeviceName("NOT YET");
        }catch (Exception ex){
            iodd.setDeviceName("NOT FOUND");
        }
        try{
            iodd.setIoLinkRevision(this.getCommNetworkProfile().getIolinkRevision());
        }catch (Exception ex){
            iodd.setIoLinkRevision("NOT FOUND");
        }
        try{
            iodd.setBitrate(this.getCommNetworkProfile().getTransportLayers().getPhysicalLayer().getBitrate());
        }catch (Exception ex){
            iodd.setBitrate("NOT FOUND");
        }

        return iodd;
    }
}
