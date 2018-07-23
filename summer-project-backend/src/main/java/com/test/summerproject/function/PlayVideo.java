package com.test.summerproject.function;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.*;

import org.bytedeco.javacv.FrameGrabber.Exception;

public class PlayVideo {

    public static void run(String filename) {
        File txtfile = new File("G:\\SummerProject\\snapshot\\snapshot.txt");
        if (txtfile.exists()) {
            txtfile.delete();
        }

        String filePath = filename;
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("file is not exist");
            return;
        }
        PlayVideo app = new PlayVideo();
        app.play(filePath);
    }

    Semaphore semaphore = new Semaphore(0);
    final static int INTERVAL = 40;
    boolean videoIsPlaying = true;
    boolean videoIsOver = false;
    boolean videoIsStop = false;
    boolean videoIsExit = false;
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    ComponentEventFactory eventFactory = new ComponentEventFactory();

    final static Map<String, JButton> controllerButtons = new HashMap();
    final static Map<String, GridBagConstraints> gridBagConstraints = new HashMap();
    final static List<String> buttonNames = new ArrayList();
    FFmpegFrameGrabber ffmpegFrameGrabber;
    CanvasFrame desktopPlayer;
    JPanel playerController;
    JLabel timeClock;
    JSlider videoProcess;
    Frame nowFrame;
    SaveInformation saveInformation;

    int maxStamp;
    int fflength;
    int startStamp;
    double present;
    final long beginingRecordTime = 0;
    double speedRate = 1.0;

    static {
        buttonNames.add("play");
        buttonNames.add("stop");
        buttonNames.add("save");
        buttonNames.add("exit");


        controllerButtons.put("play", new JButton("play"));
        controllerButtons.put("stop", new JButton("stop"));
        controllerButtons.put("save", new JButton("save"));
        controllerButtons.put("exit", new JButton("exit"));


        GridBagConstraints player = new GridBagConstraints();
        player.insets.top = player.insets.bottom = 10;
        player.weightx = 0;
        player.insets.left = 5;
        gridBagConstraints.put("play", player);

        GridBagConstraints common = new GridBagConstraints();
        common.insets.top = common.insets.bottom = 10;
        common.weightx = 0;
        common.insets.left = 5;
        gridBagConstraints.put("stop", common);
        gridBagConstraints.put("save", common);
        gridBagConstraints.put("exit", common);


        GridBagConstraints speedRate = new GridBagConstraints();
        speedRate.insets.top = speedRate.insets.bottom = 10;
        speedRate.weightx = 0;
        speedRate.insets.left = 10;
        gridBagConstraints.put("speedRate", speedRate);

        GridBagConstraints timeSlider = new GridBagConstraints();
        timeSlider.insets.top = timeSlider.insets.bottom = 10;
        timeSlider.weightx = 1;
        timeSlider.insets.left = 20;
        timeSlider.gridx = GridBagConstraints.RELATIVE;
        timeSlider.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.put("timeSlider", timeSlider);

        GridBagConstraints timeLabel = new GridBagConstraints();
        timeLabel.insets.top = timeLabel.insets.bottom = 10;
        timeLabel.weightx = 0;
        timeLabel.insets.left = 10;
        timeLabel.insets.right = 5;
        timeLabel.gridx = GridBagConstraints.RELATIVE;

        gridBagConstraints.put("timeLabel", timeLabel);
    }

    public PlayVideo() {
        playerController = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        playerController.setLayout(gridbag);
        List<JButton> controllerButtions = new ArrayList<>();
        for (String buttonName : buttonNames) {
            JButton button = controllerButtons.get(buttonName);
            button.setToolTipText(buttonName);
            button.setPreferredSize(new Dimension(70, 22));
            eventFactory.addComponentEventListener(button, ComponentEventFactory.COMPONENT_BUTTON, this);
            controllerButtions.add(button);
            if (gridBagConstraints.containsKey(buttonName)) {
                playerController.add(button, gridBagConstraints.get(buttonName));
            }
        }


        videoProcess = new JSlider(0, 100, 0);
        videoProcess.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        playerController.add(videoProcess, gridBagConstraints.get("timeSlider"));
        eventFactory.addComponentEventListener(videoProcess, ComponentEventFactory.COMPONENT_SLIDER, this);

        timeClock = new JLabel(getTimeString((int) (beginingRecordTime / 1000)));
        timeClock.setForeground(Color.blue);
        playerController.add(timeClock, gridBagConstraints.get("timeLabel"));

        desktopPlayer = new CanvasFrame("Video Player", 1);
        desktopPlayer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        desktopPlayer.add(playerController, BorderLayout.SOUTH);
        desktopPlayer.setLocation(150, 150);


        saveInformation = new SaveInformation("Save Success", desktopPlayer);

        desktopPlayer.setVisible(true);

    }

    public void play(String filePath) {
        try {
            ffmpegFrameGrabber = FFmpegFrameGrabber.createDefault(filePath);
            ffmpegFrameGrabber.start();
            fflength = ffmpegFrameGrabber.getLengthInFrames();
            maxStamp = (int) (ffmpegFrameGrabber.getLengthInTime() / 1000000);
            int count = 0;
            while (!videoIsExit) {
                if (videoIsPlaying) {
                    nowFrame = ffmpegFrameGrabber.grabImage();
                    startStamp = (int) (ffmpegFrameGrabber.getTimestamp() * 1.0 / 1000000);
                    present = (startStamp * 1.0 / maxStamp) * 100;
                    if (nowFrame == null) {
                        System.out.println("!!! Failed cvQueryFrame");
                        controllerButtons.get("play").setText("play");
                        controllerButtons.get("play").setToolTipText("play");
                        videoIsOver = true;
                        continue;
                    }
                    timeClock.setText(getTimeString(startStamp));
                    videoProcess.setValue((int) present);
                    count++;
                    if (count % speedRate != 0) {
                        continue;
                    }
                    desktopPlayer.showImage(nowFrame);
                    Thread.sleep(INTERVAL);
                }
            }
            desktopPlayer.dispose();
            ffmpegFrameGrabber.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSliderEventListener(JSlider slider) {

        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                double x = e.getX() * 1.0 / videoProcess.getWidth();
                x *= maxStamp;
                startStamp = (int) (x + 0.5);
                try {
                    //该时间戳是以微妙计算的
                    ffmpegFrameGrabber.setTimestamp(startStamp * 1000000);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        slider.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double x = e.getX() * 1.0 / videoProcess.getWidth();
                x *= maxStamp;
                int time = (int) (x + 0.5);
                videoProcess.setToolTipText(getTimeString((int) (time + beginingRecordTime)));
            }
        });
    }

    public String getTimeString(int second) {
        return sdf.format(second * 1000);
    }
}

