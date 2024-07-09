A Simple Event-Driven Backtesting System for any financial asset
===

opinionated, basic event driven backtesting system written in pure Java. It can be used for Stocks, FX, crypto.
Some notes on architecture and functionality:

* Single threaded, Scalable design and capable to run multiple backtests with plug and play components (Type of Asset, Market data, strategies, portfolioManagers etc...)
* Being purely event driven, it avoids Look-ahead bias
* Flexibility in changing data-flow strategy at any stage due to dependency on events + plug and play architecture
* Once the backtesting engine is initialized , it uses an EventDrivenEngineContext having info about all runs/sessions. Each backtest session uses BacktestingContext, within the main engine context, to process/persist particular session
* EventDrivenEngine uses re-usable components for running multiple runs/sessions and does cleanup after each run.
* Only dummy Implementations and no integrations with any data source or exchange yet


Steps to run : Import in Intellij and run "BasicTest" config.


Contributions and ideas are welcome
