package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.scenes.DimensionsProvider;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Implementing this interface exposes the {@link Bounded#getNonTransformedBounds()} method, which returns the bounds, aka
 * Bounding Box, of this Entity.
 */
public interface Bounded extends DimensionsProvider, GameNode {

    /**
     * Return the {@link Bounds}, (Bounding Box) after all transformations have been
     * applied.
     *
     * @return the {@link Bounds}
     */
    default Bounds getTransformedBounds() {
        if (getNode().isPresent()) {
            return getNode().get().getBoundsInParent();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    /**
     * Return the {@link Bounds} (Bounding Box) before all transformations have been applied.
     *
     * @return the {@link Bounds}
     */
    default Bounds getNonTransformedBounds() {
        if (getNode().isPresent()) {
            return getNode().get().getBoundsInLocal();
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    /**
     * Return the {@link Bounds} (Bounding Box) within the {@link com.github.hanyaeger.api.engine.scenes.YaegerScene} after
     * all transformations have been applied. This method differs from {@link Bounded#getTransformedBounds} in the fact
     * that this methods threats the {@link javafx.scene.Node} as if it was part of the {@link javafx.scene.Scene}. In the case
     * of a {@link CompositeEntity} the {@link javafx.scene.Node} will be part of a {@link javafx.scene.Group}, meaning we get
     * the {@link Bounds} within that {@link javafx.scene.Group} and not the {@link javafx.scene.Scene}.
     *
     * @return the {@link Bounds}
     */
    default Bounds getBoundsInScene() {
        if (getNode().isPresent()) {
            return getNode().get().localToScene(getNode().get().getBoundsInLocal(), true);
        } else {
            return new BoundingBox(0, 0, 0, 0);
        }
    }

    @Override
    default double getWidth() {
        return getNonTransformedBounds().getWidth();
    }

    @Override
    default double getHeight() {
        return getNonTransformedBounds().getHeight();
    }

    /**
     * @return a {@code double} of the right side x value
     */
    default double getRightX() {
        return getNonTransformedBounds().getMaxX();
    }

    /**
     * @return a {@code double} of the left x value
     */
    default double getLeftX() {
        return getNonTransformedBounds().getMinX();
    }

    /**
     * @return a {@code double} of the center x value
     */
    default double getCenterX() {
        return getNonTransformedBounds().getCenterX();
    }

    /**
     * @return a {@code double} of the bottom y value
     */
    default double getBottomY() {
        return getNonTransformedBounds().getMaxY();
    }

    /**
     * @return a {@code double} of the top y value
     */
    default double getTopY() {
        return getNonTransformedBounds().getMinY();
    }

    /**
     * @return a {@code double} of the center y value
     */
    default double getCenterY() {
        return getNonTransformedBounds().getCenterY();
    }
}
