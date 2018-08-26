package com.tinkerdesk.ioddviewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class CommandService {

    @Autowired
    SimpMessagingTemplate template;

    public void sendRefreshCommand(){
        this.template.convertAndSend("/commands","REFRESH");
    }

}
