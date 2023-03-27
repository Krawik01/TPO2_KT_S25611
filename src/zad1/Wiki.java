package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Wiki extends JFrame{

    private JFXPanel jfxPanel;
    private String city;

    public Wiki(Service service) {
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(mainPanel);
        city = null;
        city = service.getCity();
        jfxPanel = new JFXPanel();
        Platform.runLater(this::createJFXContent);
        webPanel.add(jfxPanel);
        this.setSize(1240, 850);
        Weather.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(service.getWeather(service.getCity()));
                resultLabel.setText(service.getWeather(service.getCity()));
            }
        });
        showRateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(service.getRateFor(service.getEx()));
                resultLabel.setText(service.getRateFor(service.getEx()).toString());
            }
        });


        showNBPRateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(service.getNBPRate());
                resultLabel.setText(service.getNBPRate().toString());
            }
        });

        scrollBar1.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                resultLabel.getHorizontalTextPosition();
            }
        });
    }

    private void createJFXContent() {
        WebView webView = new WebView();
        webView.getEngine().load("https://en.wikipedia.org/wiki/" + city);
        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
    }

    private JButton Weather;
    private JButton showRateButton;
    private JPanel mainPanel;
    private JButton showNBPRateButton;
    private JLabel resultLabel;
    private JScrollBar scrollBar1;
    private JPanel webPanel;
}
