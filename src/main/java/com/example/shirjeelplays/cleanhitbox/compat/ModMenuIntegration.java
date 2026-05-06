package com.example.shirjeelplays.cleanhitbox.compat;

import com.example.shirjeelplays.cleanhitbox.config.ModConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.Color;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> createConfigScreen(parent);
    }

    public static Screen createConfigScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("CLEANHITBOX"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.clean-hitbox.category.behavior"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("config.clean-hitbox.showBoundingBox"))
                                .description(OptionDescription.of(Text.translatable("config.clean-hitbox.tooltip.showBoundingBox")))
                                .binding(true, () -> ModConfig.showBoundingBox, newValue -> ModConfig.showBoundingBox = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("config.clean-hitbox.showEyeHeight"))
                                .description(OptionDescription.of(Text.translatable("config.clean-hitbox.tooltip.showEyeHeight")))
                                .binding(true, () -> ModConfig.showEyeHeight, newValue -> ModConfig.showEyeHeight = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("config.clean-hitbox.showLookVector"))
                                .description(OptionDescription.of(Text.translatable("config.clean-hitbox.tooltip.showLookVector")))
                                .binding(true, () -> ModConfig.showLookVector, newValue -> ModConfig.showLookVector = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.clean-hitbox.category.colors"))
                        .option(Option.<Color>createBuilder()
                                .name(Text.translatable("config.clean-hitbox.boundingBoxColor"))
                                .description(OptionDescription.of(Text.translatable("config.clean-hitbox.tooltip.boundingBoxColor")))
                                .binding(new Color(0xFFFFFF), () -> new Color(ModConfig.boundingBoxColor | 0xFF000000, true), newValue -> ModConfig.boundingBoxColor = newValue.getRGB() & 0xFFFFFF)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Text.translatable("config.clean-hitbox.eyeHeightColor"))
                                .description(OptionDescription.of(Text.translatable("config.clean-hitbox.tooltip.eyeHeightColor")))
                                .binding(new Color(0xFF0000), () -> new Color(ModConfig.eyeHeightColor | 0xFF000000, true), newValue -> ModConfig.eyeHeightColor = newValue.getRGB() & 0xFFFFFF)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Text.translatable("config.clean-hitbox.lookVectorColor"))
                                .description(OptionDescription.of(Text.translatable("config.clean-hitbox.tooltip.lookVectorColor")))
                                .binding(new Color(0x0000FF), () -> new Color(ModConfig.lookVectorColor | 0xFF000000, true), newValue -> ModConfig.lookVectorColor = newValue.getRGB() & 0xFFFFFF)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Text.translatable("config.clean-hitbox.targetHighlightColor"))
                                .description(OptionDescription.of(Text.translatable("config.clean-hitbox.tooltip.targetHighlightColor")))
                                .binding(new Color(0xFF0000), () -> new Color(ModConfig.targetHighlightColor | 0xFF000000, true), newValue -> ModConfig.targetHighlightColor = newValue.getRGB() & 0xFFFFFF)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.clean-hitbox.category.linewidth"))
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("config.clean-hitbox.lineWidth"))
                                .description(OptionDescription.of(Text.translatable("config.clean-hitbox.tooltip.lineWidth")))
                                .binding(1.0f, () -> ModConfig.lineWidth, newValue -> ModConfig.lineWidth = newValue)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.1f, 10.0f).step(0.1f))
                                .build())
                        .build())
                .save(ModConfig::save)
                .build()
                .generateScreen(parent);
    }
}
