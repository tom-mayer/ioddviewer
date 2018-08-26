package com.tinkerdesk.ioddviewer.event;

import org.springframework.context.ApplicationEvent;

import java.io.File;

public class WatchedFileChangedEvent extends ApplicationEvent {

    private File file;
    private FileChangeType type;

    public WatchedFileChangedEvent(Object source, File file, FileChangeType type) {
        super(source);
        this.file = file;
        this.type = type;
    }

    public File getFile(){
        return this.file;
    }

    public FileChangeType getType(){
        return this.type;
    }
}
