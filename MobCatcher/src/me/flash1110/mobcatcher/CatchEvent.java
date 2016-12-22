package me.flash1110.mobcatcher;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginManager;

public class CatchEvent
  implements Listener
{
  public static MobCatcher plugin;
  
  public CatchEvent(MobCatcher instance)
  {
    plugin = instance;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  
  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event)
  {
    Entity DamagerE = event.getDamager();
    if (event.isCancelled()) {
      return;
    }
    if ((event.getDamager() instanceof Egg))
    {
      Egg egg = (Egg)event.getDamager();
      if ((egg.getShooter() != null) && ((egg.getShooter() instanceof Player)))
      {
        if (egg.getShooter().getWorld().getEnvironment().equals(World.Environment.THE_END))
        {
          Player no = (Player)egg.getShooter();
          no.sendMessage(ChatColor.RED + "Cannot use MobCatcher in the End");
          return;
        }
        event.getDamager().remove();
        
        egg.setMetadata("NoSpawny", new FixedMetadataValue(plugin, "NoSpawny"));
        
        Player shooter = (Player)egg.getShooter();
        
        Entity hit = event.getEntity();
        MobData mobdata = MobData.getMobData(event.getEntityType());
        if (mobdata == null)
        {
          shooter.sendMessage(ChatColor.RED + "That mob cannot be captured");
          return;
        }
        if ((hit instanceof Wolf))
        {
          Wolf wolf = (Wolf)hit;
          if (wolf.isTamed())
          {
            shooter.sendMessage(ChatColor.RED + "Tamed wolves can't be captured");
            return;
          }
        }
        if ((hit instanceof Ocelot))
        {
          Ocelot ocelot = (Ocelot)hit;
          if (ocelot.isTamed())
          {
            shooter.sendMessage(ChatColor.RED + "Tamed ocelots can't be captured");
            return;
          }
        }
        Material required = mobdata.getRequiredMaterial();
        int amount = mobdata.getRequiredAmount().intValue();
        int has = 0;
        if (amount > 0)
        {
          ItemStack[] contents = shooter.getInventory().getContents();
          ItemStack[] arrayOfItemStack1;
          int j = (arrayOfItemStack1 = contents).length;
          for (int i = 0; i < j; i++)
          {
            ItemStack item = arrayOfItemStack1[i];
            if (item != null)
            {
              if ((item.getType() == required) && (item.getDurability() == 0)) {
                has += item.getAmount();
              }
              if (has >= amount) {
                break;
              }
            }
          }
        }
        if (amount > has)
        {
          shooter.sendMessage(ChatColor.RED + "You require " + ChatColor.LIGHT_PURPLE + amount + " " + ChatColor.LIGHT_PURPLE + mobdata.getItemName() + ChatColor.RED + " to capture a " + ChatColor.AQUA + mobdata.getName());
          return;
        }
        int lefttoremove = amount;
        for (int i = 0; i < 36; i++)
        {
          ItemStack item = shooter.getInventory().getItem(i);
          if (item != null)
          {
            if (lefttoremove <= 0) {
              break;
            }
            if ((item.getType() == required) && (item.getDurability() == 0))
            {
              int itemamount = item.getAmount();
              if (lefttoremove == 1)
              {
                if (itemamount == 1)
                {
                  shooter.getInventory().setItem(i, new ItemStack(Material.AIR));
                  break;
                }
                shooter.getInventory().getItem(i).setAmount(itemamount - 1);
                break;
              }
              if (lefttoremove >= itemamount)
              {
                shooter.getInventory().setItem(i, new ItemStack(Material.AIR));
                lefttoremove -= itemamount;
              }
              else
              {
                shooter.getInventory().getItem(i).setAmount(itemamount - lefttoremove);
                break;
              }
            }
          }
        }
        shooter.sendMessage(ChatColor.YELLOW + "You used " + ChatColor.LIGHT_PURPLE + amount + " " + ChatColor.LIGHT_PURPLE + mobdata.getItemName() + ChatColor.YELLOW + " to try and capture the " + ChatColor.AQUA + mobdata.getName());
        if (!mobdata.performChance(mobdata.getChance(MobCatcher.getRank(shooter), Boolean.valueOf(shooter.hasPermission("0txplugin.mobcapture.extra"))).intValue()).booleanValue())
        {
          shooter.sendMessage(ChatColor.RED + "You failed to capture the " + ChatColor.AQUA + mobdata.getName());
          return;
        }
        shooter.sendMessage(ChatColor.YELLOW + "You captured the " + ChatColor.AQUA + mobdata.getName());
        
        event.setCancelled(true);
        event.getEntity().remove();
        
        ItemStack eggdrop = new ItemStack(383);
        eggdrop.setDurability(mobdata.getEggByte().byteValue());
        
        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), eggdrop);
      }
    }
  }
}

