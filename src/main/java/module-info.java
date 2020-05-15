module hanyaeger.api {
    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.media;
    requires transitive javafx.graphics;
    requires transitive com.google.guice;

    exports com.github.hanyaeger.api.engine;
    exports com.github.hanyaeger.api.engine.annotations;
    exports com.github.hanyaeger.api.engine.entities;
    exports com.github.hanyaeger.api.engine.scenes;
    exports com.github.hanyaeger.api.engine.scenes.delegates;
    exports com.github.hanyaeger.api.engine.media.repositories;
    exports com.github.hanyaeger.api.engine.debug;
    exports com.github.hanyaeger.api.engine.entities.entity.sprite;
    exports com.github.hanyaeger.api.engine.entities.tilemap;
    exports com.github.hanyaeger.api.engine.entities.entity;
    exports com.github.hanyaeger.api.engine.entities.entity.shape.text;
    exports com.github.hanyaeger.api.engine.entities.entity.shape.rectangle;
    exports com.github.hanyaeger.api.engine.entities.entity.motion;
    exports com.github.hanyaeger.api.engine.scenes.splash;
    exports com.github.hanyaeger.api.engine.entities.entity.collisions;
    exports com.github.hanyaeger.api.engine.entities.entity.events.userinput;
    exports com.github.hanyaeger.api.engine.styles;
    exports com.github.hanyaeger.api.engine.media.audio;

    exports com.github.hanyaeger.api.guice.factories;

    exports com.github.hanyaeger.api.javafx.image;
    exports com.github.hanyaeger.api.javafx.debug;
    exports com.github.hanyaeger.api.javafx.animationtimer;

    opens fonts.avenirnext;
    opens fonts.avenirnextcondensed;
    opens images;
}
