package com.github.hanyaeger.api.engine.entities.entity.shape.text;

import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.EntityCollection;
import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.motion.DefaultMotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.EntityMotionInitBuffer;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplier;
import com.github.hanyaeger.api.engine.entities.entity.motion.MotionApplierType;
import com.github.hanyaeger.api.guice.factories.MotionApplierFactory;
import com.google.inject.Injector;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class DynamicTextEntityTest {

    private final long TIMESTAMP = 0L;

    private static final String YAEGER = "Yaeger";
    private final static int X_POSITION = 37;
    private final static int Y_POSITION = 37;
    private final static Coordinate2D DEFAULT_LOCATION = new Coordinate2D(X_POSITION, Y_POSITION);
    public static final int ROTATION_SPEED = 37;
    public static final double SPEED = 37d;
    public static final double DIRECTION = 42d;

    private DynamicTextEntityImpl sut;
    private Injector injector;
    private Text text;

    @BeforeEach
    void setup() {
        sut = new DynamicTextEntityImpl(DEFAULT_LOCATION);
        text = mock(Text.class);
        sut.setText(YAEGER);
        sut.setShape(text);
        injector = mock(Injector.class);
    }

    @Test
    void bufferIsSetInConstructor() {
        // Arrange

        // Act
        Optional<EntityMotionInitBuffer> buffer = sut.getBuffer();

        // Verify
        Assertions.assertTrue(buffer.isPresent());
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
    }

    @Test
    void setUpdaterIsUsed() {
        // Arrange
        var updater = mock(Updater.class);
        sut.setUpdater(updater);

        // Act
        var u = sut.getUpdater();

        // Verify
        Assertions.assertEquals(updater, u);
    }

    @Test
    void setRotationSpeedIsUsed() {
        // Arrange
        sut.setRotationSpeed(ROTATION_SPEED);

        // Act
        var rS = sut.getRotationSpeed();

        // Verify
        Assertions.assertEquals(ROTATION_SPEED, rS);
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

    private class DynamicTextEntityImpl extends DynamicTextEntity {
        public DynamicTextEntityImpl(Coordinate2D location) {
            super(location);
        }
    }
}


