package me.flash1110.mobcatcher;


import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MobCatcher
  extends JavaPlugin
{
  public void onEnable()
  {
    new CatchEvent(this);
  }
  
  public static Rank getRank(Player player)
  {
    if (player.hasPermission("flash.rank.end")) {
      return Rank.END;
    }
    if (player.hasPermission("flash.rank.emperor")) {
      return Rank.EMPEROR;
    }
    if (player.hasPermission("flash.rank.king")) {
      return Rank.KING;
    }
    if (player.hasPermission("flash.rank.general")) {
      return Rank.GENERAL;
    }
    if (player.hasPermission("flash.rank.knight")) {
      return Rank.KNIGHT;
    }
    if (player.hasPermission("flash.rank.soldier")) {
      return Rank.SOLDIER;
    }
    return Rank.DEFAULT;
  }
}

