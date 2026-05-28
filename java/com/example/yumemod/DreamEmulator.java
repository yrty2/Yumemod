package com.example.yumemod;

import com.example.yumemod.DreamMaps.Dreams;
import com.example.yumemod.item.ModItems;
import com.example.yumemod.music.DreamMusicManager;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.example.yumemod.utility.setAllUniform;

@Mod.EventBusSubscriber
public class DreamEmulator{
    private static final Map<UUID, Boolean> sleepingMap = new HashMap<>();
    private static final Map<UUID,GameState> sleptMap=new HashMap<>();
    public static final Map<UUID, Boolean> dreamingMap = new HashMap<>();
    private static final Map<UUID, Integer> yumeochiMap = new HashMap<>();

    public static final Map<UUID,GameState> recentDreamMap=new HashMap<>();
    public static final Map<UUID,CompoundTag> nextDreamMap=new HashMap<>();
    private static final Map<UUID, Float> healthMap = new HashMap<>();
    private static final Map<UUID, Integer> foodMap = new HashMap<>();
    private static final Map<UUID, Float> saturationMap = new HashMap<>();
    private static final Map<UUID, Long> yumeTimeMap=new HashMap<>();
    //events
    public static final Map<UUID, Boolean> jellyfishEventMap=new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if (event.player.level().isClientSide) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        UUID id = player.getUUID();
        boolean isSleeping = player.isSleeping();
        boolean wasSleeping = sleepingMap.getOrDefault(id, false);

        //演出
        if (player.level().dimension() == Dreams.ABYSS){
            spawnAbyssBubbles(player);
            if (player.tickCount % 120 == 0){
                player.serverLevel().playSound(
                        null,
                        player.blockPosition(),
                        net.minecraft.sounds.SoundEvents.AMBIENT_UNDERWATER_LOOP,
                        net.minecraft.sounds.SoundSource.AMBIENT,
                        1.0F,
                        0.9F + player.getRandom().nextFloat() * 0.2F
                );
            }
            if(player.getY()>300){
                enterWorld(player,Dreams.SEWERS);
                grantAdv(player,"moon_jump");
            }
        }
        if(dreamingMap.getOrDefault(id,false)){
            Long time=(player.level().getGameTime()-yumeTimeMap.getOrDefault(player.getUUID(),999999999999999L))/20L;
            if(time>1200){
                grantAdv(player,"yume20m");
                if(time>3600){
                    grantAdv(player,"yume60m");
                }
            }
        }

        //イベント

        //睡眠判定
        if (wasSleeping && !isSleeping) {
            long time = player.level().getDayTime() % 24000;
            if (time < 1000){
                if(dreamingMap.getOrDefault(id, false)) {
                    enterWorld(player,randomDream(player));
                }else{
                    enterDream(player);
                }
            }
        }

        sleepingMap.put(id, isSleeping);
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        UUID id = player.getUUID();

