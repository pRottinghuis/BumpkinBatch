package team.rusty.bumpkinbatch.registry;

import com.sun.org.apache.xerces.internal.dom.DeferredElementDefinitionImpl;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.rusty.bumpkinbatch.BumpkinBatch;

public class BItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BumpkinBatch.ID);
}
