package com.tinkerdesk.ioddviewer.service;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tinkerdesk.ioddviewer.event.WatchedFileChangedEvent;
import com.tinkerdesk.ioddviewer.model.DTO.IoddDTO;
import com.tinkerdesk.ioddviewer.model.Iodd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class IoddService implements ApplicationListener<WatchedFileChangedEvent>{

    @Autowired
    @Qualifier("iodd")
    private DirectoryWatcherService directory;

    @Autowired
    private ApplicationHelperService helper;

    @Autowired
    private CommandService command;

    private Hashtable<String, Iodd> memoryCache;

    @PostConstruct
    public void init(){
        this.memoryCache = new Hashtable<>();

        List<Iodd> iodds = new ArrayList<>();
        List<File> files = this.directory.getCurrentDirectoryState();
        for (File file: files){
            if(!fileIsIodd(file)){
                continue;
            }
            Iodd iodd = this.convertFileToIodd(file);
            if(iodd != null){
                iodds.add(iodd);
            }
        }

        //TODO: maybe save this in a redis layer
        for(Iodd iodd: iodds){
            this.memoryCache.put(iodd.getId(), iodd);
        }

        directory.startWatching();
        this.command.sendRefreshCommand();
    }

    public List<Iodd> getAllIodds(){
        return new ArrayList<>(this.memoryCache.values());
    }

    public Iodd getIodd(String id){
        if(this.memoryCache.containsKey(id)){
            return this.memoryCache.get(id);
        }
        return null;
    }

    @Override
    public void onApplicationEvent(WatchedFileChangedEvent watchedFileChangedEvent) {
        switch (watchedFileChangedEvent.getType()){
            case MODIFIED:
            case CREATED:
                onFileCreated(watchedFileChangedEvent);
                break;
            case DELETED:
                onFileDeleted(watchedFileChangedEvent);
                break;
        }
        this.command.sendRefreshCommand();
    }

    protected void onFileCreated(WatchedFileChangedEvent watchedFileChangedEvent){
        Iodd iodd = convertFileToIodd(watchedFileChangedEvent.getFile());
        if(this.memoryCache.containsKey(iodd.getId())){
            this.memoryCache.remove(iodd.getId());
        }
        this.memoryCache.put(iodd.getId(), iodd);
    }

    protected void onFileDeleted(WatchedFileChangedEvent watchedFileChangedEvent){
        Iodd iodd = convertFileToIodd(watchedFileChangedEvent.getFile());
        if(this.memoryCache.containsKey(iodd.getId())){
            this.memoryCache.remove(iodd.getId());
        }
    }

    protected Iodd convertFileToIodd(File file){
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        IoddDTO transfer = null;
        try {
            transfer = mapper.readValue(file, IoddDTO.class);
        } catch (IOException e) {
            return null;
        }
        return transfer.toIodd();
    }

    protected boolean fileIsIodd(File file){
        return file.exists()
            && !file.isDirectory()
            && file.getName().endsWith(".xml");
    }
}
