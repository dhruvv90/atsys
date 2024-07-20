package atsys.backtesting.engine.events.listeners;

import atsys.backtesting.engine.events.KillEvent;

public class KillEventListener implements EventListener<KillEvent> {

    @Override
    public void onEvent(KillEvent event) {
        System.exit(0);
    }
}
