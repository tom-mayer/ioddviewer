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
            String deviceName = getNameForKey(this.getProfileBody().getDeviceIdentity().getDeviceName().getTextId());
            iodd.setDeviceName(deviceName);
        }catch (Exception ex){
            try{
                String variantKey = getProfileBody().getDeviceIdentity().getDeviceVariantCollection()[0].getProductName().getTextId();
                if(variantKey == null){
                    variantKey = getProfileBody().getDeviceIdentity().getDeviceVariantCollection()[0].getName().getTextId();
                    if(variantKey != null){
                        iodd.setDeviceName(getNameForKey(variantKey) + " (Variant)");
                    }
                }else{
                    iodd.setDeviceName(getNameForKey(variantKey) + " (Variant)");
                }
            }catch(Exception er){
                iodd.setDeviceName("NOT FOUND");
            }
        }
        try{
            iodd.setIoLinkRevision(this.getCommNetworkProfile().getIolinkRevision());
        }catch (Exception ex){
            iodd.setIoLinkRevision("NOT FOUND");
        }
        try{
            iodd.setBitrate(this.getCommNetworkProfile().getTransportLayers().getPhysicalLayer().getBitrate());
            if(iodd.getBitrate() == null){
                iodd.setBitrate(this.getCommNetworkProfile().getTransportLayers().getPhysicalLayer().getBaudrate());
            }
        }catch (Exception ex){
            iodd.setBitrate("NOT FOUND");
        }

        return iodd;
    }

    protected String getNameForKey(String key){
        for(TextDTO text: this.getExternalTextCollection().getPrimaryLanguage().getText()){
            if(text.getId().equals(key)){
                return text.getValue();
            }
        }
        return "NOT FOUND";
    }
}
