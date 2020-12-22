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

        Vector3[] circles = new Vector3[EDGES];
        for (int i = 1; i <= EDGES; i++) {
            float x = (float) (torusRad * Math.cos(delta * (i)));
            float y = (float) (torusRad * Math.sin(delta * (i)));
            float z = torusCenter.getZ();
            circles[i - 1] = new Vector3(x, y, z);
        }
        // просто для проверки строится траектория внутренней окружности торуса
        lines.add(new PolyLine3D(Arrays.asList(circles), true));

        //список векторов по направлению линии
        List<Vector3> angleVectors = new LinkedList<>();
        for (int o = 0; o < EDGES - 1; o++) {
            float x = circles[o + 1].getX() - circles[o].getX();
            float y = circles[o + 1].getY() - circles[o].getY();
            float z = circles[o + 1].getZ() - circles[o].getZ();
            angleVectors.add(new Vector3(x, y, z));
        }
        angleVectors.add(new Vector3(circles[EDGES - 1].getX() - circles[0].getX(),
                circles[EDGES - 1].getY() - circles[0].getY(),
                circles[EDGES - 1].getZ() - circles[0].getZ()));
        // подготовительные данные собраны:
        // центры кругов circles,
        // векторы направляющей прямой angleVectors,
        // дельта угол delta

        for (int i = 0; i < EDGES; i++) {
            // разбираем один круг
            Vector3[] slice = new Vector3[EDGES];
            for (int j = 1; j <= EDGES; j++) {
                // строим базовые точки круга
                float x = (float) (torusRad * Math.cos(delta * i) + torusThickness * Math.cos(delta * (j)));
                float y = (float) (torusRad * Math.sin(delta * i) + torusThickness * Math.sin(delta * (j)));
                float z = circles[i].getZ();

                // поворот на 90 градусов
                Vector4 v = new Vector4(x, y, z);
                Matrix4 m = Matrix4Factories.rotationXYZ(Math.PI / 2, 1);
                v = m.mul(v);

                // прибавка для смещения по иксу
                float dx = circles[i].getX() - v.getX();

                //slice[j - 1] = new Vector3(v.getX(), v.getY(), (float) (centers[i].getZ() + radius * Math.cos(delta * j)));

                // завершаем создание круга, ориентированного по ходу линии
                slice[j - 1] = new Vector3(v.getX() + dx, v.getY(),
                        (float) (circles[i].getZ() + torusThickness * Math.cos(delta * j)));
            }
            // берем вектор для этого круга, берем вектор самого круга
            Vector3 vec = angleVectors.get(i);
            Vector3 cv = circles[i];
            System.out.println("vector: " + vec.getX() + " ; " + vec.getY() + " ; " + vec.getZ() +
                    "   circle: " + cv.getX() + " ; " + cv.getY() + " ; " + cv.getZ());
            // вычисление угла между направлящим вектором прямой и нормалью текущей окружности
            double angle = Math.acos((vec.getX() * cv.getX() + vec.getY() * cv.getY() + vec.getZ() * cv.getZ()) /
                    (Math.sqrt(vec.getX() * vec.getX() + vec.getY() * vec.getY() + vec.getZ() * vec.getZ()) *
                            Math.sqrt(cv.getX() * cv.getX() + cv.getY() * cv.getY() + cv.getZ() * cv.getZ())
                    ));
            System.out.println("angle: " + angle);
            // матрица поворота на угол по тору: для каждого круга ++delta
            Matrix4 m = Matrix4Factories.rotationXYZ(angle, 2);
            // поворот вокруг оси z для всех точек круга, лежащих в slice
            for (int j = 0; j < EDGES; j++) {
                // перезапись точек окружности
                Vector4 v4 = m.mul(new Vector4(slice[j]));
                slice[j] = new Vector3(v4.getX(), v4.getY(), v4.getZ());
            }
            // создание полилинии одного круга и добавление ее в список линий
            PolyLine3D line = new PolyLine3D(Arrays.asList(slice), true);
            lines.add(line);
        }
        return lines;
    }
}
