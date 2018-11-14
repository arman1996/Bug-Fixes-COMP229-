import java.awt.*;
import java.util.*;
import java.time.*;
import java.util.List;

import bos.*;

public class Stage extends KeyObservable {
    protected Grid grid;
    protected Character sheep;
    protected Character shepherd;
    protected Character wolf;
    protected RabbitAdapter rabbit;
    private List<Character> allCharacters;
    protected Player player;

    private Instant timeOfLastMove = Instant.now();

    private static Stage instance = new Stage();

    public static Stage getInstance(){
        return instance;
    }
    private Stage() {
        SAWReader sr = new SAWReader("data/stage1.saw");
        grid     = new Grid(10, 10);
        shepherd = new Shepherd(grid.cellAtRowCol(sr.getShepherdLoc().first, sr.getShepherdLoc().second), new StandStill());
        sheep    = new Sheep(grid.cellAtRowCol(sr.getSheepLoc().first, sr.getSheepLoc().second), new MoveTowards(shepherd));
        wolf     = new Wolf(grid.cellAtRowCol(sr.getWolfLoc().first, sr.getWolfLoc().second), new MoveTowards(sheep));
        rabbit = new RabbitAdapter(grid.getRandomCell());

        player = new Player(grid.getRandomCell());
        this.register(player);
        player.startMove();

        // put in the blocks we found in the config file
        sr.getBlocks().forEach(p -> grid.blockCell(p.first, p.second));


        allCharacters = new ArrayList<Character>();
        allCharacters.add(sheep); allCharacters.add(shepherd); allCharacters.add(wolf); allCharacters.add(rabbit);

    }

    public void update(){
        if (!player.inMove()) {
            if (sheep.location == shepherd.location) {
                System.out.println("The sheep is safe :)");
                System.exit(0);
            } else if (sheep.location == wolf.location) {
                System.out.println("The sheep is dead :(");
                System.exit(1);
            } else {
                allCharacters.forEach((c) -> {
                    (new Thread() {
                        public void run(){
                            List<RelativeMove> moves = getCharacterMoves(c);
                            if (c == wolf){
                                System.out.print(c.description + ": actual path 1:"); showPath(moves);
                                moves = c.aiMoves();
                                System.out.print(c.description + ": actual path 2:"); showPath(moves);
                                moves = c.aiMoves();
                                System.out.print(c.description + ": actual path 3:"); showPath(moves);
                            }
                            /*
                            if (c == sheep){
                                System.out.print(c.description + ": actual path 1:"); showPath(moves);
                                moves = c.aiMoves();
                                System.out.print(c.description + ": actual path 2:"); showPath(moves);
                                moves = c.aiMoves();
                                System.out.print(c.description + ": actual path 3:"); showPath(moves);
                            }
                            */
                            // Performs all character's moves except hte rabbit's
                            if(c != rabbit){
                                moves.get(0).perform();
                            }
                        }
                    }).start();
                });
                player.startMove();
                timeOfLastMove = Instant.now();
            }
        }
    }

    synchronized List<RelativeMove> getCharacterMoves(Character c){
        return c.aiMoves();
    }


    public static void showPath(List<RelativeMove> lst){
        lst.forEach(rm -> {
            if (rm instanceof MoveUp){
                System.out.print("^");
            } else if (rm instanceof MoveDown){
                System.out.print("\\/");
            } else if (rm instanceof MoveRight){
                System.out.print("->");
            } else if (rm instanceof MoveLeft) {
                System.out.print("<-");
            }
        });
        System.out.println("");
    }

    private List<RelativeMove> moves = new ArrayList<>();
    private Cell originalLoc = null;

    public void paint(Graphics g, Point mouseLocation) {
        grid.paint(g, mouseLocation);
        sheep.paint(g);
        shepherd.paint(g);
        wolf.paint(g);
        rabbit.paint(g);
        player.paint(g);
        boolean onSomething = false;
        for (Character c : allCharacters){
            if (c.getLocationOf().contains(mouseLocation)){
                onSomething = true;
                if(moves == null) {
                    (new Thread() {
                        public void run() {
                            moves = c.aiMoves();
                            System.out.println("moves done");
                            showPath(moves);
                        }
                    }).start();
                } else {
                    Cell originalLoc = c.getLocationOf();
                    for (RelativeMove m : moves) { // note I am going to assume the path is correct - that is NOT generally a good idea
                        m.perform();
                        c.getLocationOf().paintOverlay(g);
                    }
                    c.setLocationOf(originalLoc);
                }

            }
        }
        if(!onSomething){
            moves = null;
        }
    }

    // momento implmentation
    public static class Momento{
        private Cell wolfLoc;
        private Cell shepLoc;
        private Cell sheepLoc;
        private Cell rabbitLoc;
        private Cell playerLoc;
        private Behaviour wolfBeh;
        private Behaviour shepBeh;
        private Behaviour sheepBeh;
        public Momento(Stage s){
            wolfLoc = s.wolf.location;
            shepLoc = s.shepherd.location;
            sheepLoc = s.sheep.location;
            rabbitLoc = s.rabbit.location;
            playerLoc = s.player.location;
            wolfBeh = s.wolf.behaviour;
            shepBeh = s.shepherd.behaviour;
            sheepBeh = s.sheep.behaviour;
        }
    }

    public Momento getMomento(){
        return new Momento(this);
    }

    public void restoreMomento(Momento m){
        wolf.location = m.wolfLoc;
        sheep.location = m.sheepLoc;
        shepherd.location = m.shepLoc;
        player.location = m.playerLoc;
        wolf.behaviour = m.wolfBeh;
        shepherd.behaviour = m.sheepBeh;
        sheep.behaviour = m.sheepBeh;
    }

}