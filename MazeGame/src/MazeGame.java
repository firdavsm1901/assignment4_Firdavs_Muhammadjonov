import java.util.HashMap;
import java.util.Map;

enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

class Maze {
    private Map<Integer, Room> mapRooms = new HashMap<Integer, Room>();

    public void addRoom(Room room) {
        mapRooms.put(room.getRoomNumber(), room);
    }

    public Room getRoomByNumber(int roomNumber) {
        return mapRooms.get(roomNumber);
    }
}

class Wall {
    private boolean isSecret;

    public Wall() {
        this.isSecret = false;
    }

    public Wall(boolean isSecret) {
        this.isSecret = isSecret;
    }

    public boolean isSecret() {
        return isSecret;
    }

    public void setSecret(boolean isSecret) {
        this.isSecret = isSecret;
    }
}

class Room {
    private Map<Direction, Wall> mapSides = new HashMap<Direction, Wall>();

    private int roomNumber;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room() {}

    public Wall getSide(Direction direction) {
        return mapSides.get(direction);
    }

    public void setSide(Direction direction, Wall wall) {
        mapSides.put(direction, wall);
    }

    protected void initialize(int roomNumber) {}

    public Integer getRoomNumber() {
        return roomNumber;
    }
}

class DoorWall extends Wall {
    private Room firstRoom;
    private Room secondRoom;
    private boolean isOpen;

    public DoorWall(Room firstRoom, Room secondRoom) {
        this.firstRoom = firstRoom;
        this.secondRoom = secondRoom;
        this.isOpen = false;
    }

    public Object getSecondRoom() {
        return secondRoom;
    }

    public Object getFirstRoom() {
        return firstRoom;
    }
}

class AdvancedRoom extends Room {

    private String enchantment;

    public AdvancedRoom(int roomNumber, String enchantment) {
        super(roomNumber);
        this.enchantment = enchantment;
    }

    public AdvancedRoom() {
        super();
    };

    public void initialize(int roomNumber) {
        super.initialize(roomNumber);
        enchantment = generateEnchantment();
    }

    private String generateEnchantment() {
        return "Spell"; 
    }
}

class DoorWallRequiringSpell extends DoorWall {
    private String spell;

    DoorWallRequiringSpell(Room firstRoom, Room secondRoom, String spell) {
        super(firstRoom, secondRoom);
        this.spell = spell;
    }
}

public class MazeGame {
    public static void main(String[] args) {
        // MazeBuilder builder = new BasicMazeBuilder();
        MazeBuilder builder = new AdvancedMazeBuilder();
        createMaze(builder);
    }

    private static Maze createMaze(MazeBuilder builder) {
        builder.buildMaze();
        builder.buildRoom(1);
        builder.buildRoom(2);
        builder.buildDoorWall(1, Direction.EAST, 2, Direction.WEST);
        return builder.getMaze();
    }
}

interface MazeBuilder {
    public void buildMaze();

    public void buildRoom(int roomNumber);

    public void buildDoorWall(int firstRoomNumber, Direction firstSide, int secondRoomNumber, Direction secondSide);

    public Maze getMaze();
}

class BasicMazeBuilder implements MazeBuilder {
    protected Maze currentMaze;

    public void buildMaze() {
        currentMaze = new Maze();
    }

    public Maze getMaze() {
        return currentMaze;
    }

    public void buildRoom(int roomNumber) {
        if (currentMaze.getRoomByNumber(roomNumber) == null) {
            Room room = new Room(roomNumber);
            currentMaze.addRoom(room);
            room.setSide(Direction.NORTH, new Wall());
            room.setSide(Direction.EAST, new Wall());
            room.setSide(Direction.SOUTH, new Wall());
            room.setSide(Direction.WEST, new Wall());
        }
    }

    public void buildDoorWall(int firstRoomNumber, Direction firstSide, int secondRoomNumber, Direction secondSide) {
        Room firstRoom = currentMaze.getRoomByNumber(firstRoomNumber);
        Room secondRoom = currentMaze.getRoomByNumber(secondRoomNumber);
        if ((firstRoom != null) && (secondRoom != null)) {
            DoorWall doorWall = new DoorWall(firstRoom, secondRoom);
            firstRoom.setSide(firstSide, doorWall);
            secondRoom.setSide(secondSide, doorWall);
        }
    }
}

class AdvancedMazeBuilder extends BasicMazeBuilder {
    public void buildRoom(int roomNumber) {
        if (currentMaze.getRoomByNumber(roomNumber) == null) {
            Room room = new AdvancedRoom(roomNumber, generateSpell());
            currentMaze.addRoom(room);

            room.setSide(Direction.NORTH, new Wall());
            room.setSide(Direction.EAST, new Wall());
            room.setSide(Direction.SOUTH, new Wall());
            room.setSide(Direction.WEST, new Wall());
        }
    }

    public void buildDoorWall(int firstRoomNumber, Direction firstSide, int secondRoomNumber, Direction secondSide) {
        Room firstRoom = currentMaze.getRoomByNumber(firstRoomNumber);
        Room secondRoom = currentMaze.getRoomByNumber(secondRoomNumber);
        if ((firstRoom != null) && (secondRoom != null)) {
            DoorWall doorWall = new DoorWallRequiringSpell(firstRoom, secondRoom, generateSpell());
            firstRoom.setSide(firstSide, doorWall);
            secondRoom.setSide(secondSide, doorWall);
        }
    }

    private String generateSpell() {
        return "Spell"; 
    }
}