class ComponentEventFactory {

    final static int COMPONENT_BUTTON = 1;

    final static int COMPONENT_SLIDER = 2;

    PlayVideo containner;

    int savenumber = 0;


    public void addComponentEventListener(Component component, int componentType, PlayVideo containner) {
        this.containner = containner;
        if (containner == null) {
            return;
        }
        switch (componentType) {
            case COMPONENT_BUTTON:
                addButtonEventListener((JButton) component);
                break;
            case COMPONENT_SLIDER:
                addSliderEventListener((JSlider) component);
                break;
            default:
                break;
        }
    }

    private void addButtonEventListener(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                String buttonName = button.getText();
                if (buttonName.equals("play")) {
                    System.out.println("play");
                    try {
                        if (containner.videoIsOver) {
                            containner.ffmpegFrameGrabber.restart();
                            containner.videoIsOver = false;
                        }
                        button.setText("pause");
                        button.setToolTipText("pause");
                        if (containner.videoIsStop) {
                            containner.ffmpegFrameGrabber.start();
                        }
                        containner.videoIsPlaying = true;
                        containner.videoIsStop = false;
                        containner.semaphore.release();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                if (buttonName.equals("pause")) {
                    System.out.println("pause");
                    button.setText("play");
                    button.setToolTipText("play");
                    containner.videoIsPlaying = false;
                    containner.videoIsStop = false;
                }

                if (buttonName.equals("stop")) {
                    System.out.println("stop");
                    try {
                        containner.controllerButtons.get("play").setText("play");
                        containner.controllerButtons.get("play").setToolTipText("play");
                        containner.ffmpegFrameGrabber.stop();
                        containner.timeClock.setText(containner.getTimeString((int) (containner.beginingRecordTime / 1000)));
                        containner.videoProcess.setValue(0);
                        containner.videoIsPlaying = false;
                        containner.videoIsStop = true;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                if (buttonName.equals("save")) {
                    System.out.println("snapshot");
                    containner.saveInformation.setVisible(true);
                    snapshot();
                }

                if (buttonName.equals("exit")) {
                    System.out.println("exit");
                    containner.videoIsExit = true;
                }

            }
        });
    }

    private void addSliderEventListener(JSlider slider) {
        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                double x = e.getX() * 1.0 / containner.videoProcess.getWidth();
                x *= containner.maxStamp;
                containner.startStamp = (int) (x + 0.5);
                try {
                    containner.ffmpegFrameGrabber.setTimestamp(containner.startStamp * 1000000);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });

        slider.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double x = e.getX() * 1.0 / containner.videoProcess.getWidth();
                x *= containner.maxStamp;
                int time = (int) (x + 0.5);
                containner.videoProcess.setToolTipText(containner.getTimeString((int) (time + containner.beginingRecordTime)));
            }
        });
    }

    private void snapshot() {
        String filename = "G:\\SummerProject\\snapshot\\";
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

        //Java2DFrameConverter converter = new Java2DFrameConverter();
        //BufferedImage bufferedImage = converter.getBufferedImage(containner.nowFrame);
        opencv_core.Mat mat = converter.convertToMat(containner.nowFrame);
        if (mat != null) {
            //OutputStream os = null;
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter("G:\\SummerProject\\snapshot\\snapshot.txt", true);
                //os = new FileOutputStream(filename + savenumber + ".png");
                //ImageIO.write(bufferedImage, "png", os);
                fileWriter.write(filename + savenumber + ".png\r\n");
                opencv_imgcodecs.imwrite(filename + savenumber + ".png", mat);
                savenumber++;
                //os.flush();
                //os.close();
                fileWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}

class SaveInformation extends JFrame {
    JButton okButton;
    CanvasFrame desktopPlayer;

    public SaveInformation(String title, CanvasFrame desktopPlayer) {
        super(title);
        this.desktopPlayer = desktopPlayer;
        setAlwaysOnTop(true);

        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        gbc.insets = new Insets(5, 0, 1, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JLabel accountTitle = new JLabel(" Save Success ");
        add(accountTitle, gbc);

        gbc.insets = new Insets(4, 0, 10, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        okButton = new JButton("   OK   ");
        add(okButton, gbc);
        okButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("okbutton key");
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_I) {
                    close();
                }
            }
        });
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                String buttonName = button.getText();
                if (buttonName.equals("   OK   ")) {
                    close();
                }
            }
        });

        pack();
        setResizable(false);
        setAlwaysOnTop(true);
        okButton.requestFocus();
    }

    public void close() {
        setVisible(false);
    }
}
