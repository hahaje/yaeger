package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.EntityMotionInitBuffer;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplierType;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import com.google.inject.Injector;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamicCompositeEntityTest {

    private final long TIMESTAMP = 314L;
    public static final double SPEED = 37d;
    public static final double DIRECTION = 42d;
    public static final int ROTATION_SPEED = 37;
    private static final Coordinate2D DEFAULT_LOCATION = new Coordinate2D(0, 0);

    private DynamicCompositeEntityImpl sut;
    private Injector injector;
    private Updater updater;
    private Group group;
    private MotionApplierFactory motionApplierFactory;
    private MotionApplier motionApplier;

    @BeforeEach
    void setup() {
        updater = mock(Updater.class);
        injector = mock(Injector.class);
        group = mock(Group.class);
        motionApplierFactory = mock(MotionApplierFactory.class);
        motionApplier = mock(MotionApplier.class);

        when(motionApplierFactory.create(any(MotionApplierType.class))).thenReturn(motionApplier);

        sut = new DynamicCompositeEntityImpl(DEFAULT_LOCATION);
        sut.setUpdater(updater);
        sut.setGroup(group);
    }

    @Test
    void bufferIsSetInConstructor() {
        // Arrange

        // Act
        Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

        // Assert
        assertTrue(buffer.isPresent());
    }

    @Nested
    class WithMotionApplierSet {

        private MotionApplierFactory motionApplierFactory;
        private MotionApplier motionApplier;

        @BeforeEach
        void setup() {
            motionApplierFactory = mock(MotionApplierFactory.class);
            motionApplier = mock(MotionApplier.class);
            when(motionApplierFactory.create(MotionApplierType.DEFAULT)).thenReturn(motionApplier);
        }

        @Test
        void bufferIsEmptiedAfterInitIsCalled() {
            // Arrange
            sut.injectMotionApplierFactory(motionApplierFactory);

            // Act
            sut.init(injector);

            // Assert
            Assertions.assertFalse(sut.getBuffer().isPresent());
        }

        @Test
        void bufferTransfersMotionOnInit() {
            // Arrange
            sut.setMotion(SPEED, DIRECTION);
            sut.injectMotionApplierFactory(motionApplierFactory);

            // Act
            sut.init(injector);

            // Assert
            verify(motionApplier).setMotion(SPEED, DIRECTION);
        }

        @Test
        void initSetsMotionToDesiredSpeed() {
            // Arrange
            sut.setSpeed(SPEED);
            sut.injectMotionApplierFactory(motionApplierFactory);

            // Act
            sut.init(injector);

            // Assert
            verify(motionApplier).setMotion(SPEED, 0d);
        }

        @Test
        void setMotionApplierIsUsed() {
            // Arrange
            sut.injectMotionApplierFactory(motionApplierFactory);

            // Act
            var mA = sut.getMotionApplier();

            // Assert
            Assertions.assertEquals(motionApplier, mA);
        }

        @Test
        void clearsGarbageOnUpdate() {
            // Arrange
            sut.injectMotionApplierFactory(motionApplierFactory);

            var updater = mock(Updater.class);
            sut.setUpdater(updater);

            ObservableList<Node> children = mock(ObservableList.class);
            when(group.getChildren()).thenReturn(children);

            var entityToAdd = mock(YaegerEntity.class);
            sut.addEntityToAdd(entityToAdd);

            sut.init(injector);
            sut.addToGarbage(entityToAdd);

            // Act
            sut.update(TIMESTAMP);

            // Assert
            assertTrue(sut.getGarbage().isEmpty());
        }
    }

    @Test
    void bufferIsEmptiedAfterInitIsCalled() {
        // Arrange
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        sut.init(injector);

        // Assert
        assertFalse(sut.getBuffer().isPresent());
    }

    @Test
    void bufferTransfersMotionOnInit() {
        // Arrange
        sut.injectMotionApplierFactory(motionApplierFactory);
        sut.setMotion(SPEED, DIRECTION);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setMotion(SPEED, DIRECTION);
    }

    @Test
    void initSetsMotionToDesiredSpeed() {
        // Arrange
        sut.setSpeed(SPEED);
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        sut.init(injector);

        // Assert
        verify(motionApplier).setMotion(SPEED, 0d);
    }

    @Test
    void setMotionApplierIsUsed() {
        // Arrange
        sut.injectMotionApplierFactory(motionApplierFactory);

        // Act
        var mA = sut.getMotionApplier();

        // Assert
        assertEquals(motionApplier, mA);
    }

    @Test
    void setUpdaterIsUsed() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        var u = sut.getUpdater();

        // Assert
        assertEquals(updater, u);
    }

    @Test
    void setRotationSpeedIsUsed() {
        // Arrange
        sut.setRotationSpeed(ROTATION_SPEED);

        // Act
        var rS = sut.getRotationSpeed();

        // Assert
        assertEquals(ROTATION_SPEED, rS);
    }

    @Test
    void addToEntityCollectionCallsAddDynamicEntity() {
        // Arrange
        var entityCollection = mock(EntityCollection.class);

        // Act
        sut.addToEntityCollection(entityCollection);

        // Assert
        verify(entityCollection).addDynamicEntity(sut);
    }

    @Test
    void updateGetsDelegated() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        sut.update(TIMESTAMP);

        // Assert
        verify(updater).update(TIMESTAMP);
    }


    private class DynamicCompositeEntityImpl extends DynamicCompositeEntity {

        private List<YaegerEntity> entitiesToAdd = new ArrayList<>();

        public DynamicCompositeEntityImpl(final Coordinate2D initialLocation) {
            super(initialLocation);
        }

        @Override
        protected void setupEntities() {
            entitiesToAdd.forEach(entity -> addEntity(entity));
        }

        public void addEntityToAdd(YaegerEntity entitiesToAdd) {
            this.entitiesToAdd.add(entitiesToAdd);
        }

        public List<YaegerEntity> getEntities() {
            return entities;
        }

        public void addToGarbage(final Removeable removeable) {
            garbage.add(removeable);
        }

        public List<Removeable> getGarbage() {
            return garbage;
        }
    }
}