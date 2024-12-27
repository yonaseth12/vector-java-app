package utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import components.MainFrame;
import utils.TimeFormatter;



public class EventListener implements ActionListener {
    private MainFrame mainFrame;
    Thread timeline;

    public EventListener(MainFrame mainFrame){
        this.mainFrame = mainFrame;

        timeline = new Thread(){
            public void run(){
                while (true){
                    if(MainFrame.clip.isActive()){
                        MainFrame.currentInMicro = MainFrame.clip.getMicrosecondPosition();
                        String timeline_tracker = TimeFormatter.formattedTimeOf(MainFrame.currentInMicro) + "/ ";
                        // mainFrame.controls_panel.currentTimeLabel.setText(timeline_tracker);
                        mainFrame.controls_panel.timelineSlider.setValue((int)(300 * MainFrame.currentInMicro/MainFrame.clip.getMicrosecondLength()));
                        System.out.println(timeline_tracker);
                        try {
                            Thread.sleep(100);
                            // Added to reduce overload due to repeated iteration
                        } catch (InterruptedException e) {
                            System.out.println("Thread Interrupted.");
                        }
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Thread Interrupted.");
                        }
                    }
                }
            }
        };
        
        timeline.start();
    }

    @Override
    public void actionPerformed(ActionEvent event){
        try{
            if(event.getSource() != mainFrame.controls_panel.open && event.getSource() != mainFrame.top_menu_bar.OpenfileItem && event.getSource() != mainFrame.controls_panel.playlist && event.getSource() != mainFrame.top_menu_bar.OpenplaylistItem && MainFrame.path.getPath() == ""){
                JOptionPane.showMessageDialog(null, "No music file is chosen to be played!\n\n Please open an audio file!", "No File Chosen", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                if(event.getSource() == mainFrame.controls_panel.playPause){
                    if(mainFrame.controls_panel.playPause.getText().equals("▶️")){
                        MainFrame.clip.start();
                        mainFrame.controls_panel.totalTimeLabel.setText(TimeFormatter.formattedTimeOf(MainFrame.clip.getMicrosecondLength()));
                        mainFrame.controls_panel.playPause.setText("⏸️");
                    }
                    else{
                        MainFrame.clip.stop();
                        mainFrame.controls_panel.playPause.setText("▶️");
                    }
                }

                if(event.getSource() == mainFrame.controls_panel.previousFile && MainFrame.playlistIsActive){
                    if(MainFrame.directoryListing.length > 0){
                        if(MainFrame.clip.isOpen()) {MainFrame.clip.close();}
                        if(mainFrame.musicInPlaylist-1 >= 0 ){
                            mainFrame.musicInPlaylist--;
                            MainFrame.path = MainFrame.directoryListing[mainFrame.musicInPlaylist];
                        }
                        else{
                            mainFrame.musicInPlaylist = MainFrame.directoryListing.length-1;
                            MainFrame.path = MainFrame.directoryListing[mainFrame.musicInPlaylist];
                        }
                        mainFrame.audioStream = AudioSystem.getAudioInputStream(MainFrame.path);
                        MainFrame.clip = AudioSystem.getClip();
                        MainFrame.clip.open(mainFrame.audioStream);
                        MainFrame.clip.start();
                        MainFrame.volume = (FloatControl)MainFrame.clip.getControl(FloatControl.Type.MASTER_GAIN);
                        MainFrame.volume.setValue((float)(20 * Math.log10(mainFrame.controls_panel.volumeSlider.getValue() / 50.0)));
                        mainFrame.controls_panel.totalTimeLabel.setText(TimeFormatter.formattedTimeOf(MainFrame.clip.getMicrosecondLength()));
                        mainFrame.controls_panel.playPause.setText("⏸️");
                        mainFrame.musicLabel.setText(MainFrame.path.getName());
                    }
                }

                if(event.getSource() == mainFrame.controls_panel.nextFile && MainFrame.playlistIsActive){
                    if(MainFrame.directoryListing.length > 0){
                        if(MainFrame.clip.isOpen()) {MainFrame.clip.close();}
                        if(mainFrame.musicInPlaylist+1 > MainFrame.directoryListing.length-1){
                            mainFrame.musicInPlaylist = 0;
                            MainFrame.path = MainFrame.directoryListing[mainFrame.musicInPlaylist];
                        }
                        else{
                            mainFrame.musicInPlaylist++;
                            MainFrame.path = MainFrame.directoryListing[mainFrame.musicInPlaylist];
                        }
                        mainFrame.audioStream = AudioSystem.getAudioInputStream(MainFrame.path);
                        MainFrame.clip = AudioSystem.getClip();
                        MainFrame.clip.open(mainFrame.audioStream);
                        MainFrame.clip.start();
                        MainFrame.volume = (FloatControl)MainFrame.clip.getControl(FloatControl.Type.MASTER_GAIN);
                        MainFrame.volume.setValue((float)(20 * Math.log10(mainFrame.controls_panel.volumeSlider.getValue() / 50.0)));
                        mainFrame.controls_panel.totalTimeLabel.setText(TimeFormatter.formattedTimeOf(MainFrame.clip.getMicrosecondLength()));
                        mainFrame.controls_panel.playPause.setText("⏸️");
                        mainFrame.musicLabel.setText(MainFrame.path.getName());
                    }
                }


                if(event.getSource() == mainFrame.controls_panel.looper){
                    switch(mainFrame.loopcounter){
                        case 0:
                                MainFrame.clip.loop(0);
                                mainFrame.loopcounter = 1;
                                mainFrame.controls_panel.looper.setText("🔄");
                                mainFrame.controls_panel.looper.setToolTipText("Loop-off");
                                break;
                        case 1:
                                MainFrame.clip.loop(1);
                                mainFrame.loopcounter = 2;
                                mainFrame.controls_panel.looper.setText("🔂");
                                mainFrame.controls_panel.looper.setToolTipText("Loop once");
                                break;
                        case 2:
                                MainFrame.clip.loop(Clip.LOOP_CONTINUOUSLY);
                                mainFrame.loopcounter = 0;
                                mainFrame.controls_panel.looper.setText("🔁");
                                mainFrame.controls_panel.looper.setToolTipText("Loop infinitely");
                                break;
                        default:
                                MainFrame.clip.loop(0);
                                mainFrame.controls_panel.looper.setText("🔄");
                    }
                }

                if(event.getSource() == mainFrame.controls_panel.reset){
                    MainFrame.clip.setMicrosecondPosition(0);
                }

                if(event.getSource() == mainFrame.controls_panel.mute || event.getSource() == mainFrame.top_menu_bar.MuteItem){
                    if(MainFrame.muteState){
                        mainFrame.controls_panel.volumeSlider.setValue((int)(50.0 * Math.pow(10, MainFrame.lastRecordedVolume/20.0)));
                        mainFrame.controls_panel.mute.setText(" 🔈 ");
                        mainFrame.controls_panel.mute.setToolTipText("Mute");
                        MainFrame.muteState = false;
                    }
                    else{
                        mainFrame.controls_panel.volumeSlider.setValue(0);
                        mainFrame.controls_panel.mute.setText("🔇");
                        mainFrame.controls_panel.mute.setToolTipText("Unmute");
                        MainFrame.muteState = true;
                    }
                }

                if(event.getSource() == mainFrame.controls_panel.volumedown || event.getSource() == mainFrame.top_menu_bar.volumeDownItem){
                    if(mainFrame.controls_panel.volumeSlider.getValue() >= 1)
                        mainFrame.controls_panel.volumeSlider.setValue(mainFrame.controls_panel.volumeSlider.getValue() - 1);
                    else
                        mainFrame.controls_panel.volumeSlider.setValue(0);
                }

                if(event.getSource() == mainFrame.controls_panel.controls){
                    MainFrame.reverbration.setValue(MainFrame.reverbration.getValue() + 1000);
                }

                if(event.getSource() == mainFrame.controls_panel.volumeup || event.getSource() == mainFrame.top_menu_bar.volumeUpItem){
                    if(mainFrame.controls_panel.volumeSlider.getValue() <= 99)
                        mainFrame.controls_panel.volumeSlider.setValue(mainFrame.controls_panel.volumeSlider.getValue() + 1);
                    else
                        mainFrame.controls_panel.volumeSlider.setValue(100);
                }

                if(event.getSource() == mainFrame.controls_panel.open  || event.getSource() == mainFrame.top_menu_bar.OpenfileItem){
                    MainFrame.fileC.getChoosableFileFilters();
                    int choice = MainFrame.fileC.showOpenDialog(null);
                    if(choice==JFileChooser.APPROVE_OPTION){
                        if(MainFrame.clip.isOpen())   {MainFrame.clip.close();}
                        MainFrame.path = MainFrame.fileC.getSelectedFile();
                        mainFrame.audioStream = AudioSystem.getAudioInputStream(MainFrame.path);
                        MainFrame.clip = AudioSystem.getClip();
                        MainFrame.clip.open(mainFrame.audioStream);
                        MainFrame.clip.start();

                        mainFrame.TitlePlaylist.setText("");
                        mainFrame.playlist_label.setText("");
                        
                        MainFrame.volume = (FloatControl)MainFrame.clip.getControl(FloatControl.Type.MASTER_GAIN);
                        MainFrame.volume.setValue((float)(20 * Math.log10(mainFrame.controls_panel.volumeSlider.getValue() / 50.0)));
                        mainFrame.musicLabel.setText(MainFrame.path.getName());
                        mainFrame.controls_panel.playPause.setText("⏸️");

                        // Runnable  doRun;
                        // try {
                            // doRun = new Runnable() {
                            //     public void run () {
                            //         while (MainFrame.clip.isActive()){
                            //             timelineSlider.setValue((int)((((float)MainFrame.clip.getMicrosecondPosition())/MainFrame.clip.getMicrosecondLength()) * 300.0));
                            //             try {Thread.sleep(1000);}
                            //             catch (InterruptedException e) {}
                            //         }
                            
                                    
                                //}
                            //};
                            //doRun.run();
                        // } catch (Exception e1) {System.out.println("Error alert!!!"); //do nothing}
                        
                        mainFrame.controls_panel.totalTimeLabel.setText(TimeFormatter.formattedTimeOf(MainFrame.clip.getMicrosecondLength()));

                        mainFrame.musicInPlaylist = 0;
                        MainFrame.playlistIsActive = false;
                    }
                }

                if(event.getSource() == mainFrame.controls_panel.playlist || event.getSource() == mainFrame.top_menu_bar.OpenplaylistItem){
                    MainFrame.fileC.getChoosableFileFilters();
                    int choice = MainFrame.fileC.showOpenDialog(null);
                    if(choice==JFileChooser.APPROVE_OPTION){
                        if(MainFrame.clip.isOpen()) {MainFrame.clip.close();}
                        MainFrame.path = MainFrame.fileC.getSelectedFile();
                        String parentlocation = MainFrame.path.getParent();
                        File directory = new File(parentlocation);
                        MainFrame.directoryListing = directory.listFiles();
                        MainFrame.playlistIsActive = true;
                        for(int i = 0; i < MainFrame.directoryListing.length ; i++){
                            if(MainFrame.directoryListing[i].equals(MainFrame.path)){
                                mainFrame.musicInPlaylist = i;
                                break;
                            }
                        }

                    //Visual display
                    mainFrame.TitlePlaylist.setText("<html> &nbsp <h3>Playlist  - " + MainFrame.directoryListing[0].getParentFile().getName() + "</h3><hr></html>");
                    String allcontainer = "<html><p overflow=scroll>";
                    for(int i = 0; i < MainFrame.directoryListing.length; i++){
                        if(MainFrame.directoryListing[i].getName().length() > 40)
                            allcontainer = allcontainer + " &nbsp " + (1+i) + ". " + MainFrame.directoryListing[i].getName().substring(0, 40) + "... &nbsp <br>";
                        else
                            allcontainer = allcontainer + " &nbsp " + (1+i) + ". " + MainFrame.directoryListing[i].getName() + "<br>";
                    }
                    allcontainer = allcontainer + "</p></html>";
                    mainFrame.playlist_label.setText(allcontainer);
                    mainFrame.playlist_panel.add(mainFrame.playlist_label, BorderLayout.WEST);
                    mainFrame.playlist_panel.setAlignmentY(Component.TOP_ALIGNMENT);
                    }
                    if(MainFrame.playlistIsActive){
                        mainFrame.audioStream = AudioSystem.getAudioInputStream(MainFrame.path);
                        MainFrame.clip = AudioSystem.getClip();
                        MainFrame.clip.open(mainFrame.audioStream);
                        MainFrame.clip.start();
                        MainFrame.volume = (FloatControl)MainFrame.clip.getControl(FloatControl.Type.MASTER_GAIN);
                        MainFrame.volume.setValue((float)(20 * Math.log10(mainFrame.controls_panel.volumeSlider.getValue() / 50.0)));
                        mainFrame.controls_panel.totalTimeLabel.setText(TimeFormatter.formattedTimeOf(MainFrame.clip.getMicrosecondLength()));
                        mainFrame.controls_panel.playPause.setText("⏸️");
                        mainFrame.musicLabel.setText(MainFrame.path.getName());
                    }
                    
                }
            }
        }
        
        catch(UnsupportedAudioFileException eventt){
            JOptionPane.showMessageDialog(null, "    This file is not supported!\n Only .WAV .AU .AIFF file formats are supprted!", "Unsupported Audio File", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Unknown exception is causing the system to disfunction!", "Unknown error", JOptionPane.INFORMATION_MESSAGE);
        }
        finally{
            //Nothing to include here for now
        }
    }
}
