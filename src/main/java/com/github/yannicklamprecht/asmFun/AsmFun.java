package com.github.yannicklamprecht.asmFun;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class AsmFun extends JavaPlugin {

    private final NamespacedKey key = new NamespacedKey(this, "test_sword");
    private final PersistentDataType<Byte, Boolean> type = PersistentDataType.BOOLEAN;

    @Override
    public void onEnable() {

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {

            event.registrar().register(Commands.literal("test")
                    .requires(source -> source.getExecutor() instanceof Player)
                    .executes(context -> {
                        if (context.getSource().getExecutor() instanceof Player player) {
                            var stack = ItemType.NETHERITE_SWORD.createItemStack();

                            stack.editMeta(meta -> {
                                PersistentDataContainer container = meta.getPersistentDataContainer();
                                container.set(key, type, true);
                            });

                            player.sendRichMessage("Is test sword with type:  " + stack.getPersistentDataContainer().has(key, type));
                            player.sendRichMessage("Is test sword no type:  " + stack.getPersistentDataContainer().has(key));
                            player.sendRichMessage("Is test sword get:  " + stack.getPersistentDataContainer().get(key, type));
                            player.sendRichMessage("All Keys: " + stack.getPersistentDataContainer().getKeys());
                            player.getInventory().addItem(stack);
                        }
                        return Command.SINGLE_SUCCESS;
                    })

                    .build());
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
