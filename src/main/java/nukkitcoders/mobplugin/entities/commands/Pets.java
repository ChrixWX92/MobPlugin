package nukkitcoders.mobplugin.entities.commands;


import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityItemFrame;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.ItemMap;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.TextFormat;
import com.denzelcode.form.FormAPI;
import com.denzelcode.form.event.ModalFormSubmitEvent;
import com.gms.mc.PlayerInstance;
import com.gms.mc.custom.particles.ParticleFX;
import com.gms.mc.custom.particles.ParticleFXSequence;
import com.gms.mc.custom.sound.*;
import com.gms.mc.data.ChildProfile;
import com.gms.mc.error.InvalidFrameWriteException;
import com.gms.mc.util.Helper;
import com.gms.mc.util.Log;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.entities.GSPetData;
import nukkitcoders.mobplugin.entities.animal.flying.Bat;
import nukkitcoders.mobplugin.entities.animal.flying.Bee;
import nukkitcoders.mobplugin.entities.animal.flying.Parrot;
import nukkitcoders.mobplugin.entities.animal.jumping.Rabbit;
import nukkitcoders.mobplugin.entities.animal.swimming.*;
import nukkitcoders.mobplugin.entities.animal.walking.*;
import nukkitcoders.mobplugin.entities.monster.flying.EnderDragon;
import nukkitcoders.mobplugin.entities.monster.walking.Creeper;
import nukkitcoders.mobplugin.entities.monster.walking.Husk;
import nukkitcoders.mobplugin.entities.monster.walking.Wolf;
import ru.nukkitx.forms.elements.CustomForm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.nukkit.level.ParticleEffect.CAMERA_SHOOT_EXPLOSION;

public class Pets extends Command {

    public Pets() {
        super("pets");
        this.setDescription("Opens the pet selection menu.");
    }

    public static final List<String> petList = Arrays.asList(
            "Bat", "Bee",
            "Cat", "Chicken", "Cod", "Cow", "Creeper",
            "Dolphin", "Donkey",
            "Fox",
            "Horse", "Husk",
            "Llama",
            "Mooshroom", "Mule",
            "Ocelot",
            "Panda", "Parrot", "Pig", "Polar Bear", "Pufferfish",
            "Rabbit",
            "Salmon", "Sheep", "Skeleton", "Squid", "Strider",
            "Tropical Fish", "Turtle",
            "Wolf",
            "Zombie"
    );



