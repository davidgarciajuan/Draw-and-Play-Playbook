package controller;

import Draws.ImageAtacante6;
import Draws.ImageDefensor2;
import Draws.Line;
import Draws.Shape;
import Draws.ImageStair;
import Draws.ImageCesta;
import Draws.ImageDefensor;
import Draws.ImageDefensor1;
import Draws.ImageAtacante4;
import Draws.ImageBall;
import Draws.ImageAtacante3;
import Draws.ImageAtacante;
import Draws.ImageDefensor4;
import Draws.BloqLine;
import Draws.ImageDefensor8;
import Draws.ImageDefensor7;
import Draws.ImageDefensor5;
import Draws.ImageCone;
import Draws.ImageAtacante1;
import Draws.ImageAtacante9;
import Draws.ImageBasket;
import Draws.DrillLine;
import Draws.ImageAtacante2;
import Draws.Rectangle;
import Draws.ImageDefensor6;
import Draws.ImageDefensor3;
import Draws.ImageAtacante8;
import Draws.ImageAtacante7;
import Draws.Circle;
import Draws.ImageAtacante5;
import Draws.ImageCoach;
import Draws.ShadedLine;
import Draws.ImageDefensor9;
import Draws.ShootLine;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import main.Main;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sketch {

    private static Sketch instance;
    private Color fillColor = Color.BLACK;
    private Color strokeColor = Color.BLACK;
    private boolean moveFlag;
    private boolean resizeFlag;
    private boolean deleteFlag;
    private double mainWidth = 514;
    private double mainHeight = 445;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double lineWidth;
    private byte pointsIterator = 0;
    private Shape tempShape;
    private double[] oldPoints = {-1, -1};
    private ShapeType shapeType = ShapeType.NULL;
    @FXML
    private StackPane canvas;
    @FXML
    private Canvas baseCanvas;
    @FXML
    private ImageView fondo;

    String[] dataLocale = Main.getInstance().getLocale();
    Locale language = new Locale(dataLocale[1]);
    String path = dataLocale[0];
    ResourceBundle messages = ResourceBundle.getBundle(path, language);

    public static Sketch getInstance() {
        return instance;
    }

    public static void setInstance(Sketch instance) {
        Sketch.instance = instance;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public double getMainWidth() {
        return mainWidth;
    }

    public void setMainWidth(double mainWidth) {
        this.mainWidth = mainWidth;
        canvas.setMaxWidth(mainWidth);
        GraphicsContext gc = baseCanvas.getGraphicsContext2D();
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0, 0, baseCanvas.getWidth(), baseCanvas.getHeight());
        setFillColor(Color.WHITE);
        baseCanvas.setWidth(mainWidth);
    }

    public double getMainHeight() {
        return mainHeight;
    }

    public void setMainHeight(double mainHeight) {
        this.mainHeight = mainHeight;
        canvas.setMaxHeight(mainHeight);
        GraphicsContext gc = baseCanvas.getGraphicsContext2D();
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0, 0, baseCanvas.getWidth(), baseCanvas.getHeight());
        setFillColor(Color.WHITE);
        baseCanvas.setHeight(mainHeight);
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @FXML
    private void initialize() {
        double[] XPoints = new double[3];
        double[] YPoints = new double[3];
        setInstance(this);

        setMainWidth(mainWidth);
        setMainHeight(mainHeight);

        canvas.setOnMousePressed(e -> {
            if (moveFlag || resizeFlag) {
                Shape s = ShapeSelector.getSelectedShape(e.getX(), e.getY());
                if (s != null) {
                    tempShape = s;
                    oldPoints[0] = e.getX();
                    oldPoints[1] = e.getY();
                }
                return;
            }
            if (deleteFlag) {
                Frame.getInstance().getDelete().setDisable(false);
                Shape s = ShapeSelector.getSelectedShape(e.getX(), e.getY());
                if (s != null) {
                    Main.getInstance().getShapes().remove(s);
                    s.remove();
                }
                return;
            }
            startX = e.getX();
            startY = e.getY();
            SmallRectangles.create(e.getX(), e.getY());
            if (shapeType == ShapeType.TRIANGLE) {
                XPoints[pointsIterator] = e.getX();
                YPoints[pointsIterator] = e.getY();
                pointsIterator++;
            }
        });

        canvas.setOnMouseReleased(e -> {

            if (deleteFlag) {
                deleteFlag = false;
                return;
            }
            endX = e.getX();
            endY = e.getY();
            switch (shapeType) {
                case LINE:
                    SmallRectangles.remove();
                    new Line(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case CIRCLE:
                    SmallRectangles.remove();
                    new Circle(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER:
                    SmallRectangles.remove();
                    new ImageAtacante(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER1:
                    SmallRectangles.remove();
                    new ImageAtacante1(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER2:
                    SmallRectangles.remove();
                    new ImageAtacante2(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER3:
                    SmallRectangles.remove();
                    new ImageAtacante3(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER4:
                    SmallRectangles.remove();
                    new ImageAtacante4(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER5:
                    SmallRectangles.remove();
                    new ImageAtacante5(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER6:
                    SmallRectangles.remove();
                    new ImageAtacante6(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER7:
                    SmallRectangles.remove();
                    new ImageAtacante7(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER8:
                    SmallRectangles.remove();
                    new ImageAtacante8(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case OFFENSEPLAYER9:
                    SmallRectangles.remove();
                    new ImageAtacante9(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER:
                    SmallRectangles.remove();
                    new ImageDefensor(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER1:
                    SmallRectangles.remove();
                    new ImageDefensor1(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER2:
                    SmallRectangles.remove();
                    new ImageDefensor2(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER3:
                    SmallRectangles.remove();
                    new ImageDefensor3(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER4:
                    SmallRectangles.remove();
                    new ImageDefensor4(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER5:
                    SmallRectangles.remove();
                    new ImageDefensor5(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER6:
                    SmallRectangles.remove();
                    new ImageDefensor6(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER7:
                    SmallRectangles.remove();
                    new ImageDefensor7(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER8:
                    SmallRectangles.remove();
                    new ImageDefensor8(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DEFENSEPLAYER9:
                    SmallRectangles.remove();
                    new ImageDefensor9(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case SHOOTLINE:
                    SmallRectangles.remove();
                    new ShootLine(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case BLOQLINE:
                    SmallRectangles.remove();
                    new BloqLine(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case PASSLINE:
                    SmallRectangles.remove();
                    new ShadedLine(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case DRILLLINE:
                    SmallRectangles.remove();
                    new DrillLine(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case BALL:
                    SmallRectangles.remove();
                    new ImageBall(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case CONE:
                    SmallRectangles.remove();
                    new ImageCone(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case BASQUET:
                    SmallRectangles.remove();
                    new ImageBasket(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case COACH:
                    SmallRectangles.remove();
                    new ImageCoach(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case STAIR:
                    SmallRectangles.remove();
                    new ImageStair(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case CESTA:
                    SmallRectangles.remove();
                    new ImageCesta(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;

                case RECTANGLE:
                    SmallRectangles.remove();
                    new Rectangle(new double[]{startX, startY}, new double[]{endX, endY}, getStrokeColor(), getFillColor(), getLineWidth() == 0 ? 1 : getLineWidth()).draw();
                    break;
                case NULL:
                    SmallRectangles.remove();
                    break;
            }
        });
    }

    //Define el fondo del sketch, según lo marcado cuando la creas
    public void definirFondo() {
        Image img;


        if (Frame.getInstance().getpassFondo().contains(messages.getString("Attack"))) {
            img = new Image("/Images/pistaAtaque.png");
        } else if (Frame.getInstance().getpassFondo().contains(messages.getString("Defense"))) {
            img = new Image("/Images/pistaDefensiva.png");
        } else {
            img = new Image("/Images/pistaEntera.png");
        }
        fondo.setImage(img);
    }

    public StackPane getCanvas() {
        return canvas;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
        Frame.getInstance().Change = true;
    }

    public void setMoveFlag(boolean moveFlag) {
        this.moveFlag = moveFlag;
    }

    public void setResizeFlag(boolean resizeFlag) {
        this.resizeFlag = resizeFlag;

    }

    public enum ShapeType {
        LINE, CIRCLE, TRIANGLE, RECTANGLE, OFFENSEPLAYER, OFFENSEPLAYER1, OFFENSEPLAYER2,
        OFFENSEPLAYER3, OFFENSEPLAYER4, OFFENSEPLAYER5, OFFENSEPLAYER6, OFFENSEPLAYER7, OFFENSEPLAYER8, OFFENSEPLAYER9, DEFENSEPLAYER,
        DEFENSEPLAYER1, DEFENSEPLAYER2, DEFENSEPLAYER3, DEFENSEPLAYER4, DEFENSEPLAYER5, DEFENSEPLAYER6, DEFENSEPLAYER7, DEFENSEPLAYER8, DEFENSEPLAYER9,
        SHOOTLINE, PASSLINE, BLOQLINE, DRILLLINE, BALL, CONE, BASQUET, CESTA, COACH, STAIR, NULL
    }

}

class SmallRectangles {

    private static ArrayList<Canvas> smallRectangles = new ArrayList<>();

    public static void create(double x, double y) {
        Canvas c = new Canvas(Sketch.getInstance().getMainWidth(), Sketch.getInstance().getMainHeight());
        c.getGraphicsContext2D().strokeRect(x, y, 2, 2);
        smallRectangles.add(c);
        Sketch.getInstance().getCanvas().getChildren().add(c);
    }

    public static void remove() {
        Sketch.getInstance().getCanvas().getChildren().removeAll(smallRectangles);
    }
}

class ShapeSelector {

    public static Shape getSelectedShape(double x, double y) {
        for (Shape s : Main.getInstance().getShapes()) {
            if (s.getProperties().containsKey("point1-x")) {
                if (isInRange(x, (double) s.getProperties().get("point1-x"), y, (double) s.getProperties().get("point1-y"))) {
                    return s;
                }
                if (isInRange(x, (double) s.getProperties().get("point2-x"), y, (double) s.getProperties().get("point2-y"))) {
                    return s;
                }
                if (isInRange(x, (double) s.getProperties().get("point3-x"), y, (double) s.getProperties().get("point3-y"))) {
                    return s;
                }
            } else {
                if (isInRange(x, (double) s.getProperties().get("starting-x"), y, (double) s.getProperties().get("starting-y"))) {
                    return s;
                }
                if (isInRange(x, (double) s.getProperties().get("ending-x"), y, (double) s.getProperties().get("ending-y"))) {
                    return s;
                }
            }
        }
        return null;
    }

    private static boolean isInRange(double x1, double x2, double y1, double y2) {
        return Math.abs(x1 - x2) < 5 && Math.abs(y1 - y2) < 5;
    }
}
