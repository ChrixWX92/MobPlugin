package nukkitcoders.mobplugin.entities.animal.swimming;

import cn.nukkit.level.Location;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class Axolotl extends Fish {

    public static final int NETWORK_ID = 130;

    public Axolotl(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Location getSpawnLoc() {
        return null;
    }

    @Override
    public void initEntity() {
        super.initEntity();

        this.setMaxHealth(14);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 1.3f;
    }

    @Override
    public float getHeight() {
        return 0.6f;
    }

    @Override
    int getBucketMeta() {
        return 12;
    }
}
