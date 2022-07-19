package team.brickfire.robot_parts.sensors;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.UARTSensor;

/**
 * <p>Base model for every Sensor</p>
 * <p><i>Follows a variation of the Singleton-Pattern</i></p>
 * @param <T> The UARTSensor the child class will handle
 * @version 1.0
 * @author Team BrickFire
 */
public abstract class Sensor<T extends UARTSensor> {

    protected static Sensor[] portsUsed = new Sensor[]{null, null, null, null};
    protected static Port[] ports = new Port[]{SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4};

    protected final T sensor;

    /**
     * Creates a sensor of the given Typ
     * @param sensor The sensor the class will manage
     */
    public Sensor(T sensor) {
        this.sensor = sensor;
    }

    /**
     * Returns the wanted sensor in the given port
     *
     * @param port Port the sensor is plugged into (1-4)
     * @return The sensor object
     */
    public static Sensor get(int port) {
        throw new NoSensorFoundException("Sensor not connected in port " + port);
    }
}
