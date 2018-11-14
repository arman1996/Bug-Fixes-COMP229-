import bos.*;
import java.awt.Color;
import java.util.*;

public class RabbitAdapter extends Character {
    private Rabbit adaptee;
    // Uses two variables to look one move ahead.
    // Or else the move that appears in the overlay is the move that has just been performed.
    //private int number;
    private int currentMove;
    private int futureMove;
    private int firstMoveInt;
    private int secondMoveInt;
    // Needed to make a random number to populate secondMoveInt.
    private static Random rand = new Random();
    public RabbitAdapter(Cell location) {
        super(location, null);
        display = Optional.of(Color.LIGHT_GRAY);
        adaptee = new Rabbit();
        description = "rabbit";
        // Call the method when the rabbit is created so it runs forever.
        calcMove();
        // Assign a starting value because java complains otherwise.
        secondMoveInt = rand.nextInt(4);

    }

    @Override
    public List<RelativeMove> aiMoves(){
        // Since first move is the random integer that the rabbit class generates,
        // That is used for the switch case statement.
        while(true){
            switch (currentMove/*adaptee.nextMove()*/){
                case 0:
                    return Arrays.asList(new MoveDown(Stage.getInstance().grid, this));
                case 1:
                    return Arrays.asList(new MoveUp(Stage.getInstance().grid, this));
                case 2:
                    return Arrays.asList(new MoveLeft(Stage.getInstance().grid, this));
                case 3:
                    return Arrays.asList(new MoveRight(Stage.getInstance().grid, this));
            };
            return Arrays.asList(new MoveRandomly(Stage.getInstance().grid, this));
        }

    }
    // Method spawns a new thread and tries to compute the next move without affecting the current thread.
    public void calcMove(){
        currentMove = futureMove;
        Thread getFutureInt = new Thread(){
            public void run(){
                while(true){
                    futureMove = adaptee.nextMove();
                    Stage.getInstance().rabbit.aiMoves().get(0).perform();
                }
            }
        };
        getFutureInt.start();
    }
}
