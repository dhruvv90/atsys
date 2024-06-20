package atsys.api.components;

import atsys.api.LifecycleManager;
import atsys.api.model.TickData;

/**
 * Primary component for ingesting, processing and streaming data of type T into trading engine.
 */
public interface DataStreamer<T extends TickData> extends LifecycleManager {

    /**
     * Used to read next chunk of data
     */
    T readData();

    /**
     * Checks if next chunk is available for streaming
     * @return false when no chunk available
     */
    boolean hasNext();
}
