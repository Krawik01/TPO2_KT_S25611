/**
 *
 *  @author Krawiecki Tymoteusz S25611
 *
 */

package zad1;


import javax.swing.*;
import java.net.MalformedURLException;

public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();

      SwingUtilities.invokeLater(() -> {
          new Wiki(s);
      });
  }
}
