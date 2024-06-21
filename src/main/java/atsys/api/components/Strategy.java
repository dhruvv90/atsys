package atsys.api.components;

import atsys.api.LifecycleManager;
import atsys.api.core.event.TickEvent;

public interface Strategy extends LifecycleManager {

    void handleTick(TickEvent event);

}
