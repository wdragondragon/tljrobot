package com.jdragon.tljrobot.client.event.threadEvent;

public class Synchronous extends Thread {
    private SynchronousOperation synchronousOperation;
    Synchronous(SynchronousOperation synchronousOperation){
        this.synchronousOperation = synchronousOperation;
    }
    public void run(){
        synchronousOperation.start();
    }
}
