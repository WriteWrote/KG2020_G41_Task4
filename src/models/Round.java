package models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;

public interface Round extends IModel {
    Vector3[] getPath(int dimension, float torusThickness);
    Vector3[] getCircle(int dimension, float z);
}
