package com.redstoneguy10ls.lithicaddon.common.items;

import com.redstoneguy10ls.lithicaddon.LithicAddon;
import com.redstoneguy10ls.lithicaddon.common.blocks.lithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.blocks.rockBlocks;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.util.SelfTests;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class lithicTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final lithicTab.CreativeTabHolder LITHIC =
            register("lithic", () -> new ItemStack(lithicItems.LITHIC.get()), lithicTab::fillTab);

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {

        //accept(out, lithicItems.LITHIC);
        lithicItems.METAL_SPINDLES.values().forEach(reg -> accept(out, reg));
        lithicItems.SPINDLE_HEADS.values().forEach(reg -> accept(out, reg));

        accept(out, lithicItems.ALUMINUM_LID);
        accept(out, lithicItems.STAINLESS_STEEL_LID);
        accept(out, lithicItems.EMPTY_JAR_WITH_ALUMINUM_LID);
        accept(out, lithicItems.EMPTY_JAR_WITH_STAINLESS_STEEL_LID);

        for(Food food : Food.values())
        {
            accept(out, lithicItems.FRUIT_PRESERVES_ALUMINUM, food);
            accept(out, lithicItems.FRUIT_PRESERVES_STAINLESS_STEEL, food);

        }
        lithicItems.METAL_FLUIDS_BUCKETS.values().forEach(reg -> accept(out, reg));
        lithicItems.ACID_FLUIDS_BUCKETS.values().forEach(reg -> accept(out, reg));
        lithicItems.GLASS_FLUIDS_BUCKETS.values().forEach(reg -> accept(out, reg));



        for(Rock rock : Rock.VALUES)
        {
            accept(out, lithicBlocks.ROCKS_PILLARS, rock);
            for (rockBlocks type : rockBlocks.VALUES)
            {
                accept(out, lithicBlocks.ROCKS_BLOCKS, rock, type);
            }
        }

    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            SelfTests.reportExternalError();
            return;
        }
        out.accept(reg.get());
    }
    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }
    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static lithicTab.CreativeTabHolder register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("lithicaddon.creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new lithicTab.CreativeTabHolder(reg, displayItems);
    }

    private static <T> void consumeOurs(IForgeRegistry<T> registry, Consumer<T> consumer)
    {
        for (T value : registry)
        {
            if (Objects.requireNonNull(registry.getKey(value)).getNamespace().equals(LithicAddon.MOD_ID))
            {
                consumer.accept(value);
            }
        }
    }
    public record CreativeTabHolder(RegistryObject<CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {}
}
