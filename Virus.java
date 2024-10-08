 
import javafx.scene.paint.Color;
import java.util.List;

/**
 * A form of life that changes its behaviour throughout its life
 *
 * @author Gurvir Singh (23010952) Ihab Azhar (23049043)
 * @version 2024.03.01
 */

public class Virus extends Cell {

    /**
     * Create a new Virus.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Virus(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Virus decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        int generation = Simulator.getGeneration();

        if (isAlive()) {
            // for loop to check for neighbours that are Mycoplasma cells.
            for (Cell neighbour : neighbours) {
                if (neighbour instanceof Mycoplasma)
                {
                    setNextState(true);
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

            if (generation < 11) {
                switch (neighbours.size()) {
                    case (2):
                    case (3):
                    case (4):
                        setNextState(true);
                        break;
                    default:
                        setNextState(false);
                }
            }

            else {
                switch (neighbours.size()) {
                    case (0):
                    case (1):
                    case (2):
                        setNextState(true);
                        break;
                    default:
                        setNextState(false);
                }
            }
        }
    }
}
