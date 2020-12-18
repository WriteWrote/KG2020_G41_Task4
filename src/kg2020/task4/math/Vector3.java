package kg2020.task4.math;

/**
 * Класс для описания аналога RealPoint в трехмерном пространства
 */
public class Vector3 {
    private float x, y, z;

    /**
     * Конструктор трехмерной точки
     * @param x - составляющая Х
     * @param y - составляющая У
     * @param z - составляющая Z
     */

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     * @return Х - составляющую
     */
    public float getX() {
        return x;
    }
    /**
     *
     * @return У - составляющую
     */
    public float getY() {
        return y;
    }
    /**
     *
     * @return Z - составляющую
     */
    public float getZ() {
        return z;
    }
}
