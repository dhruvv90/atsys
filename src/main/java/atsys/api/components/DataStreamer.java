package atsys.api.components;

import atsys.api.LifecycleManager;

/**
 * Primary component for ingesting, processing and streaming data into trading engine.
 */
public interface DataStreamer extends LifecycleManager {

    /**
     * Used to read next chunk of data
     */
    void readData();

    /**
     * Checks if next chunk is available for streaming
     * @return false when no chunk available
     */
    boolean hasNext();
}
