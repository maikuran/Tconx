package com.sakalti.modifier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

/** Persistent kill/progression data retained from the 1.16.5 Soul Eater implementation. */
public class SoulEaterData {
  public int killcount; public float health; public float amount; public int brokenblocks;
  public float bonus; public int curse; public String name = ""; public CompoundTag nbt = new CompoundTag();
  public float radius; public float dfloat; public int dint; public boolean active;
  private static ResourceLocation key(String path) { return new ResourceLocation("sakalti", path); }
  public static SoulEaterData read(CompoundTag tag) {
    SoulEaterData d = new SoulEaterData(); d.killcount=tag.getInt("killcount"); d.brokenblocks=tag.getInt("brokenblocks");
    d.health=tag.getFloat("health"); d.amount=tag.getFloat("amount"); d.bonus=tag.getFloat("bonus"); d.curse=tag.getInt("curse");
    d.name=tag.getString("name"); d.nbt=tag.getCompound("nbt").copy(); d.radius=tag.getFloat("radius"); d.dfloat=tag.getFloat("dfloat"); d.dint=tag.getInt("dint"); d.active=tag.getBoolean("active"); return d;
  }
  public static SoulEaterData read(ModDataNBT tag) {
    SoulEaterData d = new SoulEaterData(); d.killcount=tag.getInt(key("killcount")); d.brokenblocks=tag.getInt(key("brokenblocks"));
    d.health=tag.getFloat(key("health")); d.amount=tag.getFloat(key("amount")); d.bonus=tag.getFloat(key("bonus")); d.curse=tag.getInt(key("curse"));
    d.name=tag.getString(key("name")); d.nbt=tag.getCompound(key("nbt")); d.radius=tag.getFloat(key("radius")); d.dfloat=tag.getFloat(key("dfloat")); d.dint=tag.getInt(key("dint")); d.active=tag.getBoolean(key("active")); return d;
  }
  public void write(CompoundTag tag) {
    tag.putInt("killcount",killcount); tag.putInt("brokenblocks",brokenblocks); tag.putFloat("health",health); tag.putFloat("amount",amount);
    tag.putFloat("bonus",bonus); tag.putInt("curse",curse); tag.putString("name",name); tag.put("nbt",nbt.copy());
    tag.putFloat("radius",radius); tag.putFloat("dfloat",dfloat); tag.putInt("dint",dint); tag.putBoolean("active",active);
  }
  public void write(ModDataNBT tag) {
    tag.putInt(key("killcount"),killcount); tag.putInt(key("brokenblocks"),brokenblocks); tag.putFloat(key("health"),health); tag.putFloat(key("amount"),amount);
    tag.putFloat(key("bonus"),bonus); tag.putInt(key("curse"),curse); tag.putString(key("name"),name); tag.put(key("nbt"),nbt.copy());
    tag.putFloat(key("radius"),radius); tag.putFloat(key("dfloat"),dfloat); tag.putInt(key("dint"),dint); tag.putBoolean(key("active"),active);
  }
}
