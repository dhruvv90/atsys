package atsys.api;

/**
 * Represents runnable classes which do actions at starting and at completion
 */
public interface LifecycleManager {
    void onInit();
    void onComplete();
}
