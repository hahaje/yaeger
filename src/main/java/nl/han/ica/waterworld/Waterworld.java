package nl.han.ica.waterworld;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.han.ica.waterworld.gameobjects.BubbleSpawner;
import nl.han.ica.waterworld.gameobjects.Player;
import nl.han.ica.waterworld.gameobjects.Swordfish;
import nl.han.ica.yaeger.YaegerEngine;
import nl.han.ica.yaeger.gameobjects.text.TextObject;
import nl.han.ica.yaeger.metrics.GameDimensions;
import nl.han.ica.yaeger.resourceconsumer.audio.Sound;

public class Waterworld extends YaegerEngine {

    private static final String GAME_TITLE = "Waterworld 2";
    private static final int WATERWORLD_WIDTH = 1204;
    private static final int WATERWORLD_HEIGHT = 903;

    private TextObject bubblesPoppedText;
    private TextObject healthText;
    private TextObject gameOverText;

    private int bubblesPopped = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void beforeStageIsCreated() {
        setGameDimensions(new GameDimensions(WATERWORLD_WIDTH, WATERWORLD_HEIGHT));
        setGameTitle(GAME_TITLE);
    }

    @Override
    protected void beforeGameLoopIsCreated() {
        setBackgroundImage("background.jpg");
        addInitialGameObjects();
        addDashboard();
        addBackgroundAudio();
    }

    private void addDashboard() {
        bubblesPoppedText = new TextObject(0, 40);
        bubblesPoppedText.setFont(Font.font("palatino", 50));
        addGameObject(bubblesPoppedText);
        updateBubblesPoppedText();

        healthText = new TextObject(0, 90);
        healthText.setFont(Font.font("palatino", 50));
        addGameObject(healthText);
        setHealthText(10);

        gameOverText = new TextObject(420, 400, "Game over");
        gameOverText.setFill(Color.DARKRED);
        gameOverText.setFont(Font.font("palatino", 60));
        addGameObject(gameOverText);
        gameOverText.setVisible(false);
    }


    private void addInitialGameObjects() {
        var swordFish = new Swordfish(200, 200);
        addGameObject(swordFish);

        var player = new Player(100, 100, this);
        addGameObject(player);
    }

    @Override
    public void afterStageIsShown() {
        BubbleSpawner spawner = new BubbleSpawner(WATERWORLD_WIDTH, WATERWORLD_HEIGHT, this);
        registerSpawner(spawner);
    }

    /**
     * Verhoog de waarde van het aantal ontplofte bubbles.
     */
    public void increaseBubblesPopped() {
        bubblesPopped++;
        updateBubblesPoppedText();
    }

    /**
     * Zet de healthwaarde van de speler.
     *
     * @param health De health als integer.
     */
    public void setHealthText(int health) {
        healthText.setText("Health: " + health);
    }

    /**
     * Deze methode wordt aangeroepen wanneer de speler sterft.
     */
    public void playerDied() {
        gameOverText.setVisible(true);
        healthText.setVisible(false);
        bubblesPoppedText.setLocation(370, 460);
    }


    private void addBackgroundAudio() {
        Sound clip = new Sound("audio/waterworld.mp3", Sound.INDEFINITE);
        clip.play();
    }

    private void updateBubblesPoppedText() {
        bubblesPoppedText.setText("Bubbles popped: " + bubblesPopped);
    }
}
