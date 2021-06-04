import java.util.ArrayList;
//Create your battery and the rest is taken care of automatically.
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
    //Automates the _isBasement boolean
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
        //Column Constructor & servedFloor assigner & call button creator and assigner/
        var floor = 1;
        for (Integer i = 0; i < _amountOfColumns; i++)
        {
            Column column = new Column(i+1, _amountOfElevatorPerColumn, 1, trueFalseinator(i));
            columnsList.add(column);
            //For every block of servedFloors, add the designated floors to the servedFloors list.
            if(i > 0)
            { //fpc stands for floors per column, in our scenario its 1-20, 21-40, 41-60, but it's dynamic.
                var fpc = java.lang.Math.ceil((_amountOfFloors / (_amountOfColumns - 1))); // Minus one for the column allocated for the basement floors.
                for(int j = 0; j < fpc; j++)
                {
                    if(floor <= _amountOfFloors)
                        {
                            columnsList.get(i).servedFloors.add(floor);
                            CallButton downCallButtonCreator = new CallButton(floor, floor, "down");
                            columnsList.get(i).callButtonsList.add(downCallButtonCreator);
                            floor++;
                        }
                }
            }
            //Add lobby as a served floor for each column.
            if (i > 1)
            {
                columnsList.get(i).servedFloors.add(1);
            }
            //Add basement served floors.       
            if (i == 0)
            {
                for (Integer x = 1; x <= _amountOfBasements; x++)
                {
                    columnsList.get(0).servedFloors.add(-x);
                    CallButton upCallButtonCreator = new CallButton(x, -6 + x, "up");
                    columnsList.get(0).callButtonsList.add(upCallButtonCreator);
                }
                columnsList.get(0).servedFloors.add(1);
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

        //Logic is swapped if the elevator is below or above lobby.
        if(_requestedFloor > 1)
        {
            for(Elevator elevator : this.bestColumn.elevatorsList)
            { //Elevator selector
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
            } //Elevator mover
            while(bestElevator.currentFloor > 1){
                bestElevator.status = "moving";
                bestElevator.currentFloor--;
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor > _requestedFloor){
                bestElevator.currentFloor--;
                bestElevator.status = "moving";
                bestElevator.door.status = "closed";
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor < _requestedFloor){
                bestElevator.currentFloor++;
                bestElevator.status = "moving";
                bestElevator.door.status = "closed";
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor == _requestedFloor){
                bestElevator.status = "idle";
                bestElevator.door.status = "open";
                System.out.println("*DING* Elevator has arrived at floor " + _requestedFloor + ".");
                System.out.println("Please exit now.");
            break;
            }
        }
        if (_requestedFloor < 1)
        {   //Elevator selector
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
            //Moves bestElevator
            while(bestElevator.currentFloor > _requestedFloor){
                bestElevator.currentFloor--;
                bestElevator.status = "moving";
                bestElevator.door.status = "closed";
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor < _requestedFloor){
                bestElevator.currentFloor++;
                bestElevator.status = "moving";
                bestElevator.door.status = "closed";
                System.out.println("Elevator is on floor " + bestElevator.currentFloor);
            }
            while(bestElevator.currentFloor == _requestedFloor){
                bestElevator.status = "idle";
                bestElevator.door.status = "open";
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
    public Elevator bestestElevator;
    public int bestestScore;
    public int referencestGap;
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
        //Elevator creator
        for (Integer i = 0; i < _amountOfElevators; i++)
        {
            Elevator elevator = new Elevator(i+1);
            elevatorsList.add(elevator);
        }
    }
    public Elevator requestElevator(Integer _requestedFloor, String _direction)
    { //Elevator selector
        bestestElevator = null;
        bestestScore = 5;
        referencestGap = 100000;
        for(Elevator elevator : this.elevatorsList)
        {
            if (_requestedFloor < 1) //Again, basement logic
            {
                if (elevator.currentFloor == _requestedFloor && elevator.direction == "up")
                {
                    bestestElevator = elevator;
                    bestestScore = 1;
                    referencestGap = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
                if (elevator.floorRequestsList.contains(1) && elevator.direction == "up" && elevator.currentFloor < _requestedFloor)
                {
                    bestestElevator = elevator;
                    bestestScore = 2;
                    referencestGap = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
                if (elevator.status == "idle")
                {
                    bestestElevator = elevator;
                    bestestScore = 3;
                    referencestGap = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
             }
             if (_requestedFloor > 1)
             {
                if (elevator.currentFloor == _requestedFloor && elevator.direction == "down")
                {
                    bestestElevator = elevator;
                    bestestScore = 1;
                    referencestGap = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
                if (elevator.floorRequestsList.contains(1) && elevator.direction == "down" && elevator.currentFloor > _requestedFloor && java.lang.Math.abs(elevator.currentFloor - _requestedFloor) < referencestGap)
                {
                    bestestElevator = elevator;
                    bestestScore = 2;
                    referencestGap = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
                if (elevator.status == "idle")
                {
                    bestestElevator = elevator;
                    bestestScore = 3;
                    referencestGap = java.lang.Math.abs(elevator.currentFloor - _requestedFloor);
                }
             }
        }
        while(bestestElevator.currentFloor > 1)
        {
            while(bestestElevator.currentFloor == _requestedFloor)
            {
                bestestElevator.door.status = "open";
                System.out.println("*DING* Elevator doors are open, please enter");
                break;
            }
            bestestElevator.currentFloor--;
            bestestElevator.status = "moving";
            bestestElevator.door.status = "closed";
            System.out.println("Elevator is on floor " + bestestElevator.currentFloor);
        }
        while(bestestElevator.currentFloor < -1)
        {
            bestestElevator.currentFloor++;
            bestestElevator.status = "moving";
            bestestElevator.door.status = "closed";
            System.out.println("Elevator is on floor " + bestestElevator.currentFloor);
        }
        while(bestestElevator.currentFloor == -1)
        {
            bestestElevator.currentFloor++;
        }
        while(bestestElevator.currentFloor == 0)
        {
            bestestElevator.currentFloor++;
        }
        while(bestestElevator.currentFloor == 1)
        {
            bestestElevator.status = "idle";
            bestestElevator.door.status = "open";
            System.out.println("*DING* Elevator has arrived at Lobby.");
            break;
        }

    return bestestElevator; 
    }
}
class Elevator {
    public int ID;
    public String status;
    public int currentFloor;
    public String direction;
    public Door door;
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
        Battery battery1 = new Battery(1, 4, 60, 6, 5);
        battery1.findBestColumn(20);
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
        
        //Scenario 2
        // Battery battery2 = new Battery(1, 4, 60, 6, 5);
        // battery2.findBestColumn(20);
        // battery2.columnsList.get(2).elevatorsList.get(0).currentFloor = 1;
        // battery2.columnsList.get(2).elevatorsList.get(1).currentFloor = 23;
        // battery2.columnsList.get(2).elevatorsList.get(2).currentFloor = 33;
        // battery2.columnsList.get(2).elevatorsList.get(3).currentFloor = 40;
        // battery2.columnsList.get(2).elevatorsList.get(4).currentFloor = 39;
        // battery2.columnsList.get(2).elevatorsList.get(0).floorRequestsList.add(21);
        // battery2.columnsList.get(2).elevatorsList.get(1).floorRequestsList.add(28);
        // battery2.columnsList.get(2).elevatorsList.get(2).floorRequestsList.add(1);
        // battery2.columnsList.get(2).elevatorsList.get(3).floorRequestsList.add(24);
        // battery2.columnsList.get(2).elevatorsList.get(4).floorRequestsList.add(39);
        // battery2.columnsList.get(2).elevatorsList.get(0).status = "stopped";
        // battery2.columnsList.get(2).elevatorsList.get(1).status = "moving";
        // battery2.columnsList.get(2).elevatorsList.get(2).status = "moving";
        // battery2.columnsList.get(2).elevatorsList.get(3).status = "moving";
        // battery2.columnsList.get(2).elevatorsList.get(4).status = "moving";
        // battery2.columnsList.get(2).elevatorsList.get(0).direction = null;
        // battery2.columnsList.get(2).elevatorsList.get(1).direction = "up";
        // battery2.columnsList.get(2).elevatorsList.get(2).direction = "down";
        // battery2.columnsList.get(2).elevatorsList.get(3).direction = "down";
        // battery2.columnsList.get(2).elevatorsList.get(4).direction = "down";
        // battery2.assignElevator(36, "up");
        // System.out.println("Elevator C1 is below");
        // System.out.println(battery2.bestElevator.ID);

        //Scenario 3
        // Battery battery3 = new Battery(1, 4, 60, 6, 5);
        // battery3.columnsList.get(3).elevatorsList.get(0).currentFloor = 58;
        // battery3.columnsList.get(3).elevatorsList.get(1).currentFloor = 50;
        // battery3.columnsList.get(3).elevatorsList.get(2).currentFloor = 46;
        // battery3.columnsList.get(3).elevatorsList.get(3).currentFloor = 1;
        // battery3.columnsList.get(3).elevatorsList.get(4).currentFloor = 60;
        // battery3.columnsList.get(3).elevatorsList.get(0).status = "moving";
        // battery3.columnsList.get(3).elevatorsList.get(1).status = "moving";
        // battery3.columnsList.get(3).elevatorsList.get(2).status = "moving";
        // battery3.columnsList.get(3).elevatorsList.get(3).status = "moving";
        // battery3.columnsList.get(3).elevatorsList.get(4).status = "moving";
        // battery3.columnsList.get(3).elevatorsList.get(0).floorRequestsList.add(1);
        // battery3.columnsList.get(3).elevatorsList.get(1).floorRequestsList.add(60);
        // battery3.columnsList.get(3).elevatorsList.get(2).floorRequestsList.add(58);
        // battery3.columnsList.get(3).elevatorsList.get(3).floorRequestsList.add(54);
        // battery3.columnsList.get(3).elevatorsList.get(4).floorRequestsList.add(1);
        // battery3.columnsList.get(3).elevatorsList.get(0).direction = "down";
        // battery3.columnsList.get(3).elevatorsList.get(1).direction = "up";
        // battery3.columnsList.get(3).elevatorsList.get(2).direction = "up";
        // battery3.columnsList.get(3).elevatorsList.get(3).direction = "up";
        // battery3.columnsList.get(3).elevatorsList.get(4).direction = "down";
        // battery3.columnsList.get(3).requestElevator(54, "down");
        // System.out.println("Elevator D1 is below");
        // System.out.println(battery3.columnsList.get(3).bestestElevator.ID);
        // System.out.println("Elevator D1 currentFloor below");
        // System.out.println(battery3.columnsList.get(3).bestestElevator.currentFloor);

        //Scenario 4
        // Battery battery4 = new Battery(1, 4, 60, 6, 5);
        // battery4.columnsList.get(0).elevatorsList.get(0).currentFloor = -4;
        // battery4.columnsList.get(0).elevatorsList.get(1).currentFloor = 1;
        // battery4.columnsList.get(0).elevatorsList.get(2).currentFloor = -3;
        // battery4.columnsList.get(0).elevatorsList.get(3).currentFloor = -6;
        // battery4.columnsList.get(0).elevatorsList.get(4).currentFloor = -1;
        // battery4.columnsList.get(0).elevatorsList.get(0).status = "idle";
        // battery4.columnsList.get(0).elevatorsList.get(1).status = "idle";
        // battery4.columnsList.get(0).elevatorsList.get(2).status = "moving";
        // battery4.columnsList.get(0).elevatorsList.get(3).status = "moving";
        // battery4.columnsList.get(0).elevatorsList.get(4).status = "moving";
        // battery4.columnsList.get(0).elevatorsList.get(2).floorRequestsList.add(-5);
        // battery4.columnsList.get(0).elevatorsList.get(3).floorRequestsList.add(1);
        // battery4.columnsList.get(0).elevatorsList.get(4).floorRequestsList.add(-6);
        // battery4.columnsList.get(0).elevatorsList.get(0).direction = null;
        // battery4.columnsList.get(0).elevatorsList.get(1).direction = null;
        // battery4.columnsList.get(0).elevatorsList.get(2).direction = "down";
        // battery4.columnsList.get(0).elevatorsList.get(3).direction = "up";
        // battery4.columnsList.get(0).elevatorsList.get(4).direction = "down";
        // battery4.columnsList.get(0).requestElevator(-3, "up");
        // System.out.println("Elevator A4 is below");
        // System.out.println(battery4.columnsList.get(0).bestestElevator.ID);
        // System.out.println("Elevator A4 currentFloor below");
        // System.out.println(battery4.columnsList.get(0).bestestElevator.currentFloor);
        
    }
}