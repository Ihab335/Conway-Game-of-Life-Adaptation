 

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @author Gurvir Singh (23010952) Ihab Azhar (23049043)
 * @version 2024.03.01
 */

public class Simulator {

    private enum CellType {
        MYCOPLASMA,
        CHAMELEON,
        VIRUS,
        BACTERIA
    }

    private static final double MYCOPLASMA_ALIVE_PROB = 0.35;
    private static final double CHAMELEON_ALIVE_PROB = 0.50;
    private static final double VIRUS_ALIVE_PROB = 0.20;
    private static final double BACTERIA_ALIVE_PROB = 0.35;
    private List<Cell> cells;
    private Field field;

    private Random rand;
    private static int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        rand = new Random();
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for (Cell cell : cells) {
            cell.act();

            if (cell.isInfected()){
                cell.setNeighbourInfected();
            }

            cell.setRandomInfected(rand.nextDouble());
        }

        for (Cell cell : cells) {
            cell.updateState();
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                CellType type = getRandomCellType(rand);
                Cell cellToAdd = null;
                switch (type)
                {
                    case MYCOPLASMA:
                        Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
                        cellToAdd = setDeadCell(myco, rand, MYCOPLASMA_ALIVE_PROB);
                        break;

                    case CHAMELEON:
                        Chameleon cham = new Chameleon(field, location, Color.BLACK);
                        cellToAdd = setDeadCell(cham, rand, CHAMELEON_ALIVE_PROB);
                        break;

                    case VIRUS:
                        Virus vir = new Virus(field, location, Color.BLUE);
                        cellToAdd = setDeadCell(vir, rand, VIRUS_ALIVE_PROB);
                        break;

                    case BACTERIA:
                        Bacteria bac = new Bacteria(field, location, Color.CYAN);
                        cellToAdd = setDeadCell(bac, rand, BACTERIA_ALIVE_PROB);
                        break;
                }
                cells.add(cellToAdd);
            }
        }
    }
    
    /**
     * Decides whether the cell is alive or not.
     * @param randomCell the cell that it's either dead or alive.
     * @param rand The Random class used to generate a random double.
     * @param aliveProb The probability of a certain life form of being alive.
     * @return randomCell The cell that is dead or alive.
     */
    private static Cell setDeadCell(Cell randomCell, Random rand, double aliveProb){
        if (rand.nextDouble() > aliveProb) {
            randomCell.setDead();
        }
        return randomCell;
    }
    
    /**
     * Decides at random what life form to add at certain location.
     * @param rand The Random class used to generate a random integer from 0 to 3.
     * @return CellType A random cell type chosen from the enum CellType.
     */
    private static CellType getRandomCellType(Random rand){
        return CellType.values()[rand.nextInt(CellType.values().length)];
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    /**
     * Retruns the field populated with life forms.
     * @return field The field populated with life forms.
     */
    public Field getField() {
        return field;
    }
    
    /**
     * Returns what generation the simulation it's in.
     * @return generation The current generation.
     */
    public static int getGeneration() {
        return generation;
    }
}
