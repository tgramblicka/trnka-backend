package com.trnka.backend.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

@Stateless
public class DeviceEventControl {
    private final static Logger LOGGER = Logger.getLogger(DeviceEventControl.class.getName());

    public void deviceKeyPresses(String keycode) {
        LOGGER.log(Level.INFO, "Key {0} on device pressed", keycode);
    }

}
