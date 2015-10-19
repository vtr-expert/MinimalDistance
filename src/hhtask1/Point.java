package hhtask1;

/**
 * Реализует представление точки в двумерном евклидовом пространстве
 * 
 * @author Vladimir Trusov
 */
public class Point implements Comparable<Point> {

    private int x;
    private int y;

    /**
     * Конструктор. Принимает целочисленные координаты точки в качестве параметров 
     * @param x координата X
     * @param y координата Y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Конструктор. Создает объект класса, извлекая координаты точки из строки.
     * @param strPoint строка, содержащая представления координат точки
     * @param delimiter строка-разделитель координат
     * @throws NumberFormatException
     * @throws StrPointFormatException 
     */
    public Point(String strPoint, String delimiter)
            throws NumberFormatException, StrPointFormatException {
        String[] coords = strPoint.split(delimiter);
        if (coords.length == 2) {
            this.x = Integer.valueOf(coords[0]).intValue();
            this.y = Integer.valueOf(coords[1]).intValue();
        } else {
            throw new StrPointFormatException();
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return (this.x == point.x) && (this.y == point.y);
    }

    public int compareTo(Point point) {
        if (this.y < point.y) {
            return -1;
        }
        if (this.y > point.y) {
            return 1;
        }
        if (this.x < point.x) {
            return -1;
        }
        if (this.x > point.x) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