    public static String petName;
    public String petType;
    public boolean closed = false;
    private static Object pet;
    public Player p;
    private static ChildProfile profile;
    private static String chosenPet = "";
    private static BlockEntityItemFrame anchor;

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Cannot execute from the console.");
            return false;
        }

        p = ((Player) sender).getPlayer();
        setProfile(PlayerInstance.getPlayer(p.getName()).getProfile());

        if (args.length == 0) {

            CustomForm petsForm = new CustomForm("§l§n§1 Pick a pet!")
                    .addLabel("§l§bChoose your pet:")
                    .addDropDown("§fPets", petList)
                    .addLabel("§l§bName your pet:")
                    .addInput();

            petsForm.send(p, (target, form, data) -> {
                if (data == null) return;
                petType = (String) data.get(1);
                if (!((String) data.get(3)).replace(" ", "").equals("")) {
                    petName = (String) data.get(3);
                } else {
                    petName = null;
                }

                try {
                    spawnHandler(petType, p, "");
                } catch (InvalidFrameWriteException e) {
                    e.printStackTrace();
                }

                if (Pets.petName != null && !Pets.petName.equals("")){
                    ((Entity) getPet()).setNameTag(Pets.petName);
                    ((Entity) getPet()).setNameTagVisible(true);
                    ((Entity) getPet()).setNameTagAlwaysVisible(true);
                }

                petName = null;

            });
            return true;

        } else if (args.length > 1) {
            p.sendMessage(TextFormat.RED + "Too many arguments. Please specify \"list\" or a pet type.");
            return false;
        } else if (args[0].equals("list")) {
            p.sendMessage(petList.toString());
            return true;
        } else {
            boolean check = false;
            for (String petArg : petList) {
                if (petArg.replaceAll(" ", "").equalsIgnoreCase(args[0])) {
                    setChosenPet(petArg);
                    check = true;
                    break;
                }
            }
            if (check) {

                String title;
                String mid;
                char firstChar = getChosenPet().charAt(0);
                int price = GSPetData.petPrices.get(getChosenPet());

                if ("AEIOUaeiou".indexOf(firstChar) != -1) {
                    title = "§l§n§1 Buy an ";
                    mid = "buy an ";
                } else {
                    title = "§l§n§1 Buy a ";
                    mid = "buy a ";
                }

                title = title.concat(TextFormat.DARK_PURPLE + getChosenPet() + "§l§n§1?");

                FormAPI.modalWindowForm("indivPet", title, "§l§bWould you like to " + mid + TextFormat.GOLD + getChosenPet() + "§l§b for " + TextFormat.LIGHT_PURPLE + price + "§l§b tickets?", "Yes", "No")
                        .addHandler(ModalFormSubmitEvent::isAccepted).sendTo(p);
                return true;
            } else {
                p.sendMessage(args[0] + TextFormat.RED + " is not a valid pet type. Please refer to \"/pets list\" for all available pets.");
                return false;
            }
        }
    }

    public static void spawnHandler(String petType, Player p, String name) throws InvalidFrameWriteException {

        Location loc = GSPetData.petLocs.get(petType);
        int petCost = GSPetData.petPrices.get(petType);

<<<<<<< Updated upstream
        p.sendMessage(petType);
=======
        p.sendMessage(TextFormat.GREEN + "Congratulations on your new " + petType + " - " + TextFormat.LIGHT_PURPLE + name + TextFormat.GREEN + "!");
>>>>>>> Stashed changes

        switch(petType){
            case "Bat":
                setPet(new Bat(p.chunk, nbtMaker(p, loc)));
                ((Bat) getPet()).spawnToAll();
                break;

            case "Bee":
                setPet(new Bee(p.chunk, nbtMaker(p, loc)));
                ((Bee) getPet()).spawnToAll();
                break;

            case "Cat":
                setPet(new Cat(p.chunk, nbtMaker(p, loc)));
                ((Cat) getPet()).spawnToAll();
                break;

            case "Chicken":
                setPet(new Chicken(p.chunk, nbtMaker(p, loc)));
                ((Chicken) getPet()).spawnToAll();
                break;

            case "Cod":
                setPet(new Cod(p.chunk, nbtMaker(p, loc)));
                ((Cod) getPet()).spawnToAll();
                break;

            case "Cow":
                setPet(new Cow(p.chunk, nbtMaker(p, loc)));
                ((Cow) getPet()).spawnToAll();
                break;

            case "Creeper":
                setPet(new Creeper(p.chunk, nbtMaker(p, loc)));
                ((Creeper) getPet()).spawnToAll();
                break;

            case "Dolphin":
                setPet(new Dolphin(p.chunk, nbtMaker(p, loc)));
                ((Dolphin) getPet()).spawnToAll();
                break;

            case "Donkey":
                setPet(new Donkey(p.chunk, nbtMaker(p, loc)));
                ((Donkey) getPet()).spawnToAll();
                break;

            case "Fox":
                setPet(new Fox(p.chunk, nbtMaker(p, loc)));
                ((Fox) getPet()).spawnToAll();
                break;

            case "Horse":
                setPet(new Horse(p.chunk, nbtMaker(p, loc)));
                ((Horse) getPet()).spawnToAll();
                break;

            case "Husk":
                setPet(new Husk(p.chunk, nbtMaker(p, loc)));
                ((Husk) getPet()).spawnToAll();
                break;

            case "Llama":
                setPet(new Llama(p.chunk, nbtMaker(p, loc)));
                ((Llama) getPet()).spawnToAll();
                break;

            case "Mooshroom":
                setPet(new Mooshroom(p.chunk, nbtMaker(p, loc)));
                ((Mooshroom) getPet()).spawnToAll();
                break;

            case "Mule":
                setPet(new Mule(p.chunk, nbtMaker(p, loc)));
                ((Mule) getPet()).spawnToAll();
                break;

            case "Ocelot":
                setPet(new Ocelot(p.chunk, nbtMaker(p, loc)));
                ((Ocelot) getPet()).spawnToAll();
                break;

            case "Panda":
                setPet(new Panda(p.chunk, nbtMaker(p, loc)));
                ((Panda) getPet()).spawnToAll();
                break;

            case "Parrot":
                setPet(new Parrot(p.chunk, nbtMaker(p, loc)));
                ((Parrot) getPet()).spawnToAll();
                break;

            case "Pig":
                setPet(new Pig(p.chunk, nbtMaker(p, loc)));
                ((Pig) getPet()).spawnToAll();
                break;

            case "Polar Bear":
                setPet(new PolarBear(p.chunk, nbtMaker(p, loc)));
                ((PolarBear) getPet()).spawnToAll();
                break;

            case "Pufferfish":
                setPet(new Pufferfish(p.chunk, nbtMaker(p, loc)));
                ((Pufferfish) getPet()).spawnToAll();
                break;

            case "Rabbit":
                setPet(new Rabbit(p.chunk, nbtMaker(p, loc)));
                ((Rabbit) getPet()).spawnToAll();
                break;

            case "Salmon":
                setPet(new Salmon(p.chunk, nbtMaker(p, loc)));
                ((Salmon) getPet()).spawnToAll();
                break;

            case "Sheep":
                setPet(new Sheep(p.chunk, nbtMaker(p, loc)));
                ((Sheep) getPet()).spawnToAll();
                break;

            case "Squid":
                setPet(new Squid(p.chunk, nbtMaker(p, loc)));
                ((Squid) getPet()).spawnToAll();
                break;

            case "Strider":
                setPet(new Strider(p.chunk, nbtMaker(p, loc)));
                ((Strider) getPet()).spawnToAll();
                break;

            case "Tropical Fish":
                setPet(new TropicalFish(p.chunk, nbtMaker(p, loc)));
                ((TropicalFish) getPet()).spawnToAll();
                break;

            case "Turtle":
                setPet(new Turtle(p.chunk, nbtMaker(p, loc)));
                ((Turtle) getPet()).spawnToAll();
                break;

            case "Wolf":
                setPet(new Wolf(p.chunk, nbtMaker(p, loc)));
                ((Wolf) getPet()).spawnToAll();
                break;

        }
        //((Entity) pet).invulnerable = true;
        ((Entity) getPet()).fireProof = true;
        ((BaseEntity) getPet()).setFriendly(true);

        if (name != null && !name.equals("")){
            Entity tempPet = (Entity) getPet();
            tempPet.setNameTag(name);
            tempPet.setNameTagVisible(true);
            tempPet.setNameTagAlwaysVisible(true);
        }

        MusicMaker.playSFX(SFX.Type.PET_SPAWN, p);
        ParticleFXSequence pFX = new ParticleFXSequence(ParticleFX.PET_SPAWN, p.getLevel(), ((BaseEntity) getPet()).getLocation());
        synchronized (pFX) {
            pFX.run();
        }
        petName = null;

        getProfile().spendTickets(petCost);

        findPetFrame(p,petType,true);
        dragonCheck(p);

    }

    public static CompoundTag nbtMaker(Player p, Location loc) {

        CompoundTag nbt = new CompoundTag();

                nbt.putList(new ListTag<>("Pos")
                        .add(new DoubleTag("", loc.x))
                        .add(new DoubleTag("", loc.y))
                        .add(new DoubleTag("", loc.z)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", (float) p.getYaw()))
                        .add(new FloatTag("", (float) p.getPitch())))
                .putBoolean("Invulnerable", true)
                .putBoolean("isRotation", false)
                //.putList(consoleCommands) Use these if you would like pets to handle commands on interact, like NPCs
                //.putList(playerCommands)
                .putBoolean("npc", false)
                .putFloat("scale", 1);
        return nbt;
    }

    public static void findPetFrame(Player player, String pet, boolean bool) {

        Level level = player.getLevel();

        Map<Long, ? extends FullChunk> chunksMap = level.getChunks();

        Map<Long, BlockEntity> entMap;

        for (Map.Entry<Long, ? extends FullChunk> entry : chunksMap.entrySet()) {
            entMap = entry.getValue().getBlockEntities();
            for (Map.Entry<Long, BlockEntity> chunkEntry : entMap.entrySet()) {
                if (chunkEntry.getValue().namedTag.contains(pet)) {
                    if (chunkEntry.getValue() instanceof BlockEntityItemFrame) {
                        BlockEntityItemFrame iF = (BlockEntityItemFrame) chunkEntry.getValue();
                        iF.namedTag.putBoolean(pet, bool);
                        ItemMap map = new ItemMap();
                        BufferedImage image = null;
                        File filePath;
                        try {
                            if (bool) {
                                filePath = new File(Helper.getImagesDir().toFile(), "Animal.png");
                                MusicMaker.playArpeggio(player, new Chord(Note.ASHARP4_BFLAT4, ChordType.SIX9), 50, Sound.NOTE_XYLOPHONE);
                                MusicMaker.playArpeggio(player, new Chord(Note.ASHARP3_BFLAT3, ChordType.SIX9), 50, Sound.NOTE_XYLOPHONE);
                                MusicMaker.playArpeggio(player, new Chord(Note.ASHARP4_BFLAT4, ChordType.SIX9), 50, Sound.NOTE_FLUTE);
                                MusicMaker.playArpeggio(player, new Chord(Note.ASHARP4_BFLAT4, ChordType.SIX9), 50, Sound.NOTE_HARP);
                            } else {
                                filePath = new File(Helper.getImagesDir().toFile(), "NoAnimal.png");
                            }
                            image = ImageIO.read(filePath);
                        }
                        catch (IOException ioException) {
                            //ioException.printStackTrace();
                            String nameError = (TextFormat.RED + "Frame image not found in " + Helper.getImagesDir());
                            Log.debug(nameError);
                        }
                        map.setImage(image);
                        iF.setItem(map);
                        iF.getLevel().addParticleEffect(new Vector3(iF.add(0.5).x, iF.add(0,0.5).y, iF.add(0,0,0.5).z),CAMERA_SHOOT_EXPLOSION);
                    }
                }
                if (chunkEntry.getValue() instanceof BlockEntitySign) {
                    if (((BlockEntitySign) chunkEntry.getValue()).getText()[0].equals("ANCHOR")) {
                        setAnchor((BlockEntityItemFrame) chunkEntry.getValue());
                    }
                }
            }
        }
    }

    public static void dragonCheck(Player player) {

        int petCount = 0;
        Level level = player.getLevel();
        Map<Long, ? extends FullChunk> chunksMap = level.getChunks();
        Map<Long, BlockEntity> entMap;

        for (String pet : petList) {
            for (Map.Entry<Long, ? extends FullChunk> entry : chunksMap.entrySet()) {
                entMap = entry.getValue().getBlockEntities();
                for (Map.Entry<Long, BlockEntity> chunkEntry : entMap.entrySet()) {
                    if (chunkEntry.getValue().namedTag.contains(pet)) {
                        if (chunkEntry.getValue() instanceof BlockEntityItemFrame) {
                            BlockEntityItemFrame iF = (BlockEntityItemFrame) chunkEntry.getValue();
                            if (iF.namedTag.getBoolean(pet)){
                                petCount++;
                            }
                        }
                    }
                }
            }
        }

        if (petCount == petList.size()) {
            dragonSummon(player);
        }
    }

    public static void dragonSummon(Player p) {

        MusicMaker.playSFX(SFX.Type.DRAGON_SPAWN, p);
        ParticleFXSequence pFX = new ParticleFXSequence(ParticleFX.DRAGON_SPAWN, p.getLevel(), ((BaseEntity) getPet()).getLocation());
        synchronized (pFX) {
            pFX.run();
        }

        Block testBlock;

        int startX = (int) (getAnchor().x-3);
        int startY = (int) (getAnchor().y);
        int startZ = (int) (getAnchor().z-3);
        int endX = (int) (getAnchor().x+3);
        int endY = (int) (getAnchor().y+10);
        int endZ = (int) (getAnchor().z+3);

        for (int z = startZ; z < endZ; z++) {
            for (int y = startY; y < endY; y++) {
                for (int x = startX; x < endX; x++) {
                    testBlock = p.getLevel().getBlock(new Location (x,y,z));
                    if (testBlock.getId() == 173 || testBlock.getId() == 49 || (testBlock.getId() == 241 && testBlock.getDamage() == 1)) {
                        p.getLevel().setBlock(new Location (x, y, z), new BlockAir());
                    }
                }
            }
        }

        petName = null;

        setPet(new EnderDragon(p.chunk, nbtMaker(p, new Location(88, 50, -99))));
        ((EnderDragon) getPet()).spawnToAll();
    }

    public static Object getPet() {
        return pet;
    }

    public static void setPet(Object petSet) {
        pet = petSet;
    }

    public static String getChosenPet() {
        return chosenPet;
    }

    public static void setChosenPet(String chosenPet) {
        Pets.chosenPet = chosenPet;
    }

    public static ChildProfile getProfile() {
        return profile;
    }

    public static void setProfile(ChildProfile profile) {
        Pets.profile = profile;
    }

    public static BlockEntityItemFrame getAnchor() {
        return anchor;
    }

    public static void setAnchor(BlockEntityItemFrame anchor) {
        Pets.anchor = anchor;
    }

}


/*
    public BaseEntity createWaterPet(Object type, Position pos) {
        BaseEntity entity = (BaseEntity) Entity.createEntity((String) type, pos);
        if (entity != null) {
            if (!entity.isInsideOfSolid() && !petTooNearOfPlayer(pos)) {
                CreatureSpawnEvent ev = new CreatureSpawnEvent(entity.getNetworkId(), pos, entity.namedTag, CreatureSpawnEvent.SpawnReason.NATURAL);
                Server.getInstance().getPluginManager().callEvent(ev);
                if (!ev.isCancelled()) {
                    entity.spawnToAll();
                } else {
                    entity.close();
                    entity = null;
                }
            } else {
                entity.close();
                entity = null;
            }
        }
        return entity;
    }

    private boolean petTooNearOfPlayer(Position pos) {
        for (Player p : pos.getLevel().getPlayers().values()) {
            if (p.distanceSquared(pos) < 196) {
                return true;
            }
        }
        return false;
    }
    */









