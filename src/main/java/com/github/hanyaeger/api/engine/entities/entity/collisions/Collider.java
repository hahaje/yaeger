package com.github.hanyaeger.api.engine.entities.entity.collisions;

import com.github.hanyaeger.api.engine.entities.entity.motion.SpeedProvider;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.Bounded;
import com.github.hanyaeger.api.engine.entities.entity.motion.DirectionProvider;

/**
 * A {@link Collider} represents an {@link YaegerEntity} that can be collided with
 * by a {@link AABBCollided}
 * <p>
 * In case of a collision, only the {@link AABBCollided} will be notified.
 */
public interface Collider extends Bounded, SpeedProvider, DirectionProvider {
}
