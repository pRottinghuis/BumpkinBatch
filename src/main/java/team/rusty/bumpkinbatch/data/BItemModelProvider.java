package team.rusty.bumpkinbatch.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.registry.BItems;

public class BItemModelProvider extends ItemModelProvider {
    public BItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BumpkinBatch.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        candyTextures(BItems.EAST_TWICKS);
        candyTextures(BItems.WEST_TWICKS);
        candyTextures(BItems.ORE_O);
        candyTextures(BItems.GUMMY_BOARS);
        candyTextures(BItems.CREEPER_STICK);

        withExistingParent(BItems.REAPER_SPAWN_EGG.getId().getPath(), new ResourceLocation("item/template_spawn_egg"));
    }

    public void candyTextures(RegistryObject<Item> item) {
        String itemName = item.getId().getPath();
        singleTexture(itemName, new ResourceLocation("item/generated"), "layer0", new ResourceLocation("bumpkinbatch", "item/" + itemName));
    }
}
