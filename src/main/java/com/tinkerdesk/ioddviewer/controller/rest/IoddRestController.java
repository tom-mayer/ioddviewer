package com.tinkerdesk.ioddviewer.controller.rest;

import com.tinkerdesk.ioddviewer.model.Iodd;
import com.tinkerdesk.ioddviewer.service.IoddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IoddRestController {

    @Autowired
    IoddService ioddService;

    @RequestMapping(path = "/iodds", method = RequestMethod.GET, produces = "application/json")
    public List<Iodd> getIODDs(){
        return this.ioddService.getAllIodds();
    }

    @RequestMapping(path = "/iodd/{id}", method = RequestMethod.GET,  produces = "application/json")
    public Iodd getIODD(@PathVariable("id") String id){
        return this.ioddService.getIodd(id);
    }
}

