package nukkitcoders.mobplugin.entities.commands;


import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.TextFormat;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.entities.animal.flying.Bat;
import nukkitcoders.mobplugin.entities.animal.flying.Bee;
import nukkitcoders.mobplugin.entities.animal.flying.Parrot;
import nukkitcoders.mobplugin.entities.animal.jumping.Rabbit;
import nukkitcoders.mobplugin.entities.animal.swimming.*;
import nukkitcoders.mobplugin.entities.animal.walking.*;
import nukkitcoders.mobplugin.entities.monster.walking.Wolf;
import ru.nukkitx.forms.elements.CustomForm;

import java.util.Arrays;
import java.util.List;

public class Pets extends Command {

    public Pets() {
        super("pets");
        this.setDescription("Opens the pet selection menu.");
    }

    public static final List<String> petList = Arrays.asList(
            "Bat", "Bee", "Cat", "Chicken", "Cod", "Cow",
            "Dolphin", "Donkey", "Fox",
            "Horse", "Llama", "Mooshroom", "Mule", "Ocelot", "Panda", "Parrot",
            "Pig", "Polar Bear", "Pufferfish", "Rabbit", "Salmon", "Sheep", "Squid",
            "Strider", "Tropical Fish", "Turtle", "Wolf"
    );

    public static String petName;
    public String petType = "Cod";
    public boolean closed = false;
    public Object pet;


    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Cannot execute from the console.");
            return false;
        }

        Player p = ((Player) sender).getPlayer();

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
                ;
            } else {
                petName = null;
            }

            /** Changing of coords can occur here **/



            //AutoSpawnTask ast = new AutoSpawnTask(MobPlugin.getInstance());

            Integer type;

            /**
             * 0 = Domestic
             * 1 = Pastoral
             * 2 = Woodland
             * 3 = Cave
             * 4 = Water
             * 5 = Jungle
             * 6 = Bamboo
             * 7 = Parrot
             * 8 = Snow
             * 9 = Bee
             * 10 = Nether
             * 11 = Beach
             */

            switch(petType){
                case "Bat":
                    type = 3;
                    pet = new Bat(p.chunk, nbtMaker(p, type));
                    ((Bat) pet).spawnToAll();
                    break;

                case "Bee":
                    type = 9;
                    pet = new Bee(p.chunk, nbtMaker(p, type));
                    ((Bee) pet).spawnToAll();
                    break;

                case "Cat":
                    type = 0;
                    pet = new Cat(p.chunk, nbtMaker(p, type));
                    ((Cat) pet).spawnToAll();
                    break;

                case "Chicken":
                    type = 1;
                    pet = new Chicken(p.chunk, nbtMaker(p, type));
                    ((Chicken) pet).spawnToAll();
                    break;

                case "Cod":
                    type = 4;
                    pet = new Cod(p.chunk, nbtMaker(p, type));
                    ((Cod) pet).spawnToAll();
                    break;

                case "Cow":
                    type = 1;
                    pet = new Cow(p.chunk, nbtMaker(p, type));
                    ((Cow) pet).spawnToAll();
                    break;

                case "Dolphin":
                    type = 4;
                    pet = new Dolphin(p.chunk, nbtMaker(p, type));
                    ((Dolphin) pet).spawnToAll();
                    break;

                case "Donkey":
                    type = 1;
                    pet = new Donkey(p.chunk, nbtMaker(p, type));
                    ((Donkey) pet).spawnToAll();
                    break;

                case "Fox":
                    type = 2;
                    pet = new Fox(p.chunk, nbtMaker(p, type));
                    ((Fox) pet).spawnToAll();
                    break;

                case "Horse":
                    type = 1;
                    pet = new Horse(p.chunk, nbtMaker(p, type));
                    ((Horse) pet).spawnToAll();
                    break;

                case "Llama":
                    type = 1;
                    pet = new Llama(p.chunk, nbtMaker(p, type));
                    ((Llama) pet).spawnToAll();
                    break;

                case "Mooshroom":
                    type = 1;
                    pet = new Mooshroom(p.chunk, nbtMaker(p, type));
                    ((Mooshroom) pet).spawnToAll();
                    break;

                case "Mule":
                    type = 1;
                    pet = new Mule(p.chunk, nbtMaker(p, type));
                    ((Mule) pet).spawnToAll();
                    break;

                case "Ocelot":
                    type = 5;
                    pet = new Ocelot(p.chunk, nbtMaker(p, type));
                    ((Ocelot) pet).spawnToAll();
                    break;

                case "Panda":
                    type = 6;
                    pet = new Panda(p.chunk, nbtMaker(p, type));
                    ((Panda) pet).spawnToAll();
                    break;

                case "Parrot":
                    type = 7;
                    pet = new Parrot(p.chunk, nbtMaker(p, type));
                    ((Parrot) pet).spawnToAll();
                    break;

                case "Pig":
                    type = 1;
                    pet = new Pig(p.chunk, nbtMaker(p, type));
                    ((Pig) pet).spawnToAll();
                    break;

                case "Polar Bear":
                    type = 8;
                    pet = new PolarBear(p.chunk, nbtMaker(p, type));
                    ((PolarBear) pet).spawnToAll();
                    break;

                case "Pufferfish":
                    type = 4;
                    pet = new Pufferfish(p.chunk, nbtMaker(p, type));
                    ((Pufferfish) pet).spawnToAll();
                    break;

                case "Rabbit":
                    type = 2;
                    pet = new Rabbit(p.chunk, nbtMaker(p, type));
                    ((Rabbit) pet).spawnToAll();
                    break;

                case "Salmon":
                    type = 4;
                    pet = new Salmon(p.chunk, nbtMaker(p, type));
                    ((Salmon) pet).spawnToAll();
                    break;

                case "Sheep":
                    type = 1;
                    pet = new Sheep(p.chunk, nbtMaker(p, type));
                    ((Sheep) pet).spawnToAll();
                    break;

                case "Squid":
                    type = 4;
                    pet = new Squid(p.chunk, nbtMaker(p, type));
                    ((Squid) pet).spawnToAll();
                    break;

                case "Strider":
                    type = 10;
                    pet = new Strider(p.chunk, nbtMaker(p, type));
                    ((Strider) pet).spawnToAll();
                    break;

                case "Tropical Fish":
                    type = 4;
                    pet = new TropicalFish(p.chunk, nbtMaker(p, type));
                    ((TropicalFish) pet).spawnToAll();
                    break;

                case "Turtle":
                    type = 11;
                    pet = new Turtle(p.chunk, nbtMaker(p, type));
                    ((Turtle) pet).spawnToAll();
                    break;

                case "Wolf":
                    type = 0;
                    pet = new Wolf(p.chunk, nbtMaker(p, type));
                    ((Wolf) pet).spawnToAll();
                    break;

            default: break;
        }


            if (Pets.petName != null && Pets.petName != ""){
                ((Entity)pet).setNameTag(Pets.petName);
                ((Entity)pet).setNameTagVisible(true);
                ((Entity)pet).setNameTagAlwaysVisible(true);
            }
            //p.sendMessage((((Entity)pet).namedTag.getTags().toString()));

            //((Entity) pet).invulnerable = true;
            ((Entity) pet).fireProof = true;
            ((BaseEntity) pet).setFriendly(true);
            petName = null;




        //player.getServer().updatePlayerListData(playerUniqueId, player.getId(), player.getName(), nsStatic);

        //player.hidePlayer(player);
        //player.showPlayer(player);


          });
        return true;
}

    public CompoundTag nbtMaker(Player p, Integer type) {

        CompoundTag nbt = new CompoundTag();
        double x;
        double y;
        double z;
        p.sendMessage(type.toString());
                switch(type) {
                    case 10: //TODO: Type-based coords here
                        x = p.x;
                        y = (p.y + 20); // Different position Strider for testing
                        z = p.z;
                        break;
                    default:
                        x = p.x;
                        y = p.y;
                        z = p.z;
                }
                nbt.putList(new ListTag<>("Pos")
                        .add(new DoubleTag("", x))
                        .add(new DoubleTag("", y))
                        .add(new DoubleTag("", z)))
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
    }}


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









