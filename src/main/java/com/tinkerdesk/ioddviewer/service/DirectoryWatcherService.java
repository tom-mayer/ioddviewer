package com.tinkerdesk.ioddviewer.service;

import com.tinkerdesk.ioddviewer.event.FileChangeType;
import com.tinkerdesk.ioddviewer.event.WatchedFileChangedEvent;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DirectoryWatcherService {

    private String path;
    private long pollInterval;
    private FileAlterationMonitor monitor;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public boolean isInitialized(){
        return this.path != null && !this.path.equals("") && this.pollInterval != 0;
    }

    public DirectoryWatcherService setPath(String path){
        this.path = path;
        return this;
    }

    public DirectoryWatcherService setInterval(long interval){
        this.pollInterval = interval;
        return this;
    }

    public List<File> getCurrentDirectoryState(){
        if(!this.isInitialized()){
            return null;
        }
        File directory = new File(this.path);
        return  new ArrayList<>(Arrays.asList(directory.listFiles()));
    }

    public boolean startWatching(){
        if(!this.isInitialized()){
            return false;
        }
        DirectoryWatcherService eventSender = this;
        FileAlterationObserver observer = new FileAlterationObserver(this.path);
        this.monitor = new FileAlterationMonitor(this.pollInterval);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            @Override
            public void onFileCreate(File file) {
                applicationEventPublisher.publishEvent(new WatchedFileChangedEvent(eventSender, file, FileChangeType.CREATED));
            }

            @Override
            public void onFileDelete(File file) {
                applicationEventPublisher.publishEvent(new WatchedFileChangedEvent(eventSender, file, FileChangeType.DELETED));
            }

            @Override
            public void onFileChange(File file) {
                applicationEventPublisher.publishEvent(new WatchedFileChangedEvent(eventSender, file, FileChangeType.MODIFIED));
            }
        };
        observer.addListener(listener);
        monitor.addObserver(observer);
        try {
            monitor.start();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean stopWatching(){
        try {
            this.monitor.stop();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
