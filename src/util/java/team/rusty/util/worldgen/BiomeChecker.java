package team.rusty.util.worldgen;

import net.minecraft.resources.ResourceLocation;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * A tool to warn you if your biome is missing features.
 *
 * @author TheDarkColour
 */
public final class BiomeChecker {
    private static final Map<String, String> WARNINGS = Map.of(
            "addDefaultOres", "Missing overworld ores",
            "addDefaultUndergroundVariety", "Missing granite, andesite, diorite",
            "addDefaultSoftDisks", "Missing clay and sand disks",
            "addDefaultCarvers", "Missing overworld caves and ravines",
            "addDefaultLakes", "Missing overworld water lakes",
            "addDefaultCrystalFormations", "Missing overworld geodes",
            "addDefaultMonsterRoom", "Missing overworld dungeons",
            "addDefaultSprings", "Missing overworld water and lava springs",
            "commonSpawns", "Missing overworld hostile/cave mob spawns",
            "farmAnimals", "Missing overworld farm animal spawns"
    );

    public static void checkClass(AbstractBiome biome, ResourceLocation id) {
        var contents = biome.getClass().getResourceAsStream(biome.getClass().getSimpleName() + ".class");

        try {
            var reader = new ClassReader(contents);
            var node = new ClassNode();
            reader.accept(node, 0);

            var configured = false;

            for (var method : node.methods) {
                // noinspection AssignmentUsedAsCondition
                if (configured = method.name.equals("configure")) {
                    var methodCalls = StreamSupport.stream(method.instructions.spliterator(), false)
                            .filter(MethodInsnNode.class::isInstance)
                            .map(MethodInsnNode.class::cast)
                            .filter(insn -> insn.owner.equals("team/rusty/rpg/worldgen/BiomeDefaults")).collect(Collectors.toList());
                    for (var warning : WARNINGS.entrySet()) {
                        if (methodCalls.stream().noneMatch(insn -> insn.name.equals(warning.getKey()))) {
                            System.out.printf("BiomeChecker: %s (use `%s`) for biome \"%s\"\n", warning.getValue(), warning.getKey(), id);
                        }
                    }
                    break;
                }
            }

            if (!configured) {
                System.out.println("BiomeChecker: Missing `configure` method on biome " + id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
