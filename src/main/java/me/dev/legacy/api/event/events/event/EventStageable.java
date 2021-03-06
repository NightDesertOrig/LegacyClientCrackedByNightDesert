package me.dev.legacy.api.event.events.event;

public class EventStageable {
    private EventStageable.EventStage stage;

    public EventStageable() {
    }

    public EventStageable(EventStageable.EventStage stage) {
        this.stage = stage;
    }

    public EventStageable.EventStage getStage() {
        return this.stage;
    }

    public void setStage(EventStageable.EventStage stage) {
        this.stage = stage;
    }

    public static enum EventStage {
        PRE,
        POST;
    }
}
