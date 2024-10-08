 
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Random;

/**
 * A form of life that has different sets of rules which
 * have a certain probability of being used to decide if it lives or not
 *
 * @author Gurvir Singh (23010952) Ihab Azhar (23049043)
 * @version 2024.03.01
 */

public class Bacteria extends Cell {

    private enum Rules{
        RULE1,
        RULE2,
        RULE3
    }

    /**
     * Create a new Bacteria.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bacteria(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
     * This is how the Bacteria decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        Random rand = new Random();
        double randomNumber = rand.nextDouble();

        Rules ruleApplied;
        // This block first checks if infected, then uses a RNG to pick out a rule based on a probability.
        if (isAlive()) {

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

            if (randomNumber <= 0.3) {
                ruleApplied = Rules.RULE1;
            } else if (randomNumber <= 0.9) {
                ruleApplied = Rules.RULE2;
            } else {
                ruleApplied = Rules.RULE3;
            }

            switch (ruleApplied) {
                case RULE1:
                    applyRule1(neighbours);
                    break;

                case RULE2:
                    applyRule2(neighbours);
                    break;

                case RULE3:
                    applyRule3(neighbours);
                    break;
            }
        }
    }

    /**
     * Applies rule 1 if selected.
     */
    private void applyRule1(List<Cell> neighbours){
        switch(neighbours.size())
        {
            case (0):
            case (1):
            case (2):
                setNextState(true);
                break;
            default:
                setNextState(false);
        }
    }

    /**
     * Applies rule 2 if selected.
     */
    private void applyRule2(List<Cell> neighbours){
        switch(neighbours.size())
        {
            case (7):
            case (8):
                setNextState(false);
                break;
            default:
                setNextState(true);
        }
    }

    /**
     * Applies rule 3 if selected.
     */
    private void applyRule3(List<Cell> neighbours){
        switch(neighbours.size())
        {
            case (4):
            case (5):
                setNextState(true);
                break;
            default:
                setNextState(false);
        }
    }
}
