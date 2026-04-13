package com.narxoz.rpg.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Central publisher for all dungeon encounter events.
 * Stores observers and notifies them whenever a game event occurs.
 */
public class EventManager {

    private final List<GameObserver> observers = new ArrayList<>();

    public void subscribe(GameObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}