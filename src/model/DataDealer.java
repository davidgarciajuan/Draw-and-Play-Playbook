package model;

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
import controller.Sketch;
import controller.Sketch.ShapeType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Color;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;

public class DataDealer {

    private static Main instance;
    private static BufferedReader reader;

    private static BufferedWriter writer2;
    private static File saveFile;

    public static Main getInstance() {
        return instance;
    }

    public static void load(jugada jugada, int Contador) {

        ArrayList<String> shapes = new ArrayList<>();
        Map<String, String> properties = new HashMap<>();

        StringReader fileReader = new StringReader(jugada.getCoordenadasJugada());
        reader = new BufferedReader(fileReader);

        while (true) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    shapes.add(line);
                } else {
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(DataDealer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Main.getInstance().getShapes().forEach(Draws.Shape::remove);
        Main.getInstance().getShapes().clear();
        int empezar = 1;
        //Busca el bloque que leer
        for (String getSection : shapes) {
            if (getSection.contentEquals("Bloque " + Contador)) {
                //Lee las lineas que dibujar   
                for (String shapeLine : shapes.subList(empezar, shapes.size())) {
                    shapeLine = shapeLine.replace("{", "");
                    shapeLine = shapeLine.replace("}", "");
                    String[] args = shapeLine.split(", ");

                    if (shapeLine.equals(shapes.get(0))) {
                        //Añade las dimensiones del draw
                        Sketch.getInstance().setMainWidth(Double.valueOf(args[0]));
                        Sketch.getInstance().setMainHeight(Double.valueOf(args[1]));
                    } else if (shapeLine.contentEquals("Bloque " + (Contador + 1))) {
                        //Detecta el siguiente bloque y para
                        break;

                    } else {

                        for (String arg : args) {
                            String[] property = arg.split("=");
                            properties.put(property[0], property[1]);
                        }
                        loadShape(properties);
                        properties.clear();
                    }
                }
            }
            empezar++;

        }

        Main.getInstance().getShapes().forEach(Draws.Shape::draw);
    }

    public static String save(jugada jugada, int contador) {

        ArrayList<String> shapes = new ArrayList<>();

        //Lee y añade los objetos añadidos
        //crea el fichero para el guardado final
        try {
            saveFile = new File("saveDataBloque.txt");
            FileWriter fileWriter = new FileWriter(saveFile);
            writer2 = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringReader fileReader = null;
        //Prepara todo para añadirlo donde toca
        try {
             fileReader = new StringReader(jugada.getCoordenadasJugada());
             reader = new BufferedReader(fileReader);
        } catch (Exception e) {
             fileReader = null;
        }
       if (fileReader != null){
        while (true) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    shapes.add(line);
                } else {
                    break;
                }
            } catch (Exception ex) {
              
            }}
        }
      
        int empezar = 0;
        boolean hayBloque = false;
        for (String existeBloque : shapes) {
           
            if (existeBloque.contentEquals("Bloque " + contador)) {
                
                hayBloque = true;
               
            }
        }
      
