# Rocket-Elevators-Java-Controller
> Week 2 project for Rocket Elevators. Subsidiary of Codeboxx Technologies
## Table of Contents
* [General](#general)
* [Technologies](#technologies)
* [Setup](#setup)
* [Code Examples](#Code-Examples)

## General
This project features a simulated elevator sender and retriever algorithm as well as a column selector for a commercial setting. The project operates in the command terminal.
It has three methods, two in the Battery class; assignElevator & findBestColumn, and one in the Column class; requestElevator.

## Technologies
Project was created in three languages:
* Java version 14.0.1
* C# .NET Core 3.0
* Golang version 1.16.4

## Setup
To run this project, clone it through your Command Line Interface locally by entering:
```
gh repo clone PerrySawatzky/Rocket-Elevators-Java-Controller

Java is required, [download here](https://www.java.com/en/download/manual.jsp)

## Code Examples
This repo includes 4 test scenarios at the bottom of the file, starting at line 382.

However only 3 lines are absolutely critical. The first creates the Battery, the two subsequent create the Battery methods, and the last two:
```
Battery battery1 = new Battery(1, 4, 60, 6, 5);
battery1.findBestColumn(20);
battery1.assignElevator(20, "up");
```
The five parameters for Battery are id, amount of columns, amount of floors, amount of basements, and amount of elevators per column, respectfully.

We assign a variable, in this case 'battery1' to the findBestColumn method in order for the same column to be assigned when completing the subsequent assignElevator method. 

The only parameter in the findBestColumn method is the floor number.

The two parameter in the assignElevator method are the floor the user requested and the direction.

The requestElevator method is for a different type of scenario but is executed the same way as assignElevator. The test scenario for this method starts on line 437.

In order to set up a complex scenario, you can set certain properties of the elevators before hand. This is what that would look like:
```
battery1.columnsList.get(1).elevatorsList.get(0).currentFloor = 20;
battery1.columnsList.get(1).elevatorsList.get(1).currentFloor = 3;
battery1.columnsList.get(1).elevatorsList.get(2).currentFloor = 13;
battery1.columnsList.get(1).elevatorsList.get(3).currentFloor = 15;
battery1.columnsList.get(1).elevatorsList.get(4).currentFloor = 6;
battery1.columnsList.get(1).elevatorsList.get(0).floorRequestsList.add(5);
battery1.columnsList.get(1).elevatorsList.get(1).floorRequestsList.add(15);
battery1.columnsList.get(1).elevatorsList.get(2).floorRequestsList.add(1);
battery1.columnsList.get(1).elevatorsList.get(3).floorRequestsList.add(2);
battery1.columnsList.get(1).elevatorsList.get(4).floorRequestsList.add(1);
battery1.columnsList.get(1).elevatorsList.get(0).status = "moving";
battery1.columnsList.get(1).elevatorsList.get(1).status = "moving";
battery1.columnsList.get(1).elevatorsList.get(2).status = "moving";
battery1.columnsList.get(1).elevatorsList.get(3).status = "moving";
battery1.columnsList.get(1).elevatorsList.get(4).status = "moving";
battery1.columnsList.get(1).elevatorsList.get(0).direction = "down";
battery1.columnsList.get(1).elevatorsList.get(1).direction = "up";
battery1.columnsList.get(1).elevatorsList.get(2).direction = "down";
battery1.columnsList.get(1).elevatorsList.get(3).direction = "down";
battery1.columnsList.get(1).elevatorsList.get(4).direction = "down";
battery1.assignElevator(20, "up");
System.out.println("Elevator B5 is below");
System.out.println(battery1.bestElevator.ID);
```
To run the algorithms in the command line terminal, simply type:
```
javac Program.javac
```
hit enter, then type:
```
java Program
```
and hit enter.