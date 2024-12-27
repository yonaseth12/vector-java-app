package components;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

import utils.EventListener;
import utils.TimeFormatter;



public class ControlsPanel extends JPanel {
    public JPanel bottomTimelinePanel, bottomControlsToolsPanel;
    public JSlider timelineSlider, volumeSlider;
    public JButton playPause, previousFile, nextFile, looper, reset, open, volumeup, volumedown, mute, playlist, controls;
    public JLabel totalTimeLabel, currentTimeLabel;

    
    public ControlsPanel() {
        // Do nothing
    }

    public void addControlsPanelComponents(MainFrame mainFrame){

        bottomTimelinePanel = new JPanel();
        bottomControlsToolsPanel = new JPanel();
        
        add(bottomTimelinePanel);
        bottomTimelinePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(bottomControlsToolsPanel);
        bottomControlsToolsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 3));
        
        createBottomTimelineControlsPanel(mainFrame);
        createBottomControlsToolsPanel(mainFrame);

    }

    private void createBottomTimelineControlsPanel(MainFrame mainFrame) {

        timelineSlider = new JSlider(0,300);
        timelineSlider.setPreferredSize(new Dimension(mainFrame.getWidth() - 190, 18));
        timelineSlider.setValue(0);
        bottomTimelinePanel.add(timelineSlider);

        currentTimeLabel = new JLabel("--:--:--/");
        currentTimeLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        currentTimeLabel.setForeground(Color.BLACK);
        totalTimeLabel = new JLabel("--:--:--");
        totalTimeLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        totalTimeLabel.setForeground(Color.BLACK);
        bottomTimelinePanel.add(currentTimeLabel);
        bottomTimelinePanel.add(totalTimeLabel);

        timelineSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e){
                if(MainFrame.path.getPath() == ""){
                    //do nothing
                }
                else{
                    double setTime = ((double)timelineSlider.getValue() / 300.0) * MainFrame.clip.getMicrosecondLength();
                    MainFrame.timeLineTracker = (long)setTime;
                    MainFrame.clip.setMicrosecondPosition(MainFrame.timeLineTracker);
                    currentTimeLabel.setText(TimeFormatter.formattedTimeOf(MainFrame.timeLineTracker) + "/ ");
                }
            }
        });

    }

    private void createBottomControlsToolsPanel(MainFrame mainFrame) {

        playPause = new JButton("▶️");
        playPause.setMnemonic(KeyEvent.VK_ENTER);
        playPause.setToolTipText("Play/Pause");
        playPause.setBackground(Color.LIGHT_GRAY);
        playPause.setFocusable(false);
        previousFile = new JButton("⏮️");
        previousFile.setMnemonic(KeyEvent.VK_LEFT);
        previousFile.setToolTipText("Previous Music");
        previousFile.setBackground(Color.LIGHT_GRAY);
        previousFile.setFocusable(false);
        nextFile = new JButton("⏭️");
        nextFile.setMnemonic(KeyEvent.VK_RIGHT);
        nextFile.setToolTipText("Next Music");
        nextFile.setBackground(Color.LIGHT_GRAY);
        nextFile.setFocusable(false);
        looper = new JButton("🔄");
        looper.setMnemonic(KeyEvent.VK_L);
        looper.setToolTipText("Loop");
        looper.setBackground(Color.LIGHT_GRAY);
        looper.setFocusable(false);
        reset = new JButton("↩️");
        reset.setMnemonic(KeyEvent.VK_R);
        reset.setToolTipText("Reset");
        reset.setBackground(Color.LIGHT_GRAY);
        reset.setFocusable(false);
        controls = new JButton("🎛️");
        controls.setToolTipText("Controls/Effects");
        controls.setBackground(Color.LIGHT_GRAY);
        controls.setMnemonic(KeyEvent.VK_C);
        controls.setFocusable(false);;
        mute = new JButton(" 🔈 ");
        mute.setToolTipText(" Mute ");
        mute.setBackground(Color.LIGHT_GRAY);
        mute.setMnemonic(KeyEvent.VK_M);
        mute.setFocusable(false);
        volumedown = new JButton("🔉");
        volumedown.setToolTipText("Decrease Volume");
        volumedown.setBackground(Color.LIGHT_GRAY);
        volumedown.setMnemonic(KeyEvent.VK_DOWN);
        volumedown.setFocusable(false);
        volumeup = new JButton("🔊");
        volumeup.setToolTipText("Increase Volume");
        volumeup.setBackground(Color.LIGHT_GRAY);
        volumeup.setMnemonic(KeyEvent.VK_UP);
        volumeup.setFocusable(false);
        playlist = new JButton("♬");
        playlist.setToolTipText("Playlist");
        playlist.setBackground(Color.LIGHT_GRAY);
        playlist.setMnemonic(KeyEvent.VK_P);
        playlist.setFocusable(false);
        open = new JButton("📁");
        open.setMnemonic(KeyEvent.VK_O);
        open.setToolTipText("Open File");
        open.setBackground(Color.LIGHT_GRAY);
        open.setFocusable(false);

        volumeSlider = new JSlider(0, 100);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(MainFrame.path.getPath() == ""){
                    //do nothing
                }
                else{
                    if(volumeSlider.getValue() == 0){
                        MainFrame.volume.setValue(-80.0f);
                    }
                    else{
                        MainFrame.currentVolume = (float)(20 * Math.log10(volumeSlider.getValue() / 50.0));
                        MainFrame.volume.setValue(MainFrame.currentVolume);
                        mute.setText(" 🔈 ");
                        MainFrame.muteState = false;
                    }
                    MainFrame.lastRecordedVolume = MainFrame.currentVolume;
                }
            }
        });

        
        bottomControlsToolsPanel.add(playPause);
        bottomControlsToolsPanel.add(previousFile);
        bottomControlsToolsPanel.add(nextFile);
        bottomControlsToolsPanel.add(looper);
        bottomControlsToolsPanel.add(reset);
        bottomControlsToolsPanel.add(open);
        bottomControlsToolsPanel.add(playlist);
        bottomControlsToolsPanel.add(controls);
        bottomControlsToolsPanel.add(mute);
        bottomControlsToolsPanel.add(volumedown);
        bottomControlsToolsPanel.add(volumeSlider);
        bottomControlsToolsPanel.add(volumeup);
        bottomControlsToolsPanel.setBackground(Color.lightGray);

        playPause.addActionListener(new EventListener(mainFrame));
        previousFile.addActionListener(new EventListener(mainFrame));
        nextFile.addActionListener(new EventListener(mainFrame));
        looper.addActionListener(new EventListener(mainFrame));
        reset.addActionListener(new EventListener(mainFrame));
        controls.addActionListener(new EventListener(mainFrame));
        mute.addActionListener(new EventListener(mainFrame));
        volumedown.addActionListener(new EventListener(mainFrame));
        volumeup.addActionListener(new EventListener(mainFrame));
        open.addActionListener(new EventListener(mainFrame));
        playlist.addActionListener(new EventListener(mainFrame));


    }
}
