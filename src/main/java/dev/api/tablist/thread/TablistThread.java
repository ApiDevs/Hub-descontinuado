package dev.api.tablist.thread;

import dev.api.tablist.Tablist;

public class TablistThread extends Thread {

    private final Tablist tab;

    public TablistThread(Tablist tab) {
        setName("Storm Tab Thread");
        setDaemon(true);

        this.tab = tab;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                tab.getPlayerTablist().forEach((uniqueId, tab) -> tab.update());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(250L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}