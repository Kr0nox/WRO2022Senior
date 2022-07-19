package team.brickfire.actions.circuit_drive;

import java.util.Objects;

/**
 * <p>Representation of a two-dimensional vector</p>
 *
 * @author Team BrickFire
 * @version 1.0
 */
class Vector2D {

    private final double x;
    private final double y;

    /**
     * <p>Creates a vector</p>
     *
     * @param x X-coordinate of this vector
     * @param y X-coordinate of this vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * <p>Gets the x-coordinate of this vector</p>
     *
     * @return X-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * <p>Gets the y-coordinate of this vector</p>
     *
     * @return Y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * <p>Gets the length of this vector</p>
     *
     * @return Length of vector
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * <p>Subtracts another vector from this one using {@link #difference}(this, v)</p>
     *
     * @param v Vector to subtract from this one
     * @return Result of subtraction
     */
    public Vector2D subtract(Vector2D v) {
        return Vector2D.difference(this, v);
    }

    /**
     * <p>Subtracts one vector from this another. <br><i>v1 - v2</i></p>
     *
     * @param v1 Vector to subtract from
     * @param v2 Vector that gets subtracted
     * @return Result of subtraction
     */
    public static Vector2D difference(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * <p>Returns the normalized vector of this one</p>
     *
     * @return Normalized vector
     */
    public Vector2D normalized() {
        return new Vector2D(x / length(), y / length());
    }

    /**
     * <p>Calculates the angle between two vectors</p>
     *
     * @param v1 First vector
     * @param v2 Second vector
     * @return Angle between the vectors
     */
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
