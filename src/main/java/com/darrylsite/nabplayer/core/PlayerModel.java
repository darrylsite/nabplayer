/**
 * @author Kpizingui Darryl
 * mail : nabster@darrylsite.com
 * http://darrylsite.com
 * (c)2009
 * Cette classe fait partie de Nabplayer 1.1
 * Nabplayer est un lecteur de musique mp3
 */
package com.darrylsite.nabplayer.core;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {

    private boolean Playing = false;
    private boolean paused = false;
    private boolean Stop = true;
    private boolean Mute = false;
    private float volume = -12.617197f;
    float maxVol = 5, minVol = -50;
    String curPath = "";
    int curIndex = 0;
    private List<String> songData = new ArrayList<String>();
    private List<String> virtalSongData = new ArrayList<String>();
    private boxPlayer player = new boxPlayer("");

    public PlayerModel() {
        try {
            player.getPlayer().play();
            if (player.play != null) {
                if (player.play.getPosition() != 0) {
                    maxVol = player.play.getMaxVolume();
                    minVol = player.play.getMinVolume();
                    if (minVol < -50) {
                        minVol = -50;
                    }
                    player.setVolume(volume);
                }
            }
        } catch (Exception e)
        {
        }
    }

    public boolean isMute() {
        return Mute;
    }

    public void setMute(boolean Mute) {
        this.Mute = Mute;
    }

    public boolean isPlaying() {
        return Playing;
    }

    public void setPlaying(boolean Playing) {
        this.Playing = Playing;
    }

    public boolean isStop() {
        return Stop;
    }

    public void setStop(boolean Stop) {
        this.Stop = Stop;
    }

    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
    }

    public String getCurPath() {
        return curPath;
    }

    public void setCurPath(String curPath) {
        this.curPath = curPath;
    }

    public float getMaxVol() {
        if (player != null) {
            if (player.play.getPosition() != 0) {
                return player.play.getMaxVolume();
            }
        }
        return maxVol;
    }

    public void setMaxVol(float maxVol) {
        this.maxVol = maxVol;
    }

    public float getMinVol() {
        return minVol;
    }

    public void setMinVol(float minVol) {
        this.minVol = minVol;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public List<String> getSongData() {
        return songData;
    }

    public void setSongData(List<String> songData) {
        this.songData = songData;
    }

    public List<String> getVirtalSongData() {
        return virtalSongData;
    }

    public void setVirtalSongData(List<String> virtalSongData) {
        this.virtalSongData = virtalSongData;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        player.setVolume(volume);
    }

    public synchronized boxPlayer getBoxPlayer() {
        return player;
    }

}
