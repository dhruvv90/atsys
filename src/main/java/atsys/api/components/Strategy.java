package atsys.api.components;

import atsys.api.LifecycleManager;
import atsys.api.event.EventListener;
import atsys.api.event.TickDataEvent;

public interface Strategy extends LifecycleManager, EventListener<TickDataEvent> {
}
