package nukkitcoders.mobplugin.entities;

import cn.nukkit.level.Location;

import java.util.HashMap;
import java.util.Map;

public class GSPetData {

    public static final Map<String, Location> petLocs;
    static {
        petLocs = new HashMap<>();
        petLocs.put("Bat", new Location(59,39,-95));
        petLocs.put("Bee", new Location(27,45,-92));
        petLocs.put("Cat", new Location(30,44,-61));
        petLocs.put("Chicken", new Location(163,44,-169));
        petLocs.put("Cod", new Location(-5,44,-187));
        petLocs.put("Cow", new Location(160,44,-188));
        petLocs.put("Creeper", new Location(88,28,-128));
        petLocs.put("Dolphin", new Location(58,45,-66));
        petLocs.put("Donkey", new Location(78,44,-174));
        petLocs.put("Fox", new Location(103,44,-153));
        petLocs.put("Horse", new Location(145,44,-135));
        petLocs.put("Husk", new Location(88,28,-128));
        petLocs.put("Llama", new Location(155,44,-84));
        petLocs.put("Mooshroom", new Location(146,44,-45));
        petLocs.put("Mule", new Location(119,44,-164));
        petLocs.put("Ocelot", new Location(132,44,-112));
        petLocs.put("Panda", new Location(46,44,-119));
        petLocs.put("Parrot", new Location(42,44,-41));
        petLocs.put("Pig", new Location(186,44,-192));
        petLocs.put("Polar Bear", new Location(124,33,-76));
        petLocs.put("Pufferfish", new Location(-3,44,-169));
        petLocs.put("Rabbit", new Location(115, 44,-91));
        petLocs.put("Salmon", new Location(-16,44,-171));
        petLocs.put("Sheep", new Location(180,44,-172));
        petLocs.put("Skeleton", new Location(88,28,-128));
        petLocs.put("Squid", new Location(57,46,-158));
        petLocs.put("Strider", new Location(152,44,-68));
        petLocs.put("Tropical Fish", new Location(-19,44,-188));
        petLocs.put("Turtle", new Location(120,44,-142));
        petLocs.put("Wolf", new Location(25,44,-131));
        petLocs.put("Zombie", new Location(88,28,-128));
    }

    public static final Map<String, Integer> petPrices;
    static {
        petPrices = new HashMap<>();
        petPrices.put("Bat", 20);
        petPrices.put("Bee", 5);
        petPrices.put("Cat", 40);
        petPrices.put("Chicken", 15);
        petPrices.put("Cod", 15);
        petPrices.put("Cow", 25);
        petPrices.put("Creeper", 30);
        petPrices.put("Dolphin", 65);
        petPrices.put("Donkey", 20);
        petPrices.put("Fox", 50);
        petPrices.put("Horse", 25);
        petPrices.put("Husk", 30);
        petPrices.put("Llama", 40);
        petPrices.put("Mooshroom", 65);
        petPrices.put("Mule", 20);
        petPrices.put("Ocelot", 50);
        petPrices.put("Panda", 50);
        petPrices.put("Parrot", 20);
        petPrices.put("Pig", 20);
        petPrices.put("Polar Bear", 75);
        petPrices.put("Pufferfish", 20);
        petPrices.put("Rabbit", 20);
        petPrices.put("Salmon", 15);
        petPrices.put("Sheep", 20);
        petPrices.put("Skeleton", 30);
        petPrices.put("Squid", 15);
        petPrices.put("Strider", 75);
        petPrices.put("Tropical Fish", 20);
        petPrices.put("Turtle", 60);
        petPrices.put("Wolf", 40);
        petPrices.put("Zombie", 30);
    }
}
