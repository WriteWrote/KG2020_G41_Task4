package models;

import kg2019examples_task4threedimensions.math.Matrix4;
import kg2019examples_task4threedimensions.math.Matrix4Factories;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.math.Vector4;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Lenin implements IModel {
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

    abstract Vector3[] getPath(int dimension);

    Vector3[] getCircle(Vector3 center, float radius, int dimension) {
        Vector3[] points = new Vector3[dimension];
        double delta = Math.PI / dimension;
        for (int i = 1; i <= dimension; i++) {
            float x = (float) (radius * Math.cos(delta * (i)));
            float y = (float) (radius * Math.sin(delta * (i)));
            float z = center.getZ();
            points[i - 1] = new Vector3(x, y, 0.0f);
/*
            Vector4 v = new Vector4(x, y, z);
            points[i - 1] = new Vector3(v.getX(), v.getY(),
                    (float) (center.getZ() + radius * Math.cos(delta * i)));*/
        }

        return points;
    }

    Vector3[] transformCircle(List<Pair> transformation, Vector3[] circlePoints) {
        for (Pair p :
                transformation) {
            Matrix4 m = Matrix4Factories.rotationXYZ(p.angle, p.axis);
            for (int i = 0; i < circlePoints.length; i++) {
                Vector4 v4 = m.mul(new Vector4(circlePoints[i]));
                circlePoints[i] = new Vector3(v4.getX(), v4.getY(), v4.getZ());
            }
        }
        return circlePoints;
    }
    Vector3 transformCircle(List<Pair> transformation, Vector3 circlePoints) {
        for (Pair p :
                transformation) {
            Matrix4 m = Matrix4Factories.rotationXYZ(p.angle, p.axis);
                Vector4 v4 = m.mul(new Vector4(circlePoints));
                circlePoints = new Vector3(v4.getX(), v4.getY(), v4.getZ());
        }
        return circlePoints;
    }
}
