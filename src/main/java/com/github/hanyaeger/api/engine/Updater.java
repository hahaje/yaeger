package com.github.hanyaeger.api.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Updater} contains a {@link List} of {@link Updatable} to which the {@code update} method
 * will be delegated.
 */
public class Updater implements Updatable, Clearable {

    private List<Updatable> updatables = new ArrayList<>();
    private boolean clearUpdatables = false;

    /**
     * Add an {@link Updatable} to this {@link Updater}. The {@link Updatable} will
     * be added to a {@link List}, which will be iterated on each {@code update}.
     *
     * @param updatable the {@link Updatable} to be added
     */
    public void addUpdatable(final Updatable updatable) {
        this.addUpdatable(updatable, false);
    }

    /**
     * Add an {@link Updatable} to this {@link Updater}. The {@link Updatable} will
     * be added to a {@link List}, which will be iterated on each {@code update}.
     *
     * @param updatable the {@link Updatable} to be added
     * @param asFirst   Add an {@link Updatable} to this {@link Updater} as the first element and thus
     *                  be execute first during an {@code update}
     */
    public void addUpdatable(final Updatable updatable, boolean asFirst) {
        if (asFirst) {
            updatables.add(0, updatable);
        } else {
            updatables.add(updatable);
        }
    }

    @Override
    public void update(final long timestamp) {
        if (!clearUpdatables) {
            updatables.forEach(updatable -> updatable.update(timestamp));
        } else {
            updatables.clear();
        }
    }

    @Override
    public void clear() {
        clearUpdatables = true;
    }
}
