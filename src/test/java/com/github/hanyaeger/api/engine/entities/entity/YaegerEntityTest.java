package com.github.hanyaeger.api.engine.entities.entity;

import com.github.hanyaeger.api.engine.Timer;
import com.google.inject.Injector;
import javafx.geometry.BoundingBox;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class YaegerEntityTest {

    private static final Location LOCATION = new Location(37, 37);
    private static final double SCENE_WIDTH = 37d;
    private static final double SCENE_HEIGHT = 42d;
    private static final double ENTITY_WIDTH = 200d;
    private static final double ENTITY_HEIGHT = 100d;
    private static final BoundingBox BOUNDING_BOX = new BoundingBox(0, 0, 10, 10);

    private YaegerEntityImpl sut;
    private Node node;
    private Injector injector;
    private BoundingBox boundingBox;
    private Scene scene;

    @BeforeEach
    void setup() {
        sut = new YaegerEntityImpl(LOCATION);
        injector = mock(Injector.class);
        node = mock(Node.class, withSettings().withoutAnnotations());
        sut.setNode(Optional.of(node));
        scene = mock(Scene.class);
        boundingBox = mock(BoundingBox.class);
        when(node.getBoundsInLocal()).thenReturn(boundingBox);
        when(boundingBox.getWidth()).thenReturn(ENTITY_WIDTH);
        when(boundingBox.getHeight()).thenReturn(ENTITY_HEIGHT);
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);
    }

    @Test
    void getTimersReturnsAnEmptyCollection() {
        // Arrange

        // Act
        List<Timer> timers = sut.getTimers();

        // Assert
        assertNotNull(timers);
        assertTrue(timers.isEmpty());
    }

    @Test
    void initCallsSetOpacity() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(node).setOpacity(1);
    }

    @Test
    void initCallsSetVisible() {
        // Arrange

        // Act
        sut.init(injector);

        // Assert
        verify(node).setVisible(true);
    }

    @Test
    void activateCallsSetCursor() {
        // Arrange
        sut.init(injector);

        // Act
        sut.activate();

        // Assert
        verify(scene).setCursor(Cursor.DEFAULT);
    }

    @Test
    void placeOnSceneCallsSetXWithInitialLocation() {
        // Arrange
        sut.init(injector);

        // Act
        sut.placeOnScene();

        // Assert
        Assertions.assertEquals(LOCATION.getX(), sut.getOriginX());
    }

    @Test
    void placeOnSceneCallsSetYWithInitialLocation() {
        // Arrange
        sut.init(injector);

        // Act
        sut.placeOnScene();

        // Assert
        Assertions.assertEquals(LOCATION.getY(), sut.getOriginY());
    }

    @Test
    void setVisibleDelegatesToNode() {
        // Arrange

        // Act
        sut.setVisible(false);

        // Assert
        verify(node).setVisible(false);
    }

    @Test
    void removeCallsSetVisibleFalseOnGameNode() {
        // Arrange

        // Act
        sut.remove();

        // Assert
        verify(node).setVisible(false);
    }

    @Test
    void setAnchorPointBedoreNodeIsSetStoresAnchorPoint() {
        // Arrange
        sut.setNode(Optional.empty());

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_RIGHT);

        // Assert
        Assertions.assertEquals(AnchorPoint.CENTER_RIGHT, sut.getAnchorPoint());
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_LEFT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_LEFT);

        // Assert
        verify(node).setTranslateX(0);
        verify(node).setTranslateY(0);
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForTOP_RIGHT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.TOP_RIGHT);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForLEFT_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_LEFT);

        // Assert
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForCENTER_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForRIGHT_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.CENTER_RIGHT);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
        verify(node).setTranslateY(-(ENTITY_HEIGHT / 2));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_CENTER() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_CENTER);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH / 2));
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_RIGHT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_RIGHT);

        // Assert
        verify(node).setTranslateX(-(ENTITY_WIDTH));
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    @Test
    void setAnchorPointOnNodeAppliesTranslationsForBOTTOM_LEFT() {
        // Arrange

        // Act
        sut.setAnchorPoint(AnchorPoint.BOTTOM_LEFT);

        // Assert
        verify(node).setTranslateY(-(ENTITY_HEIGHT));
    }

    @Test
    void getSceneWidthReturnsSceneWidthFromNode() {
        // Arrange
        when(node.getScene()).thenReturn(scene);
        when(scene.getWidth()).thenReturn(SCENE_WIDTH);

        // Act
        double actual = sut.getSceneWidth();

        // Assert
        assertEquals(SCENE_WIDTH, actual);
    }

    @Test
    void getSceneHeightReturnsSceneHeightFromNode() {
        // Arrange
        when(node.getScene()).thenReturn(scene);
        when(scene.getHeight()).thenReturn(SCENE_HEIGHT);

        // Act
        double actual = sut.getSceneHeight();

        // Assert
        assertEquals(SCENE_HEIGHT, actual);
    }

    @Test
    void getBoundsDelegatesToNode() {
        // Arrange
        when(node.getBoundsInLocal()).thenReturn(BOUNDING_BOX);

        // Act
        var actual = sut.getNonTransformedBounds();

        // Assert
        assertEquals(BOUNDING_BOX, actual);
    }

    private class YaegerEntityImpl extends YaegerEntity {

        private Optional<Node> node;
        private double x;
        private double y;

        public YaegerEntityImpl(Location initialPosition) {
            super(initialPosition);
        }

        @Override
        public Optional<Node> getGameNode() {
            return node;
        }

        public void setNode(Optional<Node> node) {
            this.node = node;
        }

        public double getOriginX() {
            return x;
        }

        public double getOriginY() {
            return y;
        }

        @Override
        public void setOriginX(double x) {
            this.x = x;
        }

        @Override
        public void setOriginY(double y) {
            this.y = y;
        }
    }
}
