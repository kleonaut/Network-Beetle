package com.github.kleonaut.network_beetle;

import java.awt.AWTException;
import java.io.IOException;

public class Main {

    // TODO: read conditions from file
    // TODO: create a UI
    // TODO: create a better, transparent icon
    // NOTE: icon image may not be found after exporting to JAR

    public static void main(String[] args) {
        try {
            Application app = new Application();
            app.run();
        } catch (InterruptedException | IOException | AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
