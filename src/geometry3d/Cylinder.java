package geometry3d;

import exceptions.InvalidFigureException;
import geometry2d.Figure;
import java.io.Serializable;

public class Cylinder implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Figure base;
    private final double height;

    public Cylinder(Figure base, double height) {
        if (base == null) {
            throw new InvalidFigureException("Cylinder base must not be null");
        }
        if (!Double.isFinite(height) || height <= 0) {
            throw new InvalidFigureException("Invalid height: " + height);
        }
        this.base = base;
        this.height = height;
    }

    public Figure getBase()   { return base; }
    public double getHeight() { return height; }

    public double volume() { return base.area() * height; }

    @Override
    public String toString() { return "Cylinder(base=" + base + ", height=" + height + ")"; }
}