package atsys.backtesting.engine.components.order;

enum OrderStatus {
    CREATED_INTERNAL,
    OPEN, // placed at exchance but not executed yet
    STAGED, // just before sending to exchange
    COMPLETED_PARTIAL, // partially executed
    COMPLETED, // full order executed
    CANCELLED,
    REJECTED
}
