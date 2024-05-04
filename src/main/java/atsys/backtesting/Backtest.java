package atsys.backtesting;

import java.time.LocalDateTime;

public class Backtest {
    private BtParameters backtestingParams;
    private String name;
    private LocalDateTime createdAt;

    public Backtest(String name, BtParameters backtestingParams) {
        this.name = name;
        this.backtestingParams = backtestingParams;
        this.createdAt = LocalDateTime.now();
    }

    public void run(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Backtest Name : "+ this.name +" created on : "+ this.createdAt;
    }
}
