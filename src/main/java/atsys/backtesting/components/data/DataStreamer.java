package atsys.backtesting.components.data;

/**
 * Primary component for ingesting, processing and streaming data of type T into trading engine.
 */
public interface DataStreamer<T> {

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
