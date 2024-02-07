package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rectangle {
    private final Point upperLeftCorner;
    private final Point lowerRightCorner;
    private final int length;
    private final int width;
    private final int index;
    private final int nestingLevel;

    public Rectangle(Point upperLeftCorner, Point lowerRightCorner, int index, int nestingLevel) {
        this.upperLeftCorner = upperLeftCorner;
        this.lowerRightCorner = lowerRightCorner;
        this.index = index;
        this.length = lowerRightCorner.getX() - upperLeftCorner.getX();
        this.width = lowerRightCorner.getY() - upperLeftCorner.getY();
        this.nestingLevel = nestingLevel;
    }

    //согласно условию задачи, прямоугольник не может вмещать другие прямоугольники если хотя бы одна его сторона <= 6
    public boolean isSmall(){
        return width <= 6 || length <=6;
    }




}
