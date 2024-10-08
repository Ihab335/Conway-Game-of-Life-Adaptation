 

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * A graphical view of the simulation grid. The view displays a rectangle for
 * each location. Colors for each type of life form can be defined using the
 * setColor method.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @author Gurvir Singh (23010952) Ihab Azhar (23049043)
 * @version 2024.03.01
 */

public class SimulatorView extends Application {

    public static final int GRID_WIDTH = 100;
    public static final int GRID_HEIGHT = 80;
    public static final int WIN_WIDTH = 650;
    public static final int WIN_HEIGHT = 650;

    private static final Color EMPTY_COLOR = Color.WHITE;

    private final String GENERATION_PREFIX = "Generation: ";
    private final String POPULATION_PREFIX = "Population: ";

    private Label genLabel, population, infoLabel;
    private Button nextGen, resetButton;
    private ToggleButton selectGen;

    private FieldCanvas fieldCanvas;
    private FieldStats stats;
    private Simulator simulator;

    /**
     * Create a view of the given width and height.
     */
    @Override
    public void start(Stage stage) {

        stats = new FieldStats();
        fieldCanvas = new FieldCanvas(WIN_WIDTH - 50, WIN_HEIGHT - 50);
        fieldCanvas.setScale(GRID_HEIGHT, GRID_WIDTH);
        simulator = new Simulator();

        Group root = new Group();

        genLabel = new Label(GENERATION_PREFIX);
        infoLabel = new Label("  ");
        population = new Label(POPULATION_PREFIX);

        BorderPane bPane = new BorderPane();
        HBox infoPane = new HBox();
        HBox popPane = new HBox();
        
        //Creating 3 buttons
        nextGen = new Button("Next generation");
        selectGen = new ToggleButton("Simulate");
        resetButton = new Button("Reset");
        
        //Simulate 1 generation when this button is pressed
        nextGen.setOnAction(actionEvent  -> simulate(1));
        
        //Simulate until the button is pressed again
        selectGen.setOnAction(actionEvent -> {
            if (selectGen.isSelected())
            {
                selectGen.setText("Stop");
                new Thread(()-> {
                    while (selectGen.isSelected()) {
                        simulate(1);
                        simulator.delay(1000);
                    }
                }).start();
            }
            else {
                selectGen.setText("Simulate");
            }
        });
        
        //Resets the field when this button is pressed
        resetButton.setOnAction(actionEvent -> reset());

        infoPane.setSpacing(10);
        infoPane.getChildren().addAll(genLabel, infoLabel);
        popPane.getChildren().addAll(population);

        bPane.setTop(infoPane);
        bPane.setCenter(fieldCanvas);
        bPane.setBottom(population);

        nextGen.setLayoutX(452);
        nextGen.setLayoutY(620);

        selectGen.setLayoutX(560);
        selectGen.setLayoutY(620);

        resetButton.setLayoutX(400);
        resetButton.setLayoutY(620);

        root.getChildren().addAll(bPane, nextGen, selectGen, resetButton);

        Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Life Simulation");
        updateCanvas(simulator.getGeneration(), simulator.getField());

        stage.show();
    }

    /**
     * Show the current status of the field.
     * @param generation The current generation.
     * @param field The field whose status is to be displayed.
     */
    public void updateCanvas(int generation, Field field) {
        genLabel.setText(GENERATION_PREFIX + generation);
        stats.reset();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getObjectAt(row, col);

                if (cell != null && cell.isAlive()) {
                    stats.incrementCount(cell.getClass());
                    fieldCanvas.drawMark(col, row, cell.getColor());
                }
                else {
                    fieldCanvas.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }

        stats.countFinished();
        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    /**
     * Run the simulation from its current state for the given number of
     * generations.  Stop before the given number of generations if the
     * simulation ceases to be viable.
     * @param numGenerations The number of generations to run for.
     */
    public void simulate(int numGenerations) {
        new Thread(() -> {
            if (isViable(simulator.getField())) {
                for (int gen = 1; gen <= numGenerations; gen++) {
                    simulator.simOneGeneration();
                    simulator.delay(500);
                    Platform.runLater(() -> {
                        updateCanvas(simulator.getGeneration(), simulator.getField());
                    });
                }
            }
        }).start();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        simulator.reset();
        updateCanvas(simulator.getGeneration(), simulator.getField());
    }
    
    /**
     * Launches the application.
     */
    public static void main(String[] args){
        launch(args);
    }
}
