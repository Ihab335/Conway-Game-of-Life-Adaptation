 
import javafx.scene.paint.Color;
import java.util.List;

/**
 * A form of life that changes its colours throughout its life
 *
 * @author Gurvir Singh (23010952) Ihab Azhar (23049043)
 * @version 2024.03.01
 */

public class Chameleon extends Cell {

    /**
     * Create a new Chameleon.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Chameleon(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Chameleon decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());

        if (getColor().equals(Color.BROWN)){
            setNextState(false);
        }

        else if (isAlive()) {
            if (isInfected()){
                switch(neighbours.size())
                {
                    case (6):
                    case (7):
                        setNextState(true);
                        break;
                    default:
                        setNextState(false);
                }
                return;
            }

            switch(neighbours.size())
            {
                case (0):
                    setColor(Color.BROWN);
                    setNextState(true);
                    break;
                case (1):
                case (3):
                case (4):
                case (5):
                    setColor(Color.CORNFLOWERBLUE);
                    setNextState(true);
                    break;
                default:
                    setColor(Color.BLUEVIOLET);
                    setNextState(true);
            }
        }

    }
}