        if (dreamingMap.getOrDefault(id, false)) {
            float damage= event.getAmount();
            event.setAmount(0.0F);
            if(player.getHealth()+player.getAbsorptionAmount()<=damage){
                //ほとんど即死
                yumeochiMap.put(player.getUUID(),1);
                exitDream(player);
                return;
            }
            if(event.getSource().is(DamageTypes.DROWN)){
                // ダメージ量を0に
                if(player.level().dimension()!=Dreams.ABYSS && player.getY()<=30){
                    enterWorld(player,Dreams.ABYSS);
                    grantAdv(player,"enter_abyss_event");
                    return;
                }
            }
            if(event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)){
                player.fallDistance=0;
                int x=new java.util.Random().nextInt(3);
                if(x==0){
                    exitDream(player);
                }else{
                    enterWorld(player,randomDream(player));
                }
                return;
            }
            exitDream(player);
        }
    }
    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        if(dreamingMap.getOrDefault(player.getUUID(),false)){
            String message = event.getMessage().getString();
            int x = new java.util.Random().nextInt(3);
            if(message.equals("getup")){
                exitDream(player);
            }
            if(message.equals("0836:random")){
                enterWorld(player,randomDream(player));
            }
            switch (x) {
                case 0:
                    event.setMessage(Component.literal("むにゃむにゃ"));
                    break;
                case 1:
                    event.setMessage(Component.literal("ぐーぐー"));
                    break;
                case 2:
                    event.setMessage(Component.literal("うー"));
                    break;
            }
            grantAdv(player,"negoto");
        }
    }

    private static void enterDream(ServerPlayer player){
        yumeochiMap.put(player.getUUID(),0);
        savePlayerState(player);
        initializeDreamState(player);
        dreamingMap.put(player.getUUID(), true);
        //安全のため
        player.getPersistentData().put("yumemod_data", games2ctag(player));
        //ServerLevel targetLevel = player.server.getLevel(Level.NETHER);
        CompoundTag ndtag=nextDreamMap.get(player.getUUID());
        if(ndtag==null || ndtag.isEmpty()){
            ServerLevel targetLevel = randomDream(player);
            if (targetLevel == null) return;
            enterWorld(player, targetLevel);
        }else{
            applyGameState(player,utility.ctag2g(ndtag));
            grantAdv(player,"collective_unconscious");
            nextDreamMap.remove(player.getUUID());
        }
        yumeTimeMap.put(player.getUUID(),player.level().getGameTime());
        grantAdv(player,"root");
        grantAdv(player,"enter_dream");
        DreamMusicManager.start();
    }
    private static void enterWorld(ServerPlayer player,ResourceKey<Level> level){
        enterWorld(player,player.server.getLevel(level));
    }
    public static void enterWorld(ServerPlayer player, ServerLevel targetLevel){
        Random rand = new Random();
        int x=rand.nextInt(12000)-6000;
        int z=rand.nextInt(12000)-6000;
        int y=0;
        for (int tries = 0; tries < 100; tries++){
            boolean end = false;
            x = rand.nextInt(12000) - 6000;
            z = rand.nextInt(12000) - 6000;
            for (y = 63; y < 100; ++y) {
                BlockState floor = blockat(targetLevel, x, y - 1, z);
                if (!floor.is(Blocks.LAVA) && !floor.is(Blocks.WATER) && !floor.is(Blocks.AIR) && blockat(targetLevel, x, y, z).is(Blocks.AIR) && blockat(targetLevel, x, y + 1, z).is(Blocks.AIR)) {
                    end = true;
                    break;
                }
            }
            if (end) {
                break;
            }
        }
        player.teleportTo(targetLevel,x+0.5,y+1,z+0.5,player.getYRot(),player.getXRot());
        /*setAllUniform(-1,0f);
        if(targetLevel.dimension()==Dreams.BRIDGE_WORLD){
            setAllUniform(2,-0.002f);
        }
        if(targetLevel.dimension()==Dreams.FLOATING_STAIR){
            setAllUniform(10,-0.01f);
        }
        if(targetLevel.dimension()==Dreams.DENSE_FOREST){
            setAllUniform(2,0.01f);
        }
        if(targetLevel.dimension()==Dreams.DREAM_FIELD){
            setAllUniform(2,0.002f);
        }*/
    }
    private static void exitDream(ServerPlayer player){
        //実績
        Long yumetime=(player.level().getGameTime()-yumeTimeMap.getOrDefault(player.getUUID(),0L))/20L;
        if(yumetime<=5){
            grantAdv(player,"yumesupershort");
        }
        //常態保存
        recentDreamMap.put(player.getUUID(),GameStateFrom(player));
        //常態復元
        loadPlayerState(player);
        player.clearFire();
        player.setRemainingFireTicks(0);

        UUID id = player.getUUID();

        BlockPos pos = sleptMap.get(id).position;
        ResourceKey<Level> dim = sleptMap.get(id).dimension;

        if (pos == null || dim == null) return;

        ServerLevel targetLevel = player.server.getLevel(dim);
        if (targetLevel == null) return;

        jellyfishEventMap.put(id,false);
        dreamingMap.put(id,false);
        ServerLevel overworld = player.server.getLevel(Level.OVERWORLD);
        if(yumeochiMap.getOrDefault(id,0)==1){
            overworld.setDayTime(19200);
            yumeochiMap.put(id,2);
            grantAdv(player,"yumeochi");
        }else{
            overworld.setDayTime(0);
        }
        yumeTimeMap.remove(id);

        player.teleportTo(targetLevel,
                pos.getX()+0.5,
                pos.getY()+1,
                pos.getZ()+0.5,
                player.getYRot(),
                player.getXRot());
        grantAdv(player,"exit_dream");
        //メモリを洗う
        sleptMap.remove(id);
        healthMap.remove(id);
        foodMap.remove(id);
        saturationMap.remove(id);
        //setAllUniform(-1,0f);
    }
    private static BlockState blockat(Level level,int x,int y,int z){
        return level.getBlockState(new BlockPos(x,y,z));
    }
    private static void savePlayerState(ServerPlayer player){
        UUID id = player.getUUID();
        sleptMap.put(id,GameStateFrom(player));
        healthMap.put(id, player.getHealth());
        foodMap.put(id, player.getFoodData().getFoodLevel());
        saturationMap.put(id, player.getFoodData().getSaturationLevel());
    }
    public static GameState GameStateFrom(ServerPlayer player){
        ItemStack[] inv = new ItemStack[player.getInventory().getContainerSize()];
        for(int i=0;i<inv.length;i++){
            inv[i] = player.getInventory().getItem(i).copy();
        }
        return new GameState(new BlockPos(player.getBlockX(),player.getBlockY(),player.getBlockZ()),player.level().dimension(),inv);
    }
    public static void applyGameState(ServerPlayer player){
        applyGameState(player,sleptMap.get(player.getUUID()));
    }
    public static void applyGameState(ServerPlayer player,GameState u){
        ServerLevel level= ServerLifecycleHooks.getCurrentServer().getLevel(u.dimension);
        if(level==null){
            //RetryManager.add(() -> applyGameState(player, u), 20);
            return;
        }
        player.teleportTo(level,u.position.getX()+0.5,u.position.getY()+1,u.position.getZ()+0.5,player.getYRot(),player.getXRot());
        ItemStack[] inv=u.inventory;
        if(inv != null){
            for(int i=0;i<inv.length;i++){
                player.getInventory().setItem(i, inv[i].copy());
            }
        }
    }
    private static void loadPlayerState(ServerPlayer player){
        UUID id = player.getUUID();
        applyGameState(player);
        if(healthMap.containsKey(id)){
            player.setHealth(healthMap.get(id));
        }

        if(foodMap.containsKey(id)){
            player.getFoodData().setFoodLevel(foodMap.get(id));
        }
        if(saturationMap.containsKey(id)) {
            player.getFoodData().setSaturation(saturationMap.get(id));
        }
        player.getFoodData().setExhaustion(0);
    }
    private static void initializeDreamState(ServerPlayer player){
        player.getInventory().clearContent();
    }
    public static void grantAdv(ServerPlayer player,String name){
        Advancement adv = player.server.getAdvancements()
                .getAdvancement(ResourceLocation.fromNamespaceAndPath("yumemod",name));

        if (adv!=null) {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(adv);

            for (String criterion : progress.getRemainingCriteria()) {
                player.getAdvancements().award(adv, criterion);
            }
        }
    }
    //ディメンション移動
    @SubscribeEvent
    public static void onTravel(EntityTravelToDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (event.getDimension()==Level.NETHER){
            if(dreamingMap.getOrDefault(player.getUUID(), false)){
                event.setCanceled(true);
                enterWorld(player,Dreams.DREAM_NETHER);
            }
        }
    }
    public static ServerLevel randomDream(ServerPlayer player){
        int x = new java.util.Random().nextInt(10);
        switch (x) {
            case 0:
                return player.server.getLevel(Dreams.DREAM_NETHER);
            case 1:
                return player.server.getLevel(Dreams.DREAM_FIELD);
            case 2:
                return player.server.getLevel(Dreams.ICEBERG);
            case 3:
                return player.server.getLevel(Dreams.ABYSS);
            case 4:
                return player.server.getLevel(Dreams.SEWERS);
            case 5:
                return player.server.getLevel(Dreams.FLOATING_STAIR);
            case 6:
                return player.server.getLevel(Dreams.SAND_TUNNEL);
            case 7:
                return player.server.getLevel(Dreams.BRIDGE_WORLD);
            case 8:
                return player.server.getLevel(Dreams.STREET);
            case 9:
                return player.server.getLevel(Dreams.DENSE_FOREST);
            default:
                return null;
        }
    }
    private static void spawnAbyssBubbles(ServerPlayer player){
        double range=24;
        ServerLevel level = player.serverLevel();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            double px = player.getX() + (rand.nextDouble()-0.5)*range;
            double py = player.getY() + (rand.nextDouble()-0.5)*range;
            double pz = player.getZ() + (rand.nextDouble() - 0.5)*range;

            double vx = (rand.nextDouble() - 0.5) * 0.02;
            double vy = 0.03 + rand.nextDouble() * 0.08;
            double vz = (rand.nextDouble() - 0.5) * 0.02;

            level.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.BUBBLE,
                    px, py, pz,
                    1,
                    vx, vy, vz,
                    0.0
            );
        }
    }
    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null){
            if(mc.player.level().dimension() == Dreams.ABYSS) {
                event.setRed(0.04F);
                event.setGreen(0.06F);
                event.setBlue(0.18F);
            }
            if(mc.player.level().dimension() == Dreams.DENSE_FOREST){
                event.setRed(0.43F);
                event.setGreen(0.53F);
                event.setBlue(0.55F);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null){
            if(mc.player.level().dimension() == Dreams.ABYSS){
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(24.0F);
                event.setCanceled(true);
            }
            if(mc.player.level().dimension() == Dreams.DENSE_FOREST){
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(16.0F);
                event.setCanceled(true);
            }
        }
    }
    public static CompoundTag games2ctag(ServerPlayer player){
        UUID id=player.getUUID();
        CompoundTag res=new CompoundTag();
        res.putBoolean("d",dreamingMap.getOrDefault(id,false));
        res.putBoolean("s",sleepingMap.getOrDefault(id,false));
        res.putInt("y",yumeochiMap.getOrDefault(id,0));
        res.putFloat("h",healthMap.getOrDefault(id,0f));
        res.putInt("f",foodMap.getOrDefault(id,0));
        res.putFloat("sat",saturationMap.getOrDefault(id,0f));
        res.putLong("yt",yumeTimeMap.getOrDefault(id,0L));
        //slept gamestate
        GameState g=sleptMap.get(id);
        if(g!=null) {
            res.putInt("g:x", g.position.getX());
            res.putInt("g:y", g.position.getY());
            res.putInt("g:z", g.position.getZ());
            res.putString("g:d", g.dimension.location().toString());
            res.putInt("g:s", g.inventory.length);
            res.put("g:i", utility.iarray2ltag(g.inventory));
        }
        GameState r=recentDreamMap.get(id);
        if(r!=null){
            res.put("r",utility.g2ctag(r));
        }
        CompoundTag nd=nextDreamMap.get(id);
        if(nd!=null){
            res.put("nd",nd);
        }
        return res;
    }
    public static void ctag2games(ServerPlayer player,CompoundTag tag){
        UUID id=player.getUUID();
        dreamingMap.put(id,tag.getBoolean("d"));
        sleepingMap.put(id,tag.getBoolean("s"));
        yumeochiMap.put(id,tag.getInt("y"));
        healthMap.put(id,tag.getFloat("h"));
        foodMap.put(id,tag.getInt("f"));
        yumeTimeMap.put(id,tag.getLong("yt"));
        saturationMap.put(id,tag.getFloat("sat"));
        GameState g=new GameState(new BlockPos(tag.getInt("g:x"),tag.getInt("g:y"),tag.getInt("g:z")),
                ResourceKey.create(Registries.DIMENSION,ResourceLocation.parse(tag.getString("g:d"))),
                utility.ltag2iarray(tag.getList("g:i",Tag.TAG_COMPOUND),tag.getInt("g:s")));
        sleptMap.put(id,g);
        recentDreamMap.put(id,utility.ctag2g(tag.getCompound("r")));
        nextDreamMap.put(id,tag.getCompound("nd"));
    }
    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player=(ServerPlayer) event.getEntity();
        CompoundTag tag=player.getPersistentData();
        if(tag.contains("yumemod_data")) {
            ctag2games(player,tag.getCompound("yumemod_data"));
        }
    }
    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        UUID id = player.getUUID();
        player.getPersistentData().put("yumemod_data", games2ctag(player));
    }
    @SubscribeEvent
    public static void onPickupXP(net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.level().isClientSide) return;
        if(dreamingMap.getOrDefault(player.getUUID(),false)) {
            event.getOrb().value*=3;
        }
    }
}