package Draws;

import controller.Frame;
import controller.Sketch;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ImageStair extends UniverseShape {
    private double[] endPoint;

    public ImageStair(double[] startPoint, double[] endPoint, Color strokeColor, Color fillColor, double strokeWidth) {
        super();
        this.setPosition(startPoint);
        this.endPoint = endPoint;
        this.setColor(strokeColor);
        this.setFillColor(fillColor);
        this.setStrokeWidth(strokeWidth);
        this.getProperties().put("starting-x", getPosition()[0]);
        this.getProperties().put("starting-y", getPosition()[1]);
        this.getProperties().put("ending-x", endPoint[0]);
        this.getProperties().put("ending-y", endPoint[1]);
        this.getProperties().put("stroke-width", strokeWidth);
        this.getProperties().put("stroke-color", getColor());
        this.getProperties().put("fill-color", getFillColor());
             this.getProperties().put("type", Sketch.ShapeType.STAIR);
    }

  

    @Override
    public void draw() {
        getLayer().getGraphicsContext2D().setLineWidth(getStrokeWidth());
        getLayer().getGraphicsContext2D().setStroke(getColor());
        getLayer().getGraphicsContext2D().setFill(getFillColor());
        Image img = new Image ("/Images/escalera.png");
        
        getLayer().getGraphicsContext2D().drawImage(img, endPoint[0]-10, endPoint[1]-10, 30, 60);
        Sketch.getInstance().getCanvas().getChildren().add(getLayer());
        
    }

   
}
