## Overview

This project is a Java-based financial data analysis tool designed to calculate various moving averages on real-time tick data. It provides a flexible and extensible framework for processing financial market data and computing different types of moving averages.

## Features

- Real-time processing of tick data
- Calculation of Simple Moving Average (SMA)
- Calculation of Exponential Moving Average (EMA)
- Calculation of Windowed Average
- Supports both streaming Ticks and Bulks Tick updates for average calculation
- Extensible architecture for adding new calculation methods
- Design with SOLID design principles. 

## Assumptions

- I assume 'Ticks are streaming in' , seeing the analogy of Channel in Go Lang. This can be implemented in few ways in Java, I went to simple design using Observer pattern. 
- Another approach could be using BlockingQueue / Multi threaded design though I didn't feel ask really needed this.
- Anyway , classes are designed in a way that refactoring into other approach won't be tedious.
- When Last 'N' ticks are missing, calculators will use available ticks to calculate the average. 
- BigDecimal.ZERO is returned for cases where average can't be calculated. This could be changed to return null or Double.Max if needed.
- Prices are assumed to be positive, thought code will work for negatives too, it's just that unit tests and default values are written with this assumption.


### Build and Run / IDE Setup
This project can be imported into IDEs such as IntelliJ IDEA or Eclipse:
1. Open your IDE
2. Select "Import Project" or "Open Project"
3. Navigate to the project directory and select the `pom.xml` file
4. Follow the IDE's import wizard to complete the setup
5. To build using maven use command 'mvn clean install'
6. To Run unit tests, via IDE, simply go to test file , right click and run.
