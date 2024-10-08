
import javafx.scene.paint.Color;
import java.util.List;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @author Gurvir Singh (23010952) Ihab Azhar (23049043)
 * @version 2024.03.01
 */

public class Mycoplasma extends Cell {

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not.
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        if (isAlive()) {

            for (Cell neighbour : neighbours) {
                if (neighbour instanceof Virus)
                {
                    setNextState(false);
                    return;
                }
            }

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
                case (2):
                case (3):
                    setNextState(true);
                    break;
                default:
                    setNextState(false);
            }
        }

        else
        {
            if (neighbours.size() == 3)
            {
                setNextState(true);
            }
        }
    }
}

