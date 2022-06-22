package team.brickfire.robotParts.sensors.doble_color;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import team.brickfire.robotParts.sensors.ColorSensor;

public class MiddleScanner {
    
    private final ColorMap laundryMap;
    private final ColorMap roomBlockMap;
    
    private final ColorSensor sensorLeft;
    private final ColorSensor sensorRight;

    public MiddleScanner() {
        laundryMap = new LaundryColorMap();
        roomBlockMap = new RoomBlockColorMap();
        sensorLeft = new ColorSensor(new EV3ColorSensor(SensorPort.S2));
        sensorRight = new ColorSensor(new EV3ColorSensor(SensorPort.S3));
    }
    
    private int getColor(ColorMap map) {
        int leftValue = map.getPrioritisedValue(map.mappedValues(sensorLeft.accurateColorID(5),
                sensorLeft.accurateColorID(5), sensorLeft.accurateColorID(5)));

        int rightValue = map.getPrioritisedValue(map.mappedValues(sensorRight.accurateColorID(5),
                sensorRight.accurateColorID(5), sensorRight.accurateColorID(5)));
        
        return map.valueWithMaxPriority(leftValue, rightValue);
    }
    
    public int laundryColor() {
        return getColor(laundryMap);
    }
    
    public int roomBlockColor() {
        return getColor(roomBlockMap);
    } 
}
