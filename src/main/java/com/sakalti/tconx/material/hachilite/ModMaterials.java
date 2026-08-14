package com.sakalti.tconx.material.hachilite;

import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.MaterialRegistry;

public class ModMaterials {

    public static final MaterialId HACHILITE_ID = new MaterialId("tconx:hachilite");
    public static final MaterialId OUSWARI_ID = new MaterialId("tconx:ouswari");
    public static final MaterialId HIROSWARI_ID = new MaterialId("tconx:hiroswari");
    public static final MaterialId PROXIA_ID = new MaterialId("tconx:proxia");
    public static final MaterialId CHIRITE_ID = new MaterialId("tconx:chirite");
    public static final MaterialId KANILITE_ID = new MaterialId("tconx:kanilite");
    public static final MaterialId MOMONGAITE_ID = new MaterialId("tconx:momongaite");
    public static final MaterialId HERDYEEN_ID = new MaterialId("tconx:herdyeen");
    public static final MaterialId OSTLUM_ID = new MaterialId("tconx:ostlum");
    public static final MaterialId IGNIZ_ID = new MaterialId("tconx:igniz");
    public static final MaterialId IOXIUM_ID = new MaterialId("tconx:ioxium");
    public static final MaterialId DILONITE_ID = new MaterialId("tconx:dilonite");
    public static final MaterialId TIBERITE_ID = new MaterialId("tconx:tiberite");
    public static final MaterialId CHIISTEEL_ID = new MaterialId("tconx:chiisteel");
    public static final MaterialId AUROSTONE_ID = new MaterialId("tconx:aurostone");
    public static final MaterialId MARULITE_ID = new MaterialId("tconx:marulite");

    /**
     * Materials are loaded from JSON data files at:
     * resources/data/tconx/tconstruct/materials/
     * 
     * This method can be called to access materials at runtime
     */
    public static void registerMaterials() {
        // In Tinkers' Construct 3.x, materials are registered via JSON data files
        // Use MaterialRegistry.getMaterial(MaterialId) to access them at runtime
        
        var hachilite = MaterialRegistry.getMaterial(HACHILITE_ID);
        var ouswari = MaterialRegistry.getMaterial(OUSWARI_ID);
        var hiroswari = MaterialRegistry.getMaterial(HIROSWARI_ID);
        var proxia = MaterialRegistry.getMaterial(PROXIA_ID);
        var chirite = MaterialRegistry.getMaterial(CHIRITE_ID);
        var kanilite = MaterialRegistry.getMaterial(KANILITE_ID);
        var momongaite = MaterialRegistry.getMaterial(MOMONGAITE_ID);
        var herdyeen = MaterialRegistry.getMaterial(HERDYEEN_ID);
        var ostlum = MaterialRegistry.getMaterial(OSTLUM_ID);
        var igniz = MaterialRegistry.getMaterial(IGNIZ_ID);
        var ioxium = MaterialRegistry.getMaterial(IOXIUM_ID);
        var dilonite = MaterialRegistry.getMaterial(DILONITE_ID);
        var tiberite = MaterialRegistry.getMaterial(TIBERITE_ID);
        var chiisteel = MaterialRegistry.getMaterial(CHIISTEEL_ID);
        var aurostone = MaterialRegistry.getMaterial(AUROSTONE_ID);
        var marulite = MaterialRegistry.getMaterial(MARULITE_ID);
    }
}
