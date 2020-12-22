package models;

import kg2019examples_task4threedimensions.math.Matrix4;
import kg2019examples_task4threedimensions.math.Matrix4Factories;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.math.Vector4;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import javax.swing.text.EditorKit;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, описывающий фигуру "тор"/"бублик"
 */
public class Torus implements IModel {
    private Vector3 torusCenter;
    private float torusRad;
    private float torusThickness;
    private static final int EDGES = 32;

    /**
     * Создает экземпляр тора, используя уравнение окружности.
     * ***
     *
     * @param torusCenter    {@link Vector3} координата центра окружности, по которой строится тор
     * @param torusRad       радиус тора
     * @param torusThickness радиус окружности замкнутого цилиндра, из которого собирается тор
     */
    public Torus(Vector3 torusCenter, float torusRad, float torusThickness) {
        this.torusCenter = torusCenter;
        this.torusRad = torusRad;
        this.torusThickness = torusThickness;
    }

    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> lines = new LinkedList<>();
        double delta = Math.PI * 2 / EDGES;

        Vector3[] centers = new Vector3[EDGES];
        for (int i = 1; i <= EDGES; i++) {
            float x = (float) (torusRad * Math.cos(delta * (i)));
            float y = (float) (torusRad * Math.sin(delta * (i)));
            float z = torusCenter.getZ();
            /*Matrix4 m = Matrix4Factories.rotationXYZ(Math.PI / 2, 1);
            Vector4 v = new Vector4(x, y, z);
            v = m.mul(v);
            m = Matrix4Factories.rotationXYZ(-delta, 2);
            v = m.mul(v);
            centers[i - 1] = new Vector3(v.getX(), v.getY(), v.getZ());

             */
            centers[i - 1] = new Vector3(x, y, z);
        }
        // просто для проверки строится траектория внутренней окружности торуса
        lines.add(new PolyLine3D(Arrays.asList(centers), true));

        for (int i = 0; i < EDGES; i++) {
            Vector3[] slice = new Vector3[EDGES];
            //double delta = Math.PI * 2 / EDGES;
            float radius = torusThickness;
            for (int j = 1; j <= EDGES; j++) {
                float x = (float) (torusRad * Math.cos(delta * i) + radius * Math.cos(delta * (j)));
                float y = (float) (torusRad * Math.sin(delta * i) + radius * Math.sin(delta * (j)));
                float z = centers[i].getZ();
                Vector4 v = new Vector4(x, y, z);

                Matrix4 m = Matrix4Factories.rotationXYZ(Math.PI / 2, 1);
                v = m.mul(v);
                float dx = centers[i].getX() - v.getX();
                //float dz = (centers[i].getZ() - v.getZ())/EDGES;

                //slice[j - 1] = new Vector3(v.getX(), v.getY(), (float) (centers[i].getZ() + radius * Math.cos(delta * j)));
                slice[j - 1] = new Vector3(v.getX() + dx, v.getY(), (float) (centers[i].getZ() + radius * Math.cos(delta * j)));
            }
            List<Vector3> angleVectors = new LinkedList<>();
            for (int o = 0; o < EDGES - 1; o++) {
                float x = centers[o + 1].getX() - centers[o].getX();
                float y = centers[o + 1].getY() - centers[o].getY();
                float z = centers[o + 1].getZ() - centers[o].getZ();
                angleVectors.add(new Vector3(x, y, z));
            }
            angleVectors.add(new Vector3(centers[EDGES - 1].getX() - centers[0].getX(),
                    centers[EDGES - 1].getY() - centers[0].getY(),
                    centers[EDGES - 1].getZ() - centers[0].getZ()));

            for (int j = 0; j < EDGES; j++) {
                Vector3 vec = angleVectors.get(j);
                Vector3 cv = centers[i];
                double angle = Math.acos((vec.getX() * cv.getX() + vec.getY() * cv.getY() + vec.getZ() * cv.getZ()) /
                        (Math.sqrt(vec.getX() * vec.getX() + vec.getY() * vec.getY() + vec.getZ() * vec.getZ()) *
                                Math.sqrt(cv.getX() * cv.getX() + cv.getY() * cv.getY() + cv.getZ() * cv.getZ())
                        ));
                Matrix4 m = Matrix4Factories.rotationXYZ(angle, 2);
                for (int k=0; k<EDGES; k++) {
                    Vector4 v4 = m.mul(new Vector4(slice[k]));
                    slice[k] = new Vector3(v4.getX(), v4.getY(), v4.getZ());
                }

            }

            PolyLine3D line = new PolyLine3D(Arrays.asList(slice), true);
            lines.add(line);
        }

        /* добавили массив центров
         * далее будем делать вектора
         * вектором может служить просто Vector3, так как нужны только три составляющие
         * и их будет в два раза меньше, чем просто точек
         * вектор строится как v = {x2-x1, y2-y1, z2-z1}
         */
        /*
        List<Vector3> angleVectors = new LinkedList<>();
        for (int i = 0; i < EDGES - 1; i++) {
            float x = centers[i + 1].getX() - centers[i].getX();
            float y = centers[i + 1].getY() - centers[i].getY();
            float z = centers[i + 1].getZ() - centers[i].getZ();
            angleVectors.add(new Vector3(x, y, z));
        }
        angleVectors.add(new Vector3(centers[EDGES - 1].getX() - centers[0].getX(),
                centers[EDGES - 1].getY() - centers[0].getY(),
                centers[EDGES - 1].getZ() - centers[0].getZ()));
         */
        /* теперь есть список векторов, по которым будем вычислять углы
         * от стартового до последнего, от последней точки к начальной точке
         * подразумевается, что последний вектор замыкает контур линии
         */

        //List<Circle> circles = new LinkedList<>();
        /*
        for (int i = 0; i < EDGES; i++) {
            // просто заполняем обычными кругами, каждый на своей точке в базовом положении
//            Circle c = new Circle(centers[i], torusThickness);
            // теперь поворачиваем по игреку на 90 градусов
            //Matrix4 m = Matrix4Factories.rotationXYZ(Math.PI / 2, 1);
            // вот тут надо взять Vector3 точку центра круга и повернуть ее
            //Vector4 newCenter = m.mul(new Vector4(c.getCenter()));
            //c.setCenter(new Vector3(newCenter.getX(), newCenter.getY(), newCenter.getZ()));
            //circles.add(c);
        }
        // добавляем в lines их линии - просто для проверки
        for (Circle c :
                circles) {
            lines.addAll(c.getLines());
        }
*/
        /*
        for (int i = 0; i < EDGES - 1; i++) {
            Vector3 start = new Vector3(centers[i].getX(), centers[i].getY(), centers[i].getZ());
            Vector3 end = new Vector3(centers[i + 1].getX(), centers[i + 1].getY(), centers[i + 1].getZ());

            Cylinder cylinder = new Cylinder(start, end, torusThickness);
            List<PolyLine3D> l = cylinder.getLines();
            lines.addAll(cylinder.getLines());
        }
        */

        return lines;
    }

    private List<Vector3> getCircles(List<Vector3> centerPoints) {
        List<Vector3> result = new LinkedList<>();


        return result;
    }
}