        if (hayBloque) {
            for (String getSection : shapes) {
               
                if (getSection.contentEquals("Bloque " + contador)) {
                    int primerPunto = empezar + 1;

                    for (String save : shapes.subList(0, primerPunto)) {

                        try { 
                            writer2.write(save);
                            writer2.newLine();
                        } catch (IOException ex) {
                            Logger.getLogger(DataDealer.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                    for (Shape shape : Main.getInstance().getShapes()) {
                        try {
                           
                            writer2.write(shape.getProperties().toString() + '\n');
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if (getSection.contentEquals("Bloque " + (contador + 1))) {
                        int segundoPunto = empezar;
                        for (String save : shapes.subList(segundoPunto, shapes.size())) {
                            try {
                               
                                writer2.write(save);
                                writer2.newLine();
                            } catch (IOException ex) {
                                Logger.getLogger(DataDealer.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }

                    }
                } catch (Exception e) {

                }
                empezar++;

            }
        } else {
            for (String save : shapes) {
                try {
                    writer2.write(save);
                    writer2.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(DataDealer.class.getName()).log(Level.SEVERE, null, ex);
                }
                

            }
            try {
                writer2.write("Bloque " + contador);
                writer2.newLine();
            } catch (IOException ex) {
                Logger.getLogger(DataDealer.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Shape shape : Main.getInstance().getShapes()) {
                    try {
                        writer2.write(shape.getProperties().toString() + '\n');
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            
        }

        try {
            writer2.close();
        } catch (IOException ex) {
            Logger.getLogger(DataDealer.class.getName()).log(Level.SEVERE, null, ex);
        }

        String coordenadas = "";
        try {
            coordenadas = readFile("saveDataBloque.txt");
        } catch (IOException ex) {
            Logger.getLogger(DataDealer.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        saveFile.delete();
        return coordenadas;
    }

    private static void loadShape(Map<String, String> properties) {
        Stack<Shape> shapes = Main.getInstance().getShapes();
        Shape s;
        switch (ShapeType.valueOf(properties.get("type"))) {
            case LINE:
                double[] lineStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] lineEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new Line(lineStartPoint, lineEndPoint, Color.web(properties.get("stroke-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case RECTANGLE:
                double[] rectStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] rectEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new Rectangle(rectStartPoint, rectEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER:
                double[] offensePlayerStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayerEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante(offensePlayerStartPoint, offensePlayerEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER1:
                double[] offensePlayer1StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer1EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante1(offensePlayer1StartPoint, offensePlayer1EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER2:
                double[] offensePlayer2StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer2EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante2(offensePlayer2StartPoint, offensePlayer2EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER3:
                double[] offensePlayer3StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer3EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante3(offensePlayer3StartPoint, offensePlayer3EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER4:
                double[] offensePlayer4StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer4EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante4(offensePlayer4StartPoint, offensePlayer4EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER5:
                double[] offensePlayer5StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer5EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante5(offensePlayer5StartPoint, offensePlayer5EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER6:
                double[] offensePlayer6StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer6EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante6(offensePlayer6StartPoint, offensePlayer6EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER7:
                double[] offensePlayer7StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer7EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante7(offensePlayer7StartPoint, offensePlayer7EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER8:
                double[] offensePlayer8StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer8EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante8(offensePlayer8StartPoint, offensePlayer8EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case OFFENSEPLAYER9:
                double[] offensePlayer9StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] offensePlayer9EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageAtacante9(offensePlayer9StartPoint, offensePlayer9EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER:
                double[] defensePlayerStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayerEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor(defensePlayerStartPoint, defensePlayerEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER1:
                double[] defensePlayer1StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer1EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor1(defensePlayer1StartPoint, defensePlayer1EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER2:
                double[] defensePlayer2StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer2EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor2(defensePlayer2StartPoint, defensePlayer2EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER3:
                double[] defensePlayer3StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer3EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor3(defensePlayer3StartPoint, defensePlayer3EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER4:
                double[] defensePlayer4StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer4EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor4(defensePlayer4StartPoint, defensePlayer4EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER5:
                double[] defensePlayer5StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer5EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor5(defensePlayer5StartPoint, defensePlayer5EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER6:
                double[] defensePlayer6StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer6EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor6(defensePlayer6StartPoint, defensePlayer6EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER7:
                double[] defensePlayer7StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer7EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor7(defensePlayer7StartPoint, defensePlayer7EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER8:
                double[] defensePlayer8StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer8EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor8(defensePlayer8StartPoint, defensePlayer8EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DEFENSEPLAYER9:
                double[] defensePlayer9StartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] defensePlayer9EndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageDefensor9(defensePlayer9StartPoint, defensePlayer9EndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case SHOOTLINE:
                double[] shootlineStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] shootlineEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ShootLine(shootlineStartPoint, shootlineEndPoint, Color.web(properties.get("stroke-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case BLOQLINE:
                double[] bloqlineStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] bloqlineEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new BloqLine(bloqlineStartPoint, bloqlineEndPoint, Color.web(properties.get("stroke-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case PASSLINE:
                double[] passlineStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] passlineEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ShadedLine(passlineStartPoint, passlineEndPoint, Color.web(properties.get("stroke-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case DRILLLINE:
                double[] drilllineStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] drilllineEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new DrillLine(drilllineStartPoint, drilllineEndPoint, Color.web(properties.get("stroke-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case BALL:
                double[] ballStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] ballEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageBall(ballStartPoint, ballEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case CONE:
                double[] coneStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] coneEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageCone(coneStartPoint, coneEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case BASQUET:
                double[] basquetStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] basquetEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageBasket(basquetStartPoint, basquetEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case COACH:
                double[] coachStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] coachEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageCoach(coachStartPoint, coachEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case STAIR:
                double[] stairStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] stairEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageStair(stairStartPoint, stairEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;
            case CESTA:
                double[] cestaStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] cestaEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new ImageCesta(cestaStartPoint, cestaEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;

            case CIRCLE:
                double[] circleStartPoint = {Double.valueOf(properties.get("starting-x")), Double.valueOf(properties.get("starting-y"))};
                double[] circleEndPoint = {Double.valueOf(properties.get("ending-x")), Double.valueOf(properties.get("ending-y"))};
                s = new Circle(circleStartPoint, circleEndPoint, Color.web(properties.get("stroke-color")), Color.web(properties.get("fill-color")), Double.valueOf(properties.get("stroke-width")));
                if (shapes.search(s) == -1) {
                    shapes.push(s);
                }
                break;

        }
    }

    private static String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }
}
