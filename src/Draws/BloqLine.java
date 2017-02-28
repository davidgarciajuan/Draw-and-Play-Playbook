package Draws;

import com.itextpdf.awt.geom.Point;
import controller.Frame;
import controller.Sketch;
import javafx.scene.paint.Color;

public class BloqLine extends UniverseShape {

    private double[] endPoint = new double[2];

    
    public BloqLine(double[] startPoint, double[] endPoint, Color color, double strokeWidth) {
        super();
        this.setPosition(startPoint);
        this.endPoint = endPoint;
        this.setStrokeWidth(strokeWidth);
        this.setColor(color);
        this.setFillColor(Color.BLACK);
        this.getProperties().put("starting-x", getPosition()[0]);
        this.getProperties().put("starting-y", getPosition()[1]);
        this.getProperties().put("ending-x", endPoint[0]);
        this.getProperties().put("ending-y", endPoint[1]);
        this.getProperties().put("stroke-width", strokeWidth);
        this.getProperties().put("stroke-color", getColor());
        this.getProperties().put("type", Sketch.ShapeType.BLOQLINE);
    }

    @Override
    public void draw() {
        getLayer().getGraphicsContext2D().setLineWidth(3);
        getLayer().getGraphicsContext2D().setStroke(getColor());
        
        
        
        getLayer().getGraphicsContext2D().strokeLine(getPosition()[0], getPosition()[1], endPoint[0], endPoint[1]);

       normalizacion(getPosition()[0], getPosition()[1], endPoint[0], endPoint[1]);
      
        Sketch.getInstance().getCanvas().getChildren().add(getLayer());
        
        
         
    }

   

   
    // normalización
    private void normalizacion(double inicioX, double inicioY, double finalX, double finalY) {
        double coordx1 = Math.abs(inicioX);
        double coordy1 = Math.abs(inicioY);
        double coordx2 = Math.abs(finalX);
        double coordy2 = Math.abs(finalY);

        Point sumador = new Point(0, 0);
        Point centro = new Point(0, 0);
        Point director = new Point(0, 0);
        Point normalizado = new Point(0, 0);
        Point rotado = new Point(0, 0);
        Point resultadoMas = new Point(0, 0);
        Point resultadoMenos = new Point(0, 0);
        int altoArticulo = 5;

        sumador.x = finalX ;
        sumador.y = finalY ;

        //CENTRO
        centro.x = (coordx1 + coordx2) / 2;
        centro.y = (coordy1 + coordy2) / 2;
        //VECTOR DIRECTOR
        director.x = centro.x - coordx1;
        director.y = centro.y - coordy1;
        //MEDIDA
        double medida = Math.sqrt(((Math.pow(director.x, 2)) + (Math.pow(director.y, 2))));
        //NORMALIZAR VECTOR
        normalizado.x = (int) Math.round(director.x / medida);
        normalizado.y = (int) Math.round(director.y / medida);

        //ROTACIÓN 90 GRADOS
        rotado.x = (normalizado.y * -1);
        rotado.y = normalizado.x;

        //Calcula los puntos de la flecha
        resultadoMas.x = sumador.x + (rotado.x * altoArticulo);
        resultadoMas.y = sumador.y + (rotado.y * altoArticulo);
        resultadoMenos.x = sumador.x - (rotado.x * altoArticulo);
        resultadoMenos.y = sumador.y - (rotado.y * altoArticulo);


        //Dibuja la punta
        getLayer().getGraphicsContext2D().strokeLine(resultadoMas.x, resultadoMas.y,finalX, finalY);
        getLayer().getGraphicsContext2D().strokeLine(resultadoMenos.x, resultadoMenos.y, finalX, finalY);

    }

    

}
