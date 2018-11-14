import bos.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Shepherd extends Character {

    public Shepherd(Cell location, Behaviour behaviour) {
        super(location, behaviour);
        try{
            display = Optional.of(ImageIO.read(new File("shepherd.png")));
        } catch (IOException e) {
            display = Optional.empty();
        }
        description = "shepherd";
    }

}