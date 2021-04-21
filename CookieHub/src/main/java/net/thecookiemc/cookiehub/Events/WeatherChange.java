package net.thecookiemc.cookiehub.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener {

  @EventHandler
  public void OnThunder(ThunderChangeEvent e) {
    if (e.toThunderState()) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void OnChange(WeatherChangeEvent e) {
    if (e.toWeatherState()) {
      e.setCancelled(true);
    }
  }
}
