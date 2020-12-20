package models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * попытка в цилиндр
 */

public class Cylinder implements IModel {
    private Vector3 startCenter, endCenter;
    private float radius;

    public Cylinder(Vector3 startCenter, Vector3 endCenter, float radius) {
        this.startCenter = startCenter;
        this.endCenter = endCenter;
        this.radius = radius;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
// один срез
        double deltaAngle = Math.PI * 2 / 32;
        Vector3[] startSlice = new Vector3[32];
        for (int i = 1; i <= 32; i++) {
// заполняем массив точками круга
            float x = (float) (radius * Math.cos(deltaAngle * (i)));
            float y = (float) (radius * Math.sin(deltaAngle * (i)));
            float z = startCenter.getZ();
            startSlice[i - 1] = new Vector3(x, y, z);
        }
        lines.add(new PolyLine3D(Arrays.asList(startSlice), true));
        // второй срез
        /*
        lines.add(new PolyLine3D(Arrays.asList(new Vector3[]{
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())
        }), true));
         */

        /*
         * махия
         */
        return lines;
    }
}
