package models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Arrays;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, описывающий фигуру "тор"/"бублик"
 */
public class Torus implements IModel {
    private Vector3 center;
    private float torusRad;
    private float torusThickness;
    private static final int EDGES = 32;

    /**
     * Создает экземпляр тора, используя уравнение окружности.
     * ***
     *
     * @param center         {@link Vector3} координата центра окружности, по которой строится тор
     * @param torusRad       радиус тора
     * @param torusThickness радиус окружности замкнутого цилиндра, из которого собирается тор
     */
    public Torus(Vector3 center, float torusRad, float torusThickness) {
        this.center = center;
        this.torusRad = torusRad;
        this.torusThickness = torusThickness;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> lines = new LinkedList<>();
        Vector3[] centers = new Vector3[EDGES];
        double delta = Math.PI * 2 / EDGES;
        for (int i = 0; i < EDGES; i++) {
            float x = (float) (torusRad * Math.cos(delta * (i)));
            float y = (float) (torusRad * Math.sin(delta * (i)));
            float z = center.getZ();
            centers[i] = new Vector3(x, y, z);
        }
        for (int i = 0; i < EDGES - 1; i++) {
            Vector3 start = new Vector3(centers[i].getX(), centers[i].getY(), centers[i].getZ() );
            Vector3 end = new Vector3(centers[i + 1].getX(), centers[i + 1].getY(),centers[i + 1].getZ());
            TorCylinder cylinder = new TorCylinder(start, end, torusThickness);
            List<PolyLine3D> l = cylinder.getLines();
            lines.addAll(cylinder.getLines());
        }
        return lines;
    }

    private class TorCylinder implements IModel {
        private Vector3 startCenter, endCenter;
        private float radius;
        public static final int POLYGONS = 32;

        public TorCylinder(Vector3 startCenter, Vector3 endCenter, float radius) {
            this.startCenter = startCenter;
            this.endCenter = endCenter;
            this.radius = radius;
        }

        @Override
        public List<PolyLine3D> getLines() {
            List<PolyLine3D> lines = new LinkedList<>();

            double deltaAngle = Math.PI * 2 / POLYGONS;
            Vector3[] startSlice = new Vector3[POLYGONS];
            Vector3[] endSlice = new Vector3[POLYGONS];

            for (int i = 1; i <= POLYGONS; i++) {
                float x = (float) (radius * Math.cos(deltaAngle * (i)));
                float y1 = startCenter.getY();
                float z = (float) (radius * Math.sin(deltaAngle * (i)));
                startSlice[i - 1] = new Vector3(x, y1, z);

                float y2 = endCenter.getY();
                endSlice[i - 1] = new Vector3(x, y2, z);

                Vector3[] sidePolygon = new Vector3[]{
                        new Vector3(x, y1, z),
                        new Vector3(x, y2, z),
                        new Vector3((float) (radius * Math.cos(deltaAngle * (i + 1))),
                                y1,
                                (float) (radius * Math.sin(deltaAngle * (i + 1)))),
                        new Vector3((float) (radius * Math.cos(deltaAngle * (i + 1))),
                                y2,
                                (float) (radius * Math.sin(deltaAngle * (i + 1)))),
                };
                lines.add(new PolyLine3D(Arrays.asList(sidePolygon), true));
            }
            lines.add(new PolyLine3D(Arrays.asList(startSlice), true));
            lines.add(new PolyLine3D(Arrays.asList(endSlice), true));
            /*
             * махия
             */
            return lines;
        }
    }
}
