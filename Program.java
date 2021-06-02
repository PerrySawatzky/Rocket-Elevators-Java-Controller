import java.util.ArrayList;

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
    public Column(int _id, int _amountOfElevators, int _servedFloors, boolean _isBasement){
        this.ID = _id;
        this.status = "built";
        servedFloors = new ArrayList<Integer>();
        this.isBasement = _isBasement;
        elevatorsList = new ArrayList<Elevator>();
        callButtonsList = new ArrayList<CallButton>();
        for (Integer i = 0; i < _amountOfElevators; i++){
            Elevator elevator = new Elevator(i);
            elevatorsList.add(elevator);
        }
        if (_isBasement == true){
            for(Integer i = 0; i < 6; i++){
                CallButton upCallButtonCreator = new CallButton(i, -6 -i, "up");
                callButtonsList.add(upCallButtonCreator);
            }
        }
        if (_isBasement == false){
            for (Integer i = 2; i <= 60 ; i++){
                CallButton downCallButtonCreator = new CallButton(i, i, "down");
                callButtonsList.add(downCallButtonCreator);
            }
        }
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
        //System.out.println("Hello World!");
        Column column1 = new Column(1, 5, 60, false);
        System.out.println(column1.elevatorsList.size());
    }
}