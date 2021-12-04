package co.yunchao.client.views;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.awt.Font.createFont;

public class Home extends GameApplication {
    Image bgImg = null;
    Image cardImg = null;
    Image logo = null;
    Font gradFont;
    JFrame homeFr;
    JPanel container;

    Home() {
        try {
            URL logoUrl = new URL("https://cdn.discordapp.com/attachments/915957941365010473/916392787388289024/Asset_1.png");
            this.cardImg = ImageIO.read(logoUrl);
            this.logo = cardImg.getScaledInstance(412,426, Image.SCALE_AREA_AVERAGING);

            URL bgUrl = new URL("https://cdn.discordapp.com/attachments/915957941365010473/916694669449105428/background.png");
            this.bgImg = ImageIO.read(bgUrl);

            gradFont = createFont(Font.TRUETYPE_FONT,
                    new File("src/main/java/co/yunchao/base/fonts/Graduate.ttf")).deriveFont(25f);

            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(createFont(Font.TRUETYPE_FONT,
                    new File("src/main/java/co/yunchao/base/fonts/Graduate.ttf")));
        }
        catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        homeFr = new JFrame();
        /*homeFr.setContentPane(new JLabel(new ImageIcon(bgImg.getScaledInstance(1280,720,Image.SCALE_SMOOTH))));*/
        container = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel();
        JLabel homeLogo = new JLabel(new ImageIcon(logo));
        JButton playBtn = new JButton("<html><font color='#08203c'>PLAY</font></html>");
        JButton settingBtn = new JButton("OPTION");
        JButton quitBtn = new JButton("QUIT");

        JPanel btnWindow = new JPanel(new GridLayout(3,1));
        JPanel confPanel = new JPanel();
        JPanel btnCombine = new JPanel(new BorderLayout());
        JButton low = new JButton("Small");
        JButton medium = new JButton("Medium");
        JButton high = new JButton("Large");

        playBtn.setFont(gradFont);
        settingBtn.setFont(gradFont);
        quitBtn.setFont(gradFont);

        btnWindow.setBackground( Color.WHITE );
        confPanel.setBackground( Color.WHITE );
        btnCombine.setBackground( Color.WHITE );

        low.setBackground( Color.WHITE );
        medium.setBackground( Color.WHITE );
        high.setBackground( Color.WHITE );

        playBtn.setBackground( Color.decode("#f7c216") );
        playBtn.setPreferredSize(new Dimension(200, 50));
        playBtn.setMaximumSize(new Dimension(200,50));

        settingBtn.setBackground( Color.WHITE );
        settingBtn.setPreferredSize(new Dimension(200,50));
        settingBtn.setMaximumSize(new Dimension(200, 50));

        quitBtn.setBackground( Color.WHITE );
        quitBtn.setPreferredSize(new Dimension(200,50));
        quitBtn.setMaximumSize(new Dimension(200, 50));

        btnWindow.add(low);
        btnWindow.add(medium);
        btnWindow.add(high);

        btnPanel.add(playBtn);
        btnPanel.add(settingBtn);
        btnPanel.add(quitBtn);
        btnPanel.add(btnWindow);

        low.addActionListener(this::setLow);
        medium.addActionListener(this::setMed);
        high.addActionListener(this::setHigh);
        quitBtn.addActionListener(this::setExit);
        container.add(homeLogo, BorderLayout.CENTER);
        container.add(btnPanel, BorderLayout.SOUTH);

        btnPanel.setBackground( Color.decode("#08203c") );
        container.setBackground( Color.decode("#08203c") );
        homeFr.setBackground( Color.decode("#08203c") );
        homeFr.add(container);
        homeFr.setPreferredSize(new Dimension(1280,720));
        homeFr.setBounds(100,100,1280,720);
        homeFr.setLocationRelativeTo(null);
        homeFr.setVisible(true);
        homeFr.pack();
        homeFr.setResizable(false);
        homeFr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE) ;
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    public static void main(String[] args) {
        new Home();
    }

    private void setExit(ActionEvent e){
        System.exit(0);
    }
    private void setLow(ActionEvent e){
        homeFr.setBounds(100,100,640,480);
    }
    private void setMed(ActionEvent e){
        homeFr.setBounds(100,100,1280,780);
    }
    private void setHigh(ActionEvent e){
        homeFr.setBounds(100,100,1920,1080);
    }
}