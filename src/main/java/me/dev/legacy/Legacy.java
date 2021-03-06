package me.dev.legacy;

import me.dev.legacy.api.event.events.render.Render3DEvent;
import me.dev.legacy.api.manager.*;
import me.dev.legacy.api.util.Enemy;
import me.dev.legacy.api.util.IconUtil;
import me.dev.legacy.api.util.Title;
import me.dev.legacy.impl.gui.font.CustomFont;
import me.dev.legacy.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.Util.EnumOS;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.io.InputStream;
import java.nio.ByteBuffer;

@Mod(
        modid = "legacy",
        name = "legacy",
        version = "1.2.5"
)
public class Legacy {
    public static final String MODID = "legacy";
    public static final String MODNAME = "Legacy";
    public static final String MODVER = "v1.2.5";
    public static final Logger LOGGER = LogManager.getLogger("legacy");
    public static TimerManager timerManager;
    public static TotemPopManager totemPopManager;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static PacketManager packetManager;
    public static ColorManager colorManager;
    public static HoleManager holeManager;
    public static InventoryManager inventoryManager;
    public static PotionManager potionManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static SpeedManager speedManager;
    public static ReloadManager reloadManager;
    public static FileManager fileManager;
    public static ConfigManager configManager;
    public static ServerManager serverManager;
    public static EventManager eventManager;
    public static TextManager textManager;
    public static CustomFont fontRenderer;
    public static Render3DEvent render3DEvent;
    public static Enemy enemy;
    public static me.zero.alpine.EventManager eventManager1;
    @Instance
    public static Legacy INSTANCE;
    private static boolean unloaded = false;

    public static me.zero.alpine.EventManager getEventManager() {
        if (eventManager1 == null) {
            eventManager1 = new me.zero.alpine.EventManager();
        }

        return eventManager1;
    }

    public static void load() {
        LOGGER.info("loading legacy");
        unloaded = false;
        if (reloadManager != null) {
            reloadManager.unload();
            reloadManager = null;
        }

        textManager = new TextManager();
        commandManager = new CommandManager();
        friendManager = new FriendManager();
        moduleManager = new ModuleManager();
        rotationManager = new RotationManager();
        packetManager = new PacketManager();
        eventManager = new EventManager();
        speedManager = new SpeedManager();
        potionManager = new PotionManager();
        inventoryManager = new InventoryManager();
        serverManager = new ServerManager();
        fileManager = new FileManager();
        colorManager = new ColorManager();
        positionManager = new PositionManager();
        configManager = new ConfigManager();
        holeManager = new HoleManager();
        LOGGER.info("Managers loaded.");
        moduleManager.init();
        LOGGER.info("Modules loaded.");
        configManager.init();
        eventManager.init();
        LOGGER.info("EventManager loaded.");
        textManager.init(true);
        moduleManager.onLoad();
        LOGGER.info("legacy successfully loaded!\n");
    }

    public static void unload(boolean unload) {
        LOGGER.info("unloading legacy");
        if (unload) {
            reloadManager = new ReloadManager();
            reloadManager.init(commandManager != null ? commandManager.getPrefix() : ".");
        }

        onUnload();
        eventManager = null;
        friendManager = null;
        speedManager = null;
        holeManager = null;
        positionManager = null;
        rotationManager = null;
        configManager = null;
        commandManager = null;
        colorManager = null;
        serverManager = null;
        fileManager = null;
        potionManager = null;
        inventoryManager = null;
        moduleManager = null;
        textManager = null;
        LOGGER.info("legacy unloaded!\n");
    }

    public static void reload() {
        unload(false);
        load();
    }

    public static void onUnload() {
        if (!unloaded) {
            eventManager.onUnload();
            moduleManager.onUnload();
            configManager.saveConfig(configManager.config.replaceFirst("legacy/", ""));
            moduleManager.onUnloadPost();
            unloaded = true;
        }

    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Just Cracked By NightDesert");
    }

    public static void setWindowIcon() {
        if (Util.func_110647_a() != EnumOS.OSX) {
            try {
                InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/legacy/icons/legacy-16x.png");
                Throwable var1 = null;

                try {
                    InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/legacy/icons/legacy-32x.png");
                    Throwable var3 = null;

                    try {
                        ByteBuffer[] icons = new ByteBuffer[]{IconUtil.INSTANCE.readImageToBuffer(inputStream16x), IconUtil.INSTANCE.readImageToBuffer(inputStream32x)};
                        Display.setIcon(icons);
                    } catch (Throwable var28) {
                        var3 = var28;
                        throw var28;
                    } finally {
                        if (inputStream32x != null) {
                            if (var3 != null) {
                                try {
                                    inputStream32x.close();
                                } catch (Throwable var27) {
                                    var3.addSuppressed(var27);
                                }
                            } else {
                                inputStream32x.close();
                            }
                        }

                    }
                } catch (Throwable var30) {
                    var1 = var30;
                    throw var30;
                } finally {
                    if (inputStream16x != null) {
                        if (var1 != null) {
                            try {
                                inputStream16x.close();
                            } catch (Throwable var26) {
                                var1.addSuppressed(var26);
                            }
                        } else {
                            inputStream16x.close();
                        }
                    }

                }
            } catch (Exception var32) {
                LOGGER.error("Couldn't set Windows Icon", var32);
            }
        }

    }

    private void setWindowsIcon() {
        setWindowIcon();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Title());
        load();
        this.setWindowsIcon();
    }
}
