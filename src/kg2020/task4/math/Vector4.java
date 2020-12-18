package kg2020.task4.math;

/**
 * описание 3х-мерной точки в виде вектора из 4х компонент для матричных преобразований
 */
public class Vector4 {
    private float[] values;

    /**
     * Конструктор трехмерной точки по четырем составляющим составляющим
     *
     * @param x - составляющая Х
     * @param y - составляющая У
     * @param z - составляющая Z
     * @param w - составляющая W
     */

    public Vector4(float x, float y, float z, float w) {
        this.values = new float[]{x, y, z, w};
    }

    /**
     * Конструктор трехмерной точки по трем составляющим составляющим
     *
     * @param x - составляющая Х
     * @param y - составляющая У
     * @param z - составляющая Z
     */

    public Vector4(float x, float y, float z) {
        this.values = new float[]{x, y, z, 0};
    }

    /**
     * Конструктор трехмерной точки на основе вектора из трех составляющих и составляющей w
     *
     * @param vector - исходный {@link Vector3}
     * @param w - составляющая W
     */

    public Vector4(Vector3 vector, float w) {
        this.values = new float[]{vector.getX(), vector.getY(), vector.getZ(), w};
    }
    /**
     * Конструктор трехмерной точки на основе вектора из трех составляющих
     *
     * @param vector - исходный {@link Vector3}
     */

    public Vector4(Vector3 vector) {
        this.values = new float[]{vector.getX(), vector.getY(), vector.getZ(), 0};
    }

    private static final float EPS = 1e-12f;

    /**
     * Возвращает нормализованный по компоненте W вектор
     *
     * @return новый экземпляр {@link Vector4}
     */
    public Vector4 normalized() {
        if (Math.abs(getW()) < EPS) {
            return new Vector4(getX(), getY(), getZ(), 0);
        }
        return new Vector4(getX() / getW(), getY() / getW(), getZ() / getW(), 1f);
    }

    /**
     * @return Х - составляющую вектора
     */
    public float getX() {
        return values[0];
    }

    /**
     * @return У - составляющую вектора
     */
    public float getY() {
        return values[1];
    }

    /**
     * @return Z - составляющую вектора
     */
    public float getZ() {
        return values[2];
    }

    /**
     * возвращает W-составляющую вектора
     *
     * @return
     */
    public float getW() {
        return values[3];
    }

    /**
     * Возвращает координаты точки по индексу во внутреннем массиве
     *
     * @param index индекс возвращаемой координаты
     * @return значение координаты
     */
    public float at(int index) {
        return values[index];
    }
}
