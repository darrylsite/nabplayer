/**
 * @author Kpizingui Darryl
 * mail : nabster@daryylsite.com
 * http://darrylsite.com
 * (c)2009
 * Cette classe fait partie de Nabplayer 1.1
 * Nabplayer est un lecteur de musique mp3
 */
package com.darrylsite.nabplayer.core;

import nabplayer.listener.PlayingListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javazoom.jl.decoder.JavaLayerException;

public class boxPlayer implements Runnable {

    private String son;
    public Player play;
    private Thread thread;
    public boolean onPlaying = false;
    public float totalms = 0;
    public float volume = -12.617197f;
    List<PlayingListener> listener = new ArrayList<PlayingListener>();

    public boxPlayer(String son) {
        this.son = son;
    }

    public void setVolume(float f) {
        volume = f;
        if (play != null) {
            if (play.getPosition() != 0) {
                play.setVolume(f);
            }
        }
    }

    public void startPlaying() {
        onPlaying = true;
        File fichier = new File(son);
        try {
            FileInputStream in = new FileInputStream(fichier);
            play = new Player(in);
            play.setVolume(volume);

        } catch (FileNotFoundException e) {
            logging.appLogger.getLogger().log(Level.WARNING, e.getMessage());
        } catch (JavaLayerException e)
        {
            logging.appLogger.getLogger().log(Level.WARNING, e.getMessage());
        }
        thread = new Thread(this);
        thread.start();

        decoder dec = new decoder(son);
        this.totalms = dec.info();
    }

    public void setPath(String s) {
        son = s;
    }

    public void stop() {
        try {
            play.onStop = true;
            onPlaying = false;
            thread.join();
        } catch (Exception e) {
        }
    }

    public void pause() {
        try {
            play.onPause = !play.onPause;
        } catch (Exception e) 
        {
            logging.appLogger.getLogger().log(Level.WARNING, e.getMessage());
        }
    }

    public void run() {
        try {
            this.firePlayingListener(PlayingListener.Status.Starting);
            play.play();
        } catch (Exception e) 
        {
            logging.appLogger.getLogger().log(Level.WARNING, e.getMessage());
        } finally {
            if (onPlaying) {
                this.firePlayingListener(PlayingListener.Status.Ending);
            }
        }
    }

    public Player getPlayer() {
        return play;
    }

    public void addPlayingListener(PlayingListener p) {
        listener.add(p);
    }

    private void firePlayingListener(PlayingListener.Status st) {
        for (PlayingListener p : listener) {
            p.processPlaying(st);
        }
    }
}
