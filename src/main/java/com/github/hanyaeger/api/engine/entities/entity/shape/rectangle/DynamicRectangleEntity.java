package com.github.hanyaeger.api.engine.entities.entity.shape.rectangle;

import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.UpdateDelegator;
import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.entity.ContinuousRotatable;
import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.motion.BufferedMoveable;
import com.github.hanyaeger.api.engine.entities.entity.motion.EntityMotionInitBuffer;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;

import java.util.Optional;

/**
 * An {@link DynamicRectangleEntity} extends all behaviour of a {@link RectangleEntity}, but also implements the
 * {@link Updatable} Interface.
 */
public abstract class DynamicRectangleEntity extends RectangleEntity implements UpdateDelegator, BufferedMoveable, ContinuousRotatable {

    private DefaultMotionApplier motionApplier;
    private Updater updater;
    private Optional<EntityMotionInitBuffer> buffer;
    private double rotationAngle;

    /**
     * Create a new {@link DynamicRectangleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link DynamicRectangleEntity} should be placed
     */
    public DynamicRectangleEntity(final Location initialPosition) {
        super(initialPosition);

        buffer = Optional.of(new EntityMotionInitBuffer());
    }


    @Override
    public MotionApplier getMotionApplier() {
        return motionApplier;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);

        buffer.ifPresent(entityMotionInitBuffer -> {
            entityMotionInitBuffer.setMotionApplier(motionApplier);
            entityMotionInitBuffer.init(injector);
        });
        buffer = Optional.empty();
    }

    @Override
    public void setRotationSpeed(final double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    @Override
    public double getRotationSpeed() {
        return rotationAngle;
    }

    @Override
    public Optional<EntityMotionInitBuffer> getBuffer() {
        return buffer;
    }

    @Inject
    @Override
    public void setMotionApplier(final DefaultMotionApplier motionApplier) {
        this.motionApplier = motionApplier;
    }

    @Inject
    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }
}
