import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import bos.*;

import javax.imageio.ImageIO;

public class Sheep extends Character {

    public Sheep(Cell location, Behaviour behaviour) {
        super(location, behaviour);

        try{
            display = Optional.of(ImageIO.read(new File("sheep.png")));
        } catch (IOException e) {
            display = Optional.empty();
        }
        description = "sheep";
    }
}