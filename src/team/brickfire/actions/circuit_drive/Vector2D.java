package team.brickfire.actions.circuit_drive;

import java.util.Objects;

/**
 * Representation of a two-dimensional vector
 *
 * @author Team BrickFire
 * @version 1.0
 */
class Vector2D {

    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    public Vector2D subtract(Vector2D v) {
        return Vector2D.difference(this, v);
    }

    public static Vector2D difference(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y -v2.y);
    }

    public Vector2D normalized() {
        return new Vector2D(x/length(), y/length());
    }

    public static double angle(Vector2D v1, Vector2D v2) {
        double dotProduct = v1.x * v2.x + v1.y * v2.y;
        double value = dotProduct / (v1.length() * v2.length());
        return Math.toDegrees(Math.acos(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof  Vector2D) {
            Vector2D vector2D = (Vector2D) o;
            return Double.compare(vector2D.x, x) == 0 && Double.compare(vector2D.y, y) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
