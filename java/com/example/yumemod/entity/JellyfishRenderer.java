package com.example.yumemod.entity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JellyfishRenderer extends MobRenderer<JellyfishEntity, Jellyfish<JellyfishEntity>> {

    public JellyfishRenderer(EntityRendererProvider.Context context) {
        super(context,
                new Jellyfish<>(context.bakeLayer(Jellyfish.LAYER_LOCATION)),
                0.3f);
    }

    @Override
    protected RenderType getRenderType(JellyfishEntity entity, boolean bodyVisible,
                                       boolean translucent, boolean glowing) {
        return RenderType.entityTranslucent(this.getTextureLocation(entity));
    }

    @Override
    public ResourceLocation getTextureLocation(JellyfishEntity entity) {
        return new ResourceLocation("yumemod", "textures/entity/jellyfish.png");
    }
}