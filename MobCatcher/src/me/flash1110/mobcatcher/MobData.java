package me.flash1110.mobcatcher;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum MobData
{
  BAT("Bat", "Sulphur", 5, 25, 45, 75, 90, 100, 100, Material.SULPHUR, 4, (byte)65),  
  BLAZE("Blaze", "Nether Warts", 0, 10, 20, 30, 40, 50, 60, Material.NETHER_STALK, 8, (byte)61),  
  CAVE_SPIDER("Cave Spider", "String", 0, 25, 50, 60, 70, 80, 90, Material.STRING, 16, (byte)59),  
  CHICKEN("Chicken", "Feathers", 10, 50, 75, 90, 100, 100, 100, Material.FEATHER, 4, (byte)93), 
  COW("Cow", "Potatos", 5, 10, 50, 75, 100, 100, 100, Material.POTATO_ITEM, 8, (byte)92),  
  CREEPER("Creeper", "Apples", 0, 5, 7, 10, 12, 15, 20, Material.APPLE, 3, (byte)50),  
  GHAST("Ghast", "Blaze Rods", 0, 5, 10, 15, 20, 25, 30, Material.BLAZE_ROD, 6, (byte)56),  
  HORSE("Horse", "Hay Bale", 0, 0, 5, 10, 15, 20, 25, Material.HAY_BLOCK, 3, (byte)100),  
  IRON_GOLEM("Iron Golem", "Red Flowers", 0, 10, 25, 40, 60, 70, 80, Material.RED_ROSE, 32, (byte)99),  
  MAGMA_CUBE("Magma Cube", "Glowstone Dust", 0, 20, 30, 40, 45, 50, 55, Material.GLOWSTONE_DUST, 16, (byte)62), 
  MUSHROOM_COW("Mushroom Cow", "Mushroom Soup", 0, 50, 60, 70, 80, 90, 100, Material.MUSHROOM_SOUP, 2, (byte)96), 
  OCELOT("Ocelot", "Raw Chicken", 0, 10, 20, 30, 40, 50, 60, Material.RAW_CHICKEN, 4, (byte)98), 
  PIG("Pig", "Carrots", 5, 50, 75, 90, 100, 100, 100, Material.CARROT_ITEM, 6, (byte)90),  
  PIG_ZOMBIE("Pig Zombie", "Gold Ingots", 0, 30, 50, 65, 75, 85, 90, Material.GOLD_INGOT, 8, (byte)57), 
  SHEEP("Sheep", "Seeds", 5, 50, 75, 90, 100, 100, 100, Material.SEEDS, 8, (byte)91),  
  SKELETON("Skeleton", "Bones", 0, 10, 20, 30, 40, 50, 60, Material.BONE, 8, (byte)51),  
  SLIME("Slime", "Slime Balls", 0, 10, 15, 25, 35, 45, 50, Material.SLIME_BALL, 8, (byte)55), 
  SPIDER("Spider", "String", 0, 25, 50, 60, 70, 80, 90, Material.STRING, 16, (byte)52), 
  SQUID("Squid", "Raw Fish", 0, 50, 75, 90, 100, 100, 100, Material.RAW_FISH, 4, (byte)94),  
  WITCH("Witch", "Ghast Tears", 0, 10, 15, 20, 25, 30, 35, Material.GHAST_TEAR, 4, (byte)66), 
  WOLF("Wolf", "Raw Beef", 0, 10, 15, 20, 30, 40, 50, Material.RAW_BEEF, 4, (byte)95), 
  ZOMBIE("Zombie", "Rotten Flesh", 0, 10, 20, 30, 40, 50, 60, Material.ROTTEN_FLESH, 16, (byte)54);
  
  String Name;
  String ItemName;
  Integer[] Chances = new Integer[7];
  Integer ItemUseAmount;
  Material ItemUseType;
  Byte EggByte;
  
  private MobData(String name, String itemname, int reg, int soldier, int knight, int general, int king, int emp, int end, Material item, int amount, byte subid)
  {
    this.Name = name;
    this.ItemName = itemname;
    
    this.Chances[0] = Integer.valueOf(reg);
    this.Chances[1] = Integer.valueOf(soldier);
    this.Chances[2] = Integer.valueOf(knight);
    this.Chances[3] = Integer.valueOf(general);
    this.Chances[4] = Integer.valueOf(king);
    this.Chances[5] = Integer.valueOf(emp);
    this.Chances[6] = Integer.valueOf(end);
    
    this.ItemUseAmount = Integer.valueOf(amount);
    this.ItemUseType = item;
    
    this.EggByte = Byte.valueOf(subid);
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public String getItemName()
  {
    return this.ItemName;
  }
  
  public Integer getChance(Rank rank, Boolean extra)
  {
    Integer chance = Integer.valueOf(this.Chances[rank.getPermissionLevel().intValue()].intValue() + (extra.booleanValue() ? 10 : 0));
    if (chance.intValue() > 100) {
      chance = Integer.valueOf(100);
    }
    return chance;
  }
  
  public Material getRequiredMaterial()
  {
    return this.ItemUseType;
  }
  
  public Integer getRequiredAmount()
  {
    return this.ItemUseAmount;
  }
  
  public Byte getEggByte()
  {
    return this.EggByte;
  }
  
  public Boolean performChance(int chance)
  {
    Random r = new Random();
    int Low = 1;
    int High = 101;
    int R = r.nextInt(High - Low) + Low;
    if (R <= chance) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  public static MobData getMobData(EntityType et)
  {
    MobData mobdata = null;
    try
    {
      mobdata = valueOf(et.name().toUpperCase());
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return mobdata;
  }
}

