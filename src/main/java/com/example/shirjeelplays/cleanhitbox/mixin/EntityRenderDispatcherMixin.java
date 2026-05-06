package com.example.shirjeelplays.cleanhitbox.mixin;

import com.example.shirjeelplays.cleanhitbox.config.ModConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "renderHitbox", at = @At("HEAD"), cancellable = true)
    private static void onRenderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, float red, float green, float blue, CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();
        boolean isTargeted = client.targetedEntity == entity;

        if (ModConfig.showBoundingBox) {
            Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ());

            Color color = new Color(
                    (isTargeted ? ModConfig.targetHighlightColor : ModConfig.boundingBoxColor) | 0xFF000000,
                    true
            );

            drawBox(matrices, vertices, box,
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    1.0f);
        }

        if (ModConfig.showEyeHeight && entity.isAlive()) {
            double eyeHeight = entity.getEyeHeight(entity.getPose());

            Color color = new Color(ModConfig.eyeHeightColor | 0xFF000000, true);

            Box eyeBox = new Box(0.0, eyeHeight - 0.01, 0.0,
                    entity.getWidth(), eyeHeight + 0.01, entity.getWidth())
                    .offset(-entity.getWidth() / 2.0, 0.0, -entity.getWidth() / 2.0);

            drawBox(matrices, vertices, eyeBox,
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    1.0f);
        }

        if (ModConfig.showLookVector && entity.isAlive()) {
            Vec3d lookVec = entity.getRotationVec(tickDelta);

            Color color = new Color(ModConfig.lookVectorColor | 0xFF000000, true);

            matrices.push();
            matrices.translate(0.0, entity.getEyeHeight(entity.getPose()), 0.0);

            drawLookVector(matrices, vertices, lookVec, color);

            matrices.pop();
        }

        ci.cancel();
    }

    private static void drawBox(MatrixStack matrices, VertexConsumer vertices, Box box, float r, float g, float b, float a) {
        float lineWidth = ModConfig.lineWidth;
        WorldRenderer.drawBox(matrices, vertices, box, r, g, b, a);
        if (lineWidth > 1.0f) {
            float offset = 0.001f * lineWidth;
            WorldRenderer.drawBox(matrices, vertices, box.expand(offset), r, g, b, a);
            WorldRenderer.drawBox(matrices, vertices, box.expand(-offset), r, g, b, a);
        }
    }

    private static void drawLookVector(MatrixStack matrices, VertexConsumer vertices, Vec3d lookVec, Color color) {
        float lineWidth = ModConfig.lineWidth;
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = 1.0f;

        var entry = matrices.peek();

        // Draw multiple lines if thickness is needed
        int passes = lineWidth > 1.0f ? 3 : 1;
        float offsetScale = 0.001f * lineWidth;

        for (int i = 0; i < passes; i++) {
            float offX = (i == 1) ? offsetScale : (i == 2 ? -offsetScale : 0);
            float offY = (i == 1) ? offsetScale : (i == 2 ? -offsetScale : 0);
            float offZ = (i == 1) ? offsetScale : (i == 2 ? -offsetScale : 0);

            vertices.vertex(entry.getPositionMatrix(), offX, offY, offZ)
                    .color(r, g, b, a)
                    .normal(entry, 0, 1, 0);

            vertices.vertex(entry.getPositionMatrix(),
                            (float) (lookVec.x * 2.0) + offX,
                            (float) (lookVec.y * 2.0) + offY,
                            (float) (lookVec.z * 2.0) + offZ)
                    .color(r, g, b, a)
                    .normal(entry, 0, 1, 0);
        }
    }
}