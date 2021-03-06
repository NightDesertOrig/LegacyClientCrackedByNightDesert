package me.dev.legacy.api.event.events.move;

import me.dev.legacy.api.event.EventStage;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class StepEvent extends EventStage {
    private final Entity entity;
    private float height;

    public StepEvent(int stage, Entity entity) {
        super(stage);
        this.entity = entity;
        this.height = entity.field_70138_W;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
