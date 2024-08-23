package atsys.backtesting.engine.components.order;

enum OrderStatus {
    CREATED,
    STAGED, // about to be placed
    COMPLETED,
    CANCELLED,
    REJECTED
}
