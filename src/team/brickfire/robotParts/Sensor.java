package team.brickfire.robotParts;

import lejos.hardware.sensor.UARTSensor;

/**
 * Base model for every Sensor
 * @param <T> The UARTSensor the child class will handle
 * @version 1.0
 * @author Team BrickFire
 */
public abstract class Sensor<T extends UARTSensor> {
    protected final T sensor;

    /**
     * Creates a sensor of the given Typ
     * @param sensor The sensor the class will manage
     */
    public Sensor(T sensor) {
        this.sensor = sensor;
    }

}
