package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.annotations.UpdatableProvider;
import com.github.hanyaeger.api.engine.scenes.SceneBorder;
import com.github.hanyaeger.api.engine.scenes.YaegerScene;

/**
 * Implement this interface to be notified if the {@link YaegerEntity} touches the boundary of the {@link YaegerScene}.
 * In that case, the method {@link SceneBorderTouchingWatcher#notifyBoundaryTouching(SceneBorder)}
 * will be called.
 */
public interface SceneBorderTouchingWatcher extends Bounded, SceneChild {

    /**
     * This method is being called when this {@link SceneBorderTouchingWatcher} touches a border of the {@link YaegerScene}.
     *
     * @param border The border of the {@link YaegerScene} the {@link YaegerEntity} is touching.
     */
    void notifyBoundaryTouching(final SceneBorder border);

    @UpdatableProvider
    default Updatable watchForBoundaryTouching() {
        return timestamp -> {
            if (getTransformedBounds().getMinX() <= 0) {
                notifyBoundaryTouching(SceneBorder.LEFT);
            } else if (getTransformedBounds().getMinY() <= 0) {
                notifyBoundaryTouching(SceneBorder.TOP);
            } else if (getTransformedBounds().getMaxY() >= getSceneHeight()) {
                notifyBoundaryTouching(SceneBorder.BOTTOM);
            } else if (getTransformedBounds().getMaxX() >= getSceneWidth()) {
                notifyBoundaryTouching(SceneBorder.RIGHT);
            }
        };
    }
}
