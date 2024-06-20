package atsys.api.components;

import atsys.api.LifecycleManager;
import atsys.api.model.TickData;

public interface Strategy extends LifecycleManager {

    <T extends TickData> void handleData(T data);

//    void handleData(BaseTickData tickData);
}
