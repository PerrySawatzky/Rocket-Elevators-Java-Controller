import java.util.ArrayList;
class Battery
{
    public int ID;
    public String status;
    public Column bestColumn;
    public Elevator bestElevator;
    public int bestScore;
    public int referenceGap;
    public ArrayList<Column> columnsList;
    public ArrayList<FloorRequestButton> floorRequestButtonsList;
    public Boolean trueFalseinator(Integer i)
    {
        if (i == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Battery(int _id, int _amountOfColumns, int _amountOfFloors, int _amountOfBasements, int _amountOfElevatorPerColumn)
    {
        this.ID = _id;
        this.status = "charged";
        columnsList = new ArrayList<Column>();
        floorRequestButtonsList = new ArrayList<FloorRequestButton>();
        //Column Constructor
        for (Integer i = 0; i < _amountOfElevatorPerColumn; i++)
        {
            Column column = new Column(i, _amountOfElevatorPerColumn, 1, trueFalseinator(i));
            columnsList.add(column);
            if (i == 0)
            {
                for (Integer x = 1; x <= _amountOfBasements; x++)
                {
                    columnsList.get(0).servedFloors.add(-x);
                }
                columnsList.get(0).servedFloors.add(1);
            }
            if (i == 1)
            {
                for (Integer x = 0; x < 20; x++)
                {
                    columnsList.get(1).servedFloors.add(x + 1);
                }
                columnsList.get(1).servedFloors.add(1);
            } 
            if (i == 2)
            {
                for (Integer x = 20; x < 40; x++)
                {
                    columnsList.get(2).servedFloors.add(x + 1);
                }
                columnsList.get(2).servedFloors.add(1);
            }
            if (i == 3)
            {
                for (Integer x = 40; x < 60; x++)
                {
                    columnsList.get(3).servedFloors.add(x + 1);
                }
                columnsList.get(3).servedFloors.add(1);
            }
        }//Lobby only up and down buttons
        for (Integer i = 0; i < 1; i++)
            {
                var upFloorRequestButtonCreator = new FloorRequestButton(i, i + 1, "up");
                floorRequestButtonsList.add(upFloorRequestButtonCreator);
            }
        for (Integer i = 0; i < 1; i++)
            {
                var downFloorRequestButtonCreator = new FloorRequestButton(i, i + 1, "down");
                floorRequestButtonsList.add(downFloorRequestButtonCreator);
            }
    }
    public Column findBestColumn(Integer _requestedFloor)
    {
        for (Column column : this.columnsList)
        {
            if (column.servedFloors.contains(_requestedFloor))
            {
                this.bestColumn = column;
            }
        }
        return this.bestColumn;
    }
    public Elevator assignElevator(Integer _requestedFloor, String _direction)
    {
        bestElevator = null;
        bestColumn = findBestColumn(_requestedFloor);
        bestScore = 5;
        referenceGap = 10000;

        if(_requestedFloor > 1)
        {
            for(Elevator elevator : this.bestColumn.elevatorsList)
            {
                if (elevator.currentFloor == 1 && elevator.status == "stopped")
                {
                    bestElevator = elevator;
                    bestScore = 1;
                    referenceGap = java.lang.Math.abs(elevator.currentFloor - 1);
                }
                if (elevator.currentFloor == 1 && elevator.status == "idle")
                {
                    bestElevator = elevator;
                    bestScore = 2;
                    referenceGap = java.lang.Math.abs(elevator.currentFloor - 1);

                }
                if (elevator.floorRequestsList.contains(1) && java.lang.Math.abs(elevator.currentFloor - 1) < referenceGap && elevator.direction == "down")
                {
                    bestElevator = elevator;
                    bestScore = 3;
                    referenceGap = java.lang.Math.abs(elevator.currentFloor - 1);
                }
                if (elevator.status == "idle")
                {
                    bestElevator = elevator;
                    bestScore = 4;
                    referenceGap = java.lang.Math.abs(elevator.currentFloor - 1);
                }
            }
            while(bestElevator.currentFloor > 1){
                bestElevator.status = "moving";
                bestElevator.currentFloor--;
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor > _requestedFloor){
                bestElevator.currentFloor--;
                bestElevator.status = "moving";
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor < _requestedFloor){
                bestElevator.currentFloor++;
                bestElevator.status = "moving";
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor == _requestedFloor){
                bestElevator.status = "idle";
                System.out.println("*DING* Elevator has arrived at floor " + _requestedFloor + ".");
                System.out.println("Please exit now.");
            break;
            }
        }
        if (_requestedFloor < 1)
        {
            for(Elevator elevator : this.bestColumn.elevatorsList)
            {
                if (elevator.currentFloor == 1 && elevator.status == "stopped")
                {
                    bestElevator = elevator;
                    bestScore = 1;
                    referenceGap = java.lang.Math.abs(elevator.currentFloor - 1);
                }
                if (elevator.currentFloor == 1 && elevator.status == "idle")
                {
                    bestElevator = elevator;
                    bestScore = 2;
                    referenceGap = java.lang.Math.abs(elevator.currentFloor - 1);

                }
                if (elevator.floorRequestsList.contains(1) && java.lang.Math.abs(elevator.currentFloor - 1) < referenceGap && elevator.direction == "up")
                {
                    bestElevator = elevator;
                    bestScore = 3;
                    referenceGap = java.lang.Math.abs(elevator.currentFloor - 1);
                }
                if (elevator.status == "idle")
                {
                    bestElevator = elevator;
                    bestScore = 4;
                    referenceGap = java.lang.Math.abs(elevator.currentFloor - 1);
                }
            }
            while(bestElevator.currentFloor > _requestedFloor){
                bestElevator.currentFloor--;
                bestElevator.status = "moving";
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor < _requestedFloor){
                bestElevator.currentFloor++;
                bestElevator.status = "moving";
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor == _requestedFloor){
                bestElevator.status = "idle";
                System.out.println("*DING* Elevator has arrived at floor " + _requestedFloor + ".");
                System.out.println("Please exit now.");
            break;
            }
        }
        return bestElevator;
    }
}

class Column
{
    public int ID;
    public String status;
    public Elevator bestElevator1;
    public int bestScore1;
    public int referenceGap1;
    public ArrayList<Integer> servedFloors;
    public Boolean isBasement;
    public ArrayList<Elevator> elevatorsList;
    public ArrayList<CallButton> callButtonsList;
    public Column(int _id, int _amountOfElevators, int _servedFloors, boolean _isBasement)
    {
        this.ID = _id;
        this.status = "built";
        servedFloors = new ArrayList<Integer>();
        this.isBasement = _isBasement;
        elevatorsList = new ArrayList<Elevator>();
        callButtonsList = new ArrayList<CallButton>();
        for (Integer i = 0; i < _amountOfElevators; i++)
        {
            Elevator elevator = new Elevator(i);
            elevatorsList.add(elevator);
        }
        if (_isBasement == true)
        {
            for(Integer i = 0; i < 6; i++)
            {
                CallButton upCallButtonCreator = new CallButton(i, -6 -i, "up");
                callButtonsList.add(upCallButtonCreator);
            }
        }
        if (_isBasement == false)
        {
            for (Integer i = 2; i <= 60 ; i++)
            {
                CallButton downCallButtonCreator = new CallButton(i, i, "down");
                callButtonsList.add(downCallButtonCreator);
            }
        }
    }
    public Elevator requestElevator(Integer _requestedFloor, String _direction)
    {
        bestElevator1 = null;
        bestScore1 = 5;
        referenceGap1 = 100000;
        for(Elevator elevator : this.elevatorsList)
        {
            if (_requestedFloor < 1)
            {
                if (elevator.currentFloor == _requestedFloor && elevator.direction == "up")
                {
                    bestElevator1 = elevator;
                    bestScore1 = 1;
                    referenceGap1 = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
                if (elevator.floorRequestsList.contains(1) && elevator.direction == "up" && elevator.currentFloor < _requestedFloor)
                {
                    bestElevator1 = elevator;
                    bestScore1 = 2;
                    referenceGap1 = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
                if (elevator.status == "idle")
                {
                    bestElevator1 = elevator;
                    bestScore1 = 3;
                    referenceGap1 = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
             }
             if (_requestedFloor > 1)
             {
                if (elevator.currentFloor == _requestedFloor && elevator.direction == "down")
                {
                    bestElevator1 = elevator;
                    bestScore1 = 1;
                    referenceGap1 = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
                if (elevator.floorRequestsList.contains(1) && elevator.direction == "down" && elevator.currentFloor > _requestedFloor)
                {
                    bestElevator1 = elevator;
                    bestScore1 = 2;
                    referenceGap1 = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
                if (elevator.status == "idle")
                {
                    bestElevator1 = elevator;
                    bestScore1 = 3;
                    referenceGap1 = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
             }
        }
        while(bestElevator1.currentFloor > 1)
        {
            while(bestElevator1.currentFloor == _requestedFloor)
            {
                System.out.println("*DING* Elevator doors are open, please enter");
                break;
            }
            bestElevator1.currentFloor--;
            bestElevator1.status = "moving";
            System.out.println("Elevator is on floor " + bestElevator1.currentFloor);
        }
        while(bestElevator1.currentFloor < -1)
        {
            bestElevator1.currentFloor++;
            bestElevator1.status = "moving";
            System.out.println("Elevator is on floor " + bestElevator1.currentFloor);
        }
        while(bestElevator1.currentFloor == -1)
        {
            bestElevator1.currentFloor++;
        }
        while(bestElevator1.currentFloor == 0)
        {
            bestElevator1.currentFloor++;
        }
        while(bestElevator1.currentFloor == 1)
        {
            bestElevator1.status = "idle";
            System.out.println("*DING* Elevator has arrived at Lobby.");
            break;
        }

    return bestElevator1; 
    }
}
class Elevator {
    public int ID;
    public String status;
    public int currentFloor;
    public String direction;
    public Object door;
    public ArrayList<Integer> floorRequestsList;
    public ArrayList<Integer> completedRequestsList;
    public Elevator (int _id)
    {
        this.ID = _id;
        this.status = "idle";
        this.currentFloor = 1;
        this.direction = null;
        this.door = new Door(_id);
        floorRequestsList = new ArrayList<Integer>();
        completedRequestsList = new ArrayList<Integer>();
    }
}
class CallButton
{
    public int ID;
    public String status;
    public int floor;
    public String direction;
    public CallButton(int _id, int _floor, String _direction)
    {
        this.ID = _id;
        this.status = "off";
        this.floor = _floor;
        this.direction = _direction;
    }
}
class FloorRequestButton
    {
        public int ID;
        public String status;
        public int floor;
        public String direction;
        public FloorRequestButton(int _id, int _floor, String _direction)
        {
            this.ID = _id;
            this.status = "off";
            this.floor = _floor;
            this.direction = _direction;
        }
    }
class Door 
{
    public int ID;
    public String status;
    public Door(int _id) {
        this.ID = _id;
        this.status = "closed";
    }
}
public class Program
{
    public static void main(String [] args){
        //Scenario 1
        // Battery battery1 = new Battery(1, 4, 60, 6, 5);
        // battery1.findBestColumn(20);
        // battery1.columnsList.get(1).elevatorsList.get(0).currentFloor = 20;
        // battery1.columnsList.get(1).elevatorsList.get(1).currentFloor = 3;
        // battery1.columnsList.get(1).elevatorsList.get(2).currentFloor = 13;
        // battery1.columnsList.get(1).elevatorsList.get(3).currentFloor = 15;
        // battery1.columnsList.get(1).elevatorsList.get(4).currentFloor = 6;
        // battery1.columnsList.get(1).elevatorsList.get(0).floorRequestsList.add(5);
        // battery1.columnsList.get(1).elevatorsList.get(1).floorRequestsList.add(15);
        // battery1.columnsList.get(1).elevatorsList.get(2).floorRequestsList.add(1);
        // battery1.columnsList.get(1).elevatorsList.get(3).floorRequestsList.add(2);
        // battery1.columnsList.get(1).elevatorsList.get(4).floorRequestsList.add(1);
        // battery1.columnsList.get(1).elevatorsList.get(0).status = "moving";
        // battery1.columnsList.get(1).elevatorsList.get(1).status = "moving";
        // battery1.columnsList.get(1).elevatorsList.get(2).status = "moving";
        // battery1.columnsList.get(1).elevatorsList.get(3).status = "moving";
        // battery1.columnsList.get(1).elevatorsList.get(4).status = "moving";
        // battery1.columnsList.get(1).elevatorsList.get(0).direction = "down";
        // battery1.columnsList.get(1).elevatorsList.get(1).direction = "up";
        // battery1.columnsList.get(1).elevatorsList.get(2).direction = "down";
        // battery1.columnsList.get(1).elevatorsList.get(3).direction = "down";
        // battery1.columnsList.get(1).elevatorsList.get(4).direction = "down";
        // battery1.assignElevator(20, "up");
        // System.out.println("Elevator B5 is aka array[4] below");
        // System.out.println(battery1.bestElevator.ID);
        
        //Scenario 2
        Battery battery2 = new Battery(1, 4, 60, 6, 5);
        battery2.findBestColumn(20);
        battery2.columnsList.get(2).elevatorsList.get(0).currentFloor = 1;
        battery2.columnsList.get(2).elevatorsList.get(1).currentFloor = 23;
        battery2.columnsList.get(2).elevatorsList.get(2).currentFloor = 33;
        battery2.columnsList.get(2).elevatorsList.get(3).currentFloor = 40;
        battery2.columnsList.get(2).elevatorsList.get(4).currentFloor = 39;
        battery2.columnsList.get(2).elevatorsList.get(0).floorRequestsList.add(21);
        battery2.columnsList.get(2).elevatorsList.get(1).floorRequestsList.add(28);
        battery2.columnsList.get(2).elevatorsList.get(2).floorRequestsList.add(1);
        battery2.columnsList.get(2).elevatorsList.get(3).floorRequestsList.add(24);
        battery2.columnsList.get(2).elevatorsList.get(4).floorRequestsList.add(39);
        battery2.columnsList.get(2).elevatorsList.get(0).status = "stopped";
        battery2.columnsList.get(2).elevatorsList.get(1).status = "moving";
        battery2.columnsList.get(2).elevatorsList.get(2).status = "moving";
        battery2.columnsList.get(2).elevatorsList.get(3).status = "moving";
        battery2.columnsList.get(2).elevatorsList.get(4).status = "moving";
        battery2.columnsList.get(2).elevatorsList.get(0).direction = null;
        battery2.columnsList.get(2).elevatorsList.get(1).direction = "up";
        battery2.columnsList.get(2).elevatorsList.get(2).direction = "down";
        battery2.columnsList.get(2).elevatorsList.get(3).direction = "down";
        battery2.columnsList.get(2).elevatorsList.get(4).direction = "down";
        battery2.assignElevator(36, "up");
        System.out.println("Elevator C1 is aka array[0] below");
        System.out.println(battery2.bestElevator.ID);
        
        // Column column1 = new Column(1, 5, 60, false);
        // System.out.println(column1.elevatorsList.size());
    }
}