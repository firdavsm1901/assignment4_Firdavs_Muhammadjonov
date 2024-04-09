import java.util.HashMap;
import java.util.Map;

enum Direction {
    NORTH, EAST, SOUTH, WEST
}

class Room implements Cloneable {
    private Map<Direction, Wall> sides = new HashMap<Direction, Wall>();
    private int roomNumber;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room() {}

    public Wall getSide(Direction direction) {
        return sides.get(direction);
    }

    public void setSide(Direction direction, Wall wall) {
        sides.put(direction, wall);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void initialize(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }
}

class MagicalRoom extends Room {
    private String enchantment;

    public MagicalRoom(int roomNumber, String enchantment) {
        super(roomNumber);
        this.enchantment = enchantment;
    }

    public MagicalRoom() {}

    public void initialize(int roomNumber) {
        super.initialize(roomNumber);
        enchantment = someEnchantment();
    }

    private String someEnchantment() {
        return "Enchantment";
    }
}

class DoorWall extends Wall {
    private Room room1;
    private Room room2;
    private boolean isOpen;

    public DoorWall(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
        this.isOpen = false;
    }

    public DoorWall() {}
}

class DoorWallRequiringEnchantment extends DoorWall {
    private String enchantment;

    DoorWallRequiringEnchantment(Room room1, Room room2, String enchantment) {
        super(room1, room2);
        this.enchantment = enchantment;
    }

    public DoorWallRequiringEnchantment() {
        super();
    }
}

class Wall implements Cloneable {
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Maze implements Cloneable {
    private Map<Integer, Room> rooms = new HashMap<Integer, Room>();

    public void addRoom(Room room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public Room roomNumber(int roomNumber) {
        return rooms.get(roomNumber);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class FactoryPrototype {
    private static FactoryPrototype theFactory;
    private Maze mazePrototype;
    private Room roomPrototype;
    private Wall wallPrototype;
    private DoorWall doorWallProtorype;

    private FactoryPrototype(Maze maze, Room room, Wall wall, DoorWall doorWall) {
        mazePrototype = maze;
        roomPrototype = room;
        wallPrototype = wall;
        doorWallProtorype = doorWall;
    }

    public static FactoryPrototype instance(Maze maze, Room room, Wall wall, DoorWall doorWall) {
        if (theFactory == null)
            theFactory = new FactoryPrototype(maze, room, wall, doorWall);
        return theFactory;
    }

    public Maze makeMaze() throws CloneNotSupportedException {
        return (Maze) mazePrototype.clone();
    }

    public Wall makeWall() throws CloneNotSupportedException {
        return (Wall) wallPrototype.clone();
    }

    public Room makeRoom(int number) throws CloneNotSupportedException {
        Room newRoom = (Room) roomPrototype.clone();
        newRoom.initialize(number);
        return newRoom;
    }

    public DoorWall makeDoorWall(Room room1, Room room2) throws CloneNotSupportedException {
        DoorWall newDoorWall = (DoorWall) doorWallProtorype.clone();
        return newDoorWall;
    }
}

class MazeGame {
    public static void main(String[] args) throws CloneNotSupportedException {
        FactoryPrototype factory = FactoryPrototype.instance(
                new Maze(), new MagicalRoom(), new Wall(), new DoorWallRequiringEnchantment());
        createMaze(factory);
    }

    static Maze createMaze(FactoryPrototype factory) throws CloneNotSupportedException {
        Maze aMaze = factory.makeMaze();
        Room room1 = factory.makeRoom(1);
        Room room2 = factory.makeRoom(2);
        DoorWall door = factory.makeDoorWall(room1, room2);
        aMaze.addRoom(room1);
        aMaze.addRoom(room2);
        room1.setSide(Direction.NORTH, factory.makeWall());
        room1.setSide(Direction.EAST, door);
        room1.setSide(Direction.SOUTH, factory.makeWall());
        room1.setSide(Direction.WEST, factory.makeWall());
        room2.setSide(Direction.NORTH, factory.makeWall());
        room2.setSide(Direction.EAST, factory.makeWall());
        room2.setSide(Direction.SOUTH, factory.makeWall());
        room2.setSide(Direction.WEST, door);
        return aMaze;
    }
}
