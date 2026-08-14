package com.sakalti.tconx.material.hachilite;

import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;

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
    public static final MaterialId MARULITE_ID = new MaterialId("tconx:malurite");

    // Material インスタンス
    public static Material HACHILITE;
    public static Material OUSWARI;
    public static Material HIROSWARI;
    public static Material PROXIA;
    public static Material CHIRITE;
    public static Material KANILITE;
    public static Material MOMONGAITE;
    public static Material HERDYEEN;
    public static Material OSTLUM;
    public static Material IGNIZ;
    public static Material IOXIUM;
    public static Material DILONITE;
    public static Material TIBERITE;
    public static Material CHIISTEEL;
    public static Material AUROSTONE;
    public static Material MARULITE;

    public static void registerMaterials() {
        MaterialManager materialManager = MaterialManager.getInstance();

        HACHILITE = materialManager.register(
            new Material(HACHILITE_ID, Material.DisplayName.withTranslationKey("material.tconx.hachilite"))
                .setCraftable(true)
                .setCastable(true)
        );

        OUSWARI = materialManager.register(
            new Material(OUSWARI_ID, Material.DisplayName.withTranslationKey("material.tconx.ouswari"))
                .setCraftable(true)
                .setCastable(true)
        );

        HIROSWARI = materialManager.register(
            new Material(HIROSWARI_ID, Material.DisplayName.withTranslationKey("material.tconx.hiroswari"))
                .setCraftable(true)
                .setCastable(true)
        );

        PROXIA = materialManager.register(
            new Material(PROXIA_ID, Material.DisplayName.withTranslationKey("material.tconx.proxia"))
                .setCraftable(true)
                .setCastable(true)
        );

        CHIRITE = materialManager.register(
            new Material(CHIRITE_ID, Material.DisplayName.withTranslationKey("material.tconx.chirite"))
                .setCraftable(true)
                .setCastable(true)
        );

        KANILITE = materialManager.register(
            new Material(KANILITE_ID, Material.DisplayName.withTranslationKey("material.tconx.kanilite"))
                .setCraftable(true)
                .setCastable(true)
        );

        MOMONGAITE = materialManager.register(
            new Material(MOMONGAITE_ID, Material.DisplayName.withTranslationKey("material.tconx.momongaite"))
                .setCraftable(true)
                .setCastable(true)
        );

        HERDYEEN = materialManager.register(
            new Material(HERDYEEN_ID, Material.DisplayName.withTranslationKey("material.tconx.herdyeen"))
                .setCraftable(true)
                .setCastable(true)
        );

        OSTLUM = materialManager.register(
            new Material(OSTLUM_ID, Material.DisplayName.withTranslationKey("material.tconx.ostlum"))
                .setCraftable(true)
                .setCastable(true)
        );

        IGNIZ = materialManager.register(
            new Material(IGNIZ_ID, Material.DisplayName.withTranslationKey("material.tconx.igniz"))
                .setCraftable(true)
                .setCastable(true)
        );

        IOXIUM = materialManager.register(
            new Material(IOXIUM_ID, Material.DisplayName.withTranslationKey("material.tconx.ioxium"))
                .setCraftable(true)
                .setCastable(true)
        );

        DILONITE = materialManager.register(
            new Material(DILONITE_ID, Material.DisplayName.withTranslationKey("material.tconx.dilonite"))
                .setCraftable(true)
                .setCastable(true)
        );

        TIBERITE = materialManager.register(
            new Material(TIBERITE_ID, Material.DisplayName.withTranslationKey("material.tconx.tiberite"))
                .setCraftable(true)
                .setCastable(true)
        );

        CHIISTEEL = materialManager.register(
            new Material(CHIISTEEL_ID, Material.DisplayName.withTranslationKey("material.tconx.chiisteel"))
                .setCraftable(true)
                .setCastable(true)
        );

        AUROSTONE = materialManager.register(
            new Material(AUROSTONE_ID, Material.DisplayName.withTranslationKey("material.tconx.aurostone"))
                .setCraftable(true)
                .setCastable(true)
        );

        MARULITE = materialManager.register(
            new Material(MARULITE_ID, Material.DisplayName.withTranslationKey("material.tconx.malurite"))
                .setCraftable(true)
                .setCastable(true)
        );
    }
}
