package org.example;


public class Rectangle {
    private final Point upperLeftCorner;
    private final Point lowerRightCorner;
    private final Point upperLeftCornerOfSearchZone;
    private final Point lowerRightCornerOfSearchZone;
    private final int length;
    private final int width;
    private final int index;
    private final int nestingLevel;

    public Rectangle(Point upperLeftCorner, Point lowerRightCorner, int index, int nestingLevel) {
        this.upperLeftCorner = upperLeftCorner;
        this.lowerRightCorner = lowerRightCorner;
        //два поля ниже задают зону для поиска внутри прямоугольника
        this.upperLeftCornerOfSearchZone = new Point(upperLeftCorner.getX() + 2, upperLeftCorner.getY() + 2);
        this.lowerRightCornerOfSearchZone = new Point(lowerRightCorner.getX() - 2, lowerRightCorner.getY() - 2);
        this.index = index;
        this.length = lowerRightCorner.getX() - upperLeftCorner.getX() + 1;
        this.width = lowerRightCorner.getY() - upperLeftCorner.getY() + 1;
        this.nestingLevel = nestingLevel;
    }

    //согласно условию задачи, прямоугольник не может вмещать другие прямоугольники если хотя бы одна его сторона <= 6
    public boolean isSmall() {
        return width <= 6 || length <= 6;
    }

    public Point getUpperLeftCorner() {
        return upperLeftCorner;
    }

    public Point getLowerRightCorner() {
        return lowerRightCorner;
    }

    public Point getUpperLeftCornerOfSearchZone() {
        return upperLeftCornerOfSearchZone;
    }

    public Point getLowerRightCornerOfSearchZone() {
        return lowerRightCornerOfSearchZone;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getIndex() {
        return index;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }
}
