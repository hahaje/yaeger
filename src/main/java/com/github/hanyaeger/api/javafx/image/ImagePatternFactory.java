package com.github.hanyaeger.api.javafx.image;

import com.google.inject.Singleton;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * An {@code ImagePatternFactory} should be used for creating instance of {@link javafx.scene.paint.ImagePattern}.
 */
@Singleton
public class ImagePatternFactory {

    /**
     * Constructs an {@link javafx.scene.paint.ImagePattern} with content loaded from the specified
     * url.
     *
     * @param image the {@link Image} for which an {@link javafx.scene.paint.ImagePattern} should be created.
     * @return An instance of {@link javafx.scene.paint.ImagePattern}
     */
    public ImagePattern create(final Image image) {
        return new ImagePattern(image);
    }
}
