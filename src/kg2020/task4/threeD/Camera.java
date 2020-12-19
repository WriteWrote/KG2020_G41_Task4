package kg2020.task4.threeD;

import kg2020.task4.math.Vector3;

public class Camera implements ICamera {
    @Override
    public Vector3 w2s(Vector3 point) {
        return new Vector3(point.getX(), point.getY(), point.getZ());
    }
}
