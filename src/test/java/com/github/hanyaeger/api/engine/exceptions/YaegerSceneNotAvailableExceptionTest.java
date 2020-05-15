package com.github.hanyaeger.api.engine.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class YaegerSceneNotAvailableExceptionTest {

    @Test
    void correctMessageIsContructed() {
        // Arrange

        // Act
        var sut = new YaegerSceneNotAvailableException(0);

        // Assert
        var message = sut.getMessage();

        Assertions.assertEquals("Scene 0 is not available. Ensure the scene is added to the game.", message);
    }

    @Test
    void typeIsStoredByTheException() {
        // Arrange
        var sceneType = 1;

        // Act
        var sut = new YaegerSceneNotAvailableException(sceneType);

        // Assert
        var returnedType = sut.getType();

        Assertions.assertEquals(sceneType, returnedType);
    }
}
