package me.flash1110.mobcatcher;

import org.apache.commons.lang.WordUtils;


public enum Rank
{
  DEFAULT(0),  SOLDIER(1),  KNIGHT(2),  GENERAL(3),  KING(4),  EMPEROR(5),  END(6);
  
  Integer PermissionLevel;
  Integer DonorPoints;
  
  private Rank(int perm)
  {
    this.PermissionLevel = Integer.valueOf(perm);
  }
  
  public String getName()
  {
    return WordUtils.capitalizeFully(name().toLowerCase().replace("_", " "));
  }
  
  public Integer getPermissionLevel()
  {
    return this.PermissionLevel;
  }
}
