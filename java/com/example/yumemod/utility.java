package com.example.yumemod;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class utility {
    public static float CurveWeight=8;
    public static int CurveMode=0;
    public static ListTag iarray2ltag(ItemStack[] i) {
        ListTag res = new ListTag();

        for(int k = 0; k < i.length; ++k) {
            ItemStack stack = i[k];
            if (stack != null && !stack.isEmpty()){
                CompoundTag it = new CompoundTag();
                stack.save(it);
                it.putInt("index", k);
                res.add(it);
            }
        }

        return res;
    }

    public static ItemStack[] ltag2iarray(ListTag tag, int size) {
        ItemStack[] res = new ItemStack[size];

        for(int i = 0; i < size; ++i) {
            res[i] = ItemStack.EMPTY;
        }

        for(int i = 0; i < tag.size(); ++i) {
            CompoundTag it = tag.getCompound(i);
            int index = it.getInt("index");
            res[index] = ItemStack.of(it);
        }

        return res;
    }
    public static CompoundTag g2ctag(GameState g) {
        CompoundTag res = new CompoundTag();
        res.putInt("x", g.position.getX());
        res.putInt("y", g.position.getY());
        res.putInt("z", g.position.getZ());
        res.putString("d", g.dimension.location().toString());
        res.put("i", iarray2ltag(g.inventory));
        res.putInt("l", g.inventory.length);
        return res;
    }

    public static GameState ctag2g(CompoundTag tag) {
        BlockPos pos = new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
        ResourceKey<Level> dim = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(tag.getString("d")));
        ItemStack[] inventry = ltag2iarray(tag.getList("i", 10), tag.getInt("l"));
        return new GameState(pos, dim, inventry);
    }

    public static Boolean safe(Boolean a) {
        return a == null ? false : a;
    }

    public static int safe(Integer a) {
        return a == null ? 0 : a;
    }

    public static float safe(Float a) {
        return a == null ? 0.0F : a;
    }

    public static String getTimeString(long t) {
        if (t < 3600L) {
            return t / 60L + "分";
        } else {
            double h = (double)(t / 3600L);
            if (h < (double)3.0F) {
                long m = t % 3600L / 60L;
                double var10000 = Math.floor(h);
                return var10000 + "時間" + m + "分";
            } else {
                return Math.floor(h) + "時間";
            }
        }
    }
    public static ShaderInstance getShaderInstanceByName(String shaderName) {
        GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
        for (Field field : GameRenderer.class.getDeclaredFields()) {
            if (field.getType() == ShaderInstance.class) {
                try {
                    field.setAccessible(true);
                    ShaderInstance instance = (ShaderInstance) field.get(gameRenderer);
                    if (instance != null && shaderName.equals(instance.getName())) {
                        return instance;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static void setFloat(ShaderInstance shader, String uniformName, float value) {
        if (shader != null) {
            Uniform uniform = shader.getUniform(uniformName);
            if (uniform != null) {
                uniform.set(value);
            }
        }
    }
    public static void setInt(ShaderInstance shader, String uniformName, int value) {
        if (shader != null) {
            Uniform uniform = shader.getUniform(uniformName);
            if (uniform != null) {
                uniform.set(value);
            }
        }
    }
    public static void setUniform(String name,int a,float b){
        ShaderInstance Shader = getShaderInstanceByName(name);
        setInt(Shader, "CurveMode", a);
        setFloat(Shader, "CurveWeight", b);
    }
    public static void setAllUniform(int a,float b){
        CurveMode=a;
        CurveWeight=b;
        setUniform("rendertype_solid",a,b);
        setUniform("rendertype_cutout",a,b);
        setUniform("rendertype_cutout_mipped",a,b);
        setUniform("rendertype_entity_cutout",a,b);
        setUniform("rendertype_entity_solid",a,b);
        setUniform("rendertype_entity_translucent",a,b);
        setUniform("rendertype_lines",a,b);
        setUniform("rendertype_translucent",a,b);
    }
}