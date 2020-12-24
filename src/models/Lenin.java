package models;

import kg2019examples_task4threedimensions.math.Matrix4Factories;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.math.Vector4;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public interface Lenin extends IModel {
    class Pair {
        private double angle;
        private int axis;

        public Pair(double angle, int axis) {
            this.angle = angle;
            this.axis = axis;
        }

        double getAngle() {
            return angle;
        }

        int getAxis() {
            return axis;
        }
    }

    Vector3[] getPath(int dimension, Vector3 start, Vector3 end);

    private Vector3[] getCircle(Vector3 center, float radius) {
        Vector3[] points = new Vector3[30];
        double delta = Math.PI/30;
        for (int i = 1; i <= 30; i++) {
            float x = (float) (radius * Math.cos(delta * (i)));
            float y = (float) (radius * Math.sin(delta * (i)));
            float z = center.getZ();
            points[i - 1] = new Vector3(x, y, z);
        }
        return points;
    }

    private Vector3[] transformCircle(List<Pair> transformation) {

    }

    @Override
    List<PolyLine3D> getLines();
}
