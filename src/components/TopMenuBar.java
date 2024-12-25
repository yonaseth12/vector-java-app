package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import components.menubar_frames.*;
import utils.EventListener;


public class TopMenuBar extends JMenuBar{
    public MainFrame mainFrame;
    public JMenuItem OpenfileItem, OpenplaylistItem, ExitItem, volumeUpItem, volumeDownItem, MuteItem, Restoredown, FullscreenItem, MinimizeItem, about, helpItem;
    public JMenu filemenu, view, Audio, controlpanel, help;

    public TopMenuBar(MainFrame mainFrame) {

        JMenu filemenu = new JMenu("File");
        JMenu view = new JMenu("View");
        JMenu Audio = new JMenu("Audio");
        JMenu controlpanel = new JMenu("Control Panel");
        JMenu help = new JMenu("Help");
        add(filemenu);
        add(view);
        add(Audio);
        add(controlpanel);
        add(help);

        OpenfileItem = new JMenuItem("📁 Open file");
        OpenplaylistItem = new JMenuItem("♬ Open playlist");
        ExitItem = new JMenuItem("❌ Exit");
        ExitItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        filemenu.add(OpenfileItem);
        filemenu.add(OpenplaylistItem);
        filemenu.add(ExitItem);

        Restoredown = new JMenuItem("Restore down");
        FullscreenItem = new JMenuItem("Full screen");
        MinimizeItem = new JMenuItem("Minimize");
        Restoredown.addActionListener((ActionEvent event) -> {
            mainFrame.setExtendedState(JFrame.NORMAL);
            mainFrame.controls_panel.timelineSlider.setPreferredSize(new Dimension(mainFrame.getWidth() - 190, 18));
            });
        FullscreenItem.addActionListener((ActionEvent event) -> {
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainFrame.controls_panel.timelineSlider.setPreferredSize(new Dimension(mainFrame.getWidth() - 190, 18));
            });
        MinimizeItem.addActionListener((ActionEvent event) -> {
            mainFrame.setState(JFrame.ICONIFIED);
            });
        view.add(Restoredown);
        view.add(FullscreenItem);
        view.add(MinimizeItem);
        
        volumeUpItem = new JMenuItem("🔊 Volume up");
        volumeDownItem= new JMenuItem("🔉 Volume down");
        MuteItem= new JMenuItem("🔇 Mute/Unmute");
        Audio.add(volumeUpItem);
        Audio.add(volumeDownItem);
        Audio.add(MuteItem);

        helpItem = new JMenuItem("❔ Help");
        about = new JMenuItem("ⓘ  About");
        help.add(helpItem);
        help.add(about);

        // About Frame
        about.addActionListener((ActionEvent event)->{

            JFrame aboutFrame = new AboutFrame(mainFrame); 
            aboutFrame.setVisible(true);

        });

        // Help Frame
        helpItem.addActionListener((ActionEvent event)->{

            JFrame helpFrame= new HelpFrame(mainFrame);
            helpFrame.setVisible(true);

        });



        // Adding Event Listener
        MuteItem.addActionListener(new EventListener(mainFrame));
        volumeDownItem .addActionListener(new EventListener(mainFrame));
        volumeUpItem.addActionListener(new EventListener(mainFrame));
        OpenfileItem.addActionListener(new EventListener(mainFrame));
        OpenplaylistItem .addActionListener(new EventListener(mainFrame));

    }
    
}
