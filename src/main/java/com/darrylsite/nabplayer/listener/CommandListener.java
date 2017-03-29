/**
 * @author Kpizingui Darryl
 * mail : nobi_8@hotmail.com
 * http://darrylsite.com
 * (c)2009
 * Cette classe fait partie de Nabplayer 1.1
 * Nabplayer est un lecteur de musique mp3
 */
package com.darrylsite.nabplayer.listener;

import com.darrylsite.nabplayer.core.PlayerModel;
import com.darrylsite.nabplayer.view.PlayerView;

public class CommandListener implements java.util.EventListener {

    private PlayerModel model;
    private PlayerView player;

    public CommandListener(PlayerModel model, PlayerView player) {
        this.model = model;
        this.player = player;
    }

    public void processCmd(String cmd) {
        if (cmd.equals("play")) {
            if (model.isPaused()) {
                model.getBoxPlayer().pause();
                model.setPaused(false);
                return;
            }
            if (model.getBoxPlayer().play != null) {
                model.getBoxPlayer().play.close();
            }
            if (model.getSongData().isEmpty()) {
                return;
            }
            if (player.songList.isSelectionEmpty()) {
                player.songList.setSelectedIndex(0);
            }
            model.setCurPath(model.getSongData().get(player.songList.getSelectedIndex()));
            model.setCurIndex(player.songList.getSelectedIndex());
            model.getBoxPlayer().setPath(model.getCurPath());
            model.getBoxPlayer().startPlaying();
            model.setPlaying(true);
            model.setStop(false);
        } else if (cmd.equals("pause")) {
            model.getBoxPlayer().pause();
            model.setPaused(!model.isPaused());
        } else if (cmd.equals("stop")) {
            model.getBoxPlayer().stop();
            model.setPlaying(false);
            model.setPaused(true);
            model.setPaused(false);
        } else if (cmd.equals("up")) {
            if (player.scrol.getVerticalScrollBar().getMinimum() < player.scrol.getVerticalScrollBar().getValue()) {
                player.scrol.getVerticalScrollBar().setValue(player.scrol.getVerticalScrollBar().getValue() - 10);
            }
            return;
        } else if (cmd.equals("down")) {
            if (player.scrol.getVerticalScrollBar().getMaximum() > player.scrol.getVerticalScrollBar().getValue()) {
                player.scrol.getVerticalScrollBar().setValue(player.scrol.getVerticalScrollBar().getValue() + 10);
            }
            return;
        } else if (cmd.equals("fastbk")) {
            processCmd("stop");
            if (model.getCurIndex() == 0) {
                this.processCmd("play");
                return;
            }
            model.setCurIndex(model.getCurIndex() - 1);
            player.songList.setSelectedIndex(model.getCurIndex());
            this.processCmd("play");
        } else if (cmd.equals("fastwd")) {
            processCmd("stop");
            if (model.getCurIndex() == (player.songList.getModel().getSize() - 1)) {
                this.processCmd("play");
                return;
            }
            model.setCurIndex(model.getCurIndex() + 1);
            model.setCurPath(model.getSongData().get(model.getCurIndex()));
            player.songList.setSelectedIndex(model.getCurIndex());
            processCmd("play");
        } else if (cmd.equals("vol")) {
            if (model.isMute()) {
                model.getBoxPlayer().play.setVolume(model.getVolume());
            } else {
                model.getBoxPlayer().play.setVolume(model.getBoxPlayer().play.getMinVolume());
            }
            model.setMute(!model.isMute());
        } else if (cmd.equals("about")) {
            player.popMenu.setLocation(20, 20);
            player.popMenu.show(player, 10, 230);


        }
    }
}
