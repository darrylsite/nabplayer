/**
 * @author Kpizingui Darryl
 * mail : nobi_8@hotmail.com
 * http://darrylsite.tk
 * (c)2009
 * Cette classe fait partie de Nabplayer 1.1
 * Nabplayer est un lecteur de musique mp3
 */
package com.darrylsite.nabplayer.view;

import com.darrylsite.nabplayer.core.PlayerModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class SongList extends JList {

    PlayerModel model;
    PlayerView player;
    SongList SELF = this;

    public SongList(PlayerModel model, PlayerView player) {
        this.model = model;
        this.player = player;
        init();
        setKeyListener();
        setMouseListener();
    }

    private void init() {
        setCellRenderer(new MyCellRenderer());
        setLayoutOrientation(JList.VERTICAL);
        setVisibleRowCount(7);
        setListData(model.getVirtalSongData().toArray());
        setBounds(0, 0, 200, 100);
        setOpaque(false);
    }

    private void setKeyListener() {
        addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (isSelectionEmpty()) {
                    return;
                }
                if (e.getKeyChar() == KeyEvent.VK_DELETE) {
                    int ind[] = getSelectedIndices();
                    int d = 0;
                    for (int j = 0; j < ind.length; j++) {
                        model.getSongData().remove(j - d);
                        model.getVirtalSongData().remove(j - d);
                        d++;
                    }

                    setListData(model.getVirtalSongData().toArray());
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    player.fireCommandListener("stop");
                    player.fireCommandListener("play");
                } else if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_P) {
                    player.fireCommandListener("pause");
                } else if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_F) {
                    player.fireCommandListener("fastwd");
                } else if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_B) {
                    player.fireCommandListener("fastbk");
                } else if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_M) {
                    player.fireCommandListener("vol");
                } else if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_S) {
                    player.fireCommandListener("stop");
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
    }

    private void setMouseListener() {
        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) {
                    return;
                }
                player.fireCommandListener("stop");
                player.fireCommandListener("play");
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

    }

    class MyCellRenderer extends JLabel implements ListCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getListCellRendererComponent(
                JList list, // the list
                Object value, // value to display
                int index, // cell index
                boolean isSelected, // is the cell selected
                boolean cellHasFocus) // does the cell have focus
        {

            String s = value.toString();
            setText(s);
            setOpaque(false);

            if (isSelected) {
                this.setForeground(Color.red);
            } else {
                this.setForeground(Color.blue);
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            return this;
        }
    }}
