package com.rgerva.elektrocraft.config;

import com.rgerva.elektrocraft.ElektroCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = ElektroCraft.MOD_ID)
public class ModConfig {
  private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

  private static final ModConfigSpec.IntValue FE_PER_VOLT =
      BUILDER
          .comment("How many FE equals 1 Volt (ex: 20 = 1V = 20FE)")
          .defineInRange("fePerVolt", 20, 1, Integer.MAX_VALUE);

  public static final ModConfigSpec SPEC = BUILDER.build();
  public static int fePerVolt;

  @SubscribeEvent
  static void onLoad(final ModConfigEvent event) {
    fePerVolt = FE_PER_VOLT.get();
  }
}
