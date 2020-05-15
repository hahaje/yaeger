package com.github.hanyaeger.api.engine.entities.entity.sprite.delegates;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import com.github.hanyaeger.api.engine.Updatable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A {@code SpriteAnimationDelegate} holds all responsibility related to Sprites that contain multiple images.
 */
public class SpriteAnimationDelegate implements Updatable {

    private long previousCycleTime = 0;
    private long autoCycleInterval = 0;
    private ImageView imageView;
    private List<Rectangle2D> viewports = new ArrayList<>();
    private int currentIndex = 0;

    /**
     * Create a new {@code SpriteAnimationDelegate} for the given {@link ImageView} and number of frames.
     * After construction, the spriteIndex will be set to the first frame.
     *
     * @param imageView The {@link ImageView} for which the different frames should be created
     * @param frames    The number of frames available
     */
    public SpriteAnimationDelegate(ImageView imageView, int frames) {
        this.imageView = imageView;

        createViewPorts(frames);
        setSpriteIndex(0);
    }

    /**
     * Set the index of the sprite. Since de modulus (mod frames) is used, this can be an unbounded integer.
     *
     * @param index The index to select. This index will be applied modulo the total number
     *              of frames
     */
    public void setSpriteIndex(int index) {
        var modulus = index % viewports.size();
        imageView.setViewport(viewports.get(modulus));
        currentIndex = index;
    }

    @Override
    public void update(long timestamp) {
        if (autoCycleInterval == 0) {
            return;
        }

        if (timestamp > previousCycleTime + autoCycleInterval) {
            next();
            previousCycleTime = timestamp;
        }
    }

    /**
     * Set the interval at which the sprite should be automatically cycled
     *
     * @param interval the interval milli-seconds
     */
    public void setAutoCycle(long interval) {
        this.autoCycleInterval = interval * 1000000;
    }

    /**
     * Set the next index of the sprite.
     */
    public void next() {
        setSpriteIndex(++currentIndex);
    }

    private void createViewPorts(int frames) {
        var frameWidth = getFrameWidth(frames);
        var frameHeight = imageView.getImage().getHeight();

        IntStream.range(0, frames).forEach(frame -> addViewPort(frame, frameWidth, frameHeight));
    }

    private void addViewPort(int frame, double frameWidth, double frameHeight) {
        viewports.add(new Rectangle2D(frame * frameWidth, 0, frameWidth, frameHeight));
    }

    private double getFrameWidth(int frames) {
        return imageView.getImage().getWidth() / frames;
    }
}
