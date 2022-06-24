package team.brickfire.robotParts.sensors.double_color;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
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
        int[] leftMeasurements = new int[15];
        int[] rightMeasurements = new int[leftMeasurements.length];
        for (int i = 0; i < leftMeasurements.length; i++) {
            leftMeasurements[i] = sensorLeft.getColorID();
            rightMeasurements[i] = sensorRight.getColorID();
        }
        int leftValue = map.getPrioritisedValue(map.mappedValues(leftMeasurements));
        int rightValue = map.getPrioritisedValue(map.mappedValues(rightMeasurements));
        
        return map.valueWithMaxPriority(leftValue, rightValue);
    }
    
    public int laundryColor() {
        return getColor(laundryMap);
    }
    
    public int roomBlockColor() {
        return getColor(roomBlockMap);
    } 
}
