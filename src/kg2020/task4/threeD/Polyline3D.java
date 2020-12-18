package kg2020.task4.threeD;

import kg2020.task4.math.Vector3;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс, описывающий линию из точек в трехмерном пространстве
 */
public class Polyline3D {
    private List<Vector3> points;

    /**
     * Конструктор линии в трехмерном пространстве
     * @param points - LinkedList из {@link Vector3}
     */
    public Polyline3D(List<Vector3> points) {
        this.points = new LinkedList<>();
        this.points.addAll(points);
    }

    /**
     * возвращает список точек в трехмерном пространстве
     * @return LinkedList из {@link Vector3}
     */
    public List<Vector3> getPoints() {
        return new LinkedList<>(points);
    }
}
