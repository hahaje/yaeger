package com.github.hanyaeger.api.engine.annotations;

import com.github.hanyaeger.api.engine.Timer;
import com.github.hanyaeger.api.engine.Updatable;
import com.github.hanyaeger.api.engine.UpdateDelegator;
import com.github.hanyaeger.api.engine.Updater;
import com.github.hanyaeger.api.engine.entities.entity.Location;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.google.inject.Injector;
import javafx.scene.Node;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AnnotationProcessorTest {

    private static final Location LOCATION = new Location(37, 37);

    private AnnotationProcessor sut;

    @BeforeEach
    void setUp() {
        sut = new AnnotationProcessor();
    }

    @Test
    void invokeActivatorsFindsAndInvokesAnnotatedMethod() {
        var entityWithActivators = new EntityWithActivators(LOCATION);

        // Test
        sut.invokeActivators(entityWithActivators);

        // Verify
        Assertions.assertTrue(entityWithActivators.isActivated());
    }

    @Test
    void invokePostActivatorsFindsAndInvokesAnnotatedMethod() {
        var entityWithPostActivators = new EntityWithPostActivators(LOCATION);

        // Test
        sut.invokePostActivators(entityWithPostActivators);

        // Verify
        Assertions.assertTrue(entityWithPostActivators.isPostActivated());
    }

    @Test
    void entityWithAsFirstUpdateGetsAddedToTheUpdatersAsFirst() {
        var firstUpdateDelegatingEntity = new FirstUpdateDelegatingEntity(LOCATION);
        var updater = mock(Updater.class);
        firstUpdateDelegatingEntity.setUpdater(updater);

        // Test
        sut.configureUpdateDelegators(firstUpdateDelegatingEntity);

        // Verify
        verify(updater).addUpdatable(any(Updatable.class), eq(true));
    }

    @Test
    void entityWithExtraUpdateGetsAddedToTheUpdaters() {
        var updateDelegatingEntity = new UpdateDelegatingEntity(LOCATION);
        var updater = mock(Updater.class);
        updateDelegatingEntity.setUpdater(updater);

        // Test
        sut.configureUpdateDelegators(updateDelegatingEntity);

        // Verify
        verify(updater).addUpdatable(any(Updatable.class), eq(false));
    }

    private class FirstUpdateDelegatingEntity extends YaegerEntity implements UpdateDelegator {

        private Updater updater;

        public FirstUpdateDelegatingEntity(Location initialPosition) {
            super(initialPosition);
        }

        @UpdatableProvider(asFirst = true)
        public Updatable provideUpdate() {
            return timestamp -> {
                // Not required here
            };
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        @Override
        public Updater getUpdater() {
            return updater;
        }

        public void setUpdater(Updater updater) {
            this.updater = updater;
        }

        @Override
        public void update(long timestamp) {
            updater.update(timestamp);
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }

        @Override
        public void setOriginX(double x) {
            // Not required here.
        }

        @Override
        public void setOriginY(double y) {
            // Not required here.
        }
    }

    private class UpdateDelegatingEntity extends YaegerEntity implements UpdateDelegator {

        private Updater updater;

        public UpdateDelegatingEntity(Location initialPosition) {
            super(initialPosition);
        }

        @UpdatableProvider
        public Updatable provideUpdate() {
            return timestamp -> {
                // Not required here
            };
        }

        @Override
        public Optional<Node> getGameNode() {
            return null;
        }

        @Override
        public Updater getUpdater() {
            return updater;
        }

        public void setUpdater(Updater updater) {
            this.updater = updater;
        }

        @Override
        public void update(long timestamp) {
            updater.update(timestamp);
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }

        @Override
        public void setOriginX(double x) {
            // Not required here.
        }

        @Override
        public void setOriginY(double y) {
            // Not required here.
        }
    }

    private class EntityWithActivators extends YaegerEntity {

        private boolean activated = false;
        private Node node;

        public EntityWithActivators(Location initialPosition) {
            super(initialPosition);
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Optional<Node> getGameNode() {
            return Optional.of(node);  // Not required here.
        }

        @OnActivation
        public void ActivationMethod() {
            this.activated = true;
        }

        public boolean isActivated() {
            return activated;
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }

        @Override
        public void setOriginX(double x) {
            // Not required here.
        }

        @Override
        public void setOriginY(double y) {
            // Not required here.
        }
    }

    private class EntityWithPostActivators extends YaegerEntity {

        private boolean postActivated = false;
        private Node node;

        public EntityWithPostActivators(Location initialPosition) {
            super(initialPosition);
        }

        public void setNode(Node node) {
            this.node = node;
        }

        @Override
        public void init(Injector injector) {
            // Not required here.
        }

        @Override
        public void remove() {
            // Not required here.
        }

        @Override
        public Optional<Node> getGameNode() {
            return Optional.of(node);  // Not required here.
        }

        @OnPostActivation
        public void ActivationMethod() {
            this.postActivated = true;
        }

        public boolean isPostActivated() {
            return postActivated;
        }

        @Override
        public List<Timer> getTimers() {
            return null;
            // Not required here.
        }

        @Override
        public void setOriginX(double x) {
            // Not required here.
        }

        @Override
        public void setOriginY(double y) {
            // Not required here.
        }
    }
}

