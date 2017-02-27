package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Plan.
 *
 * @author David García
 */
public class Plan {

    private final StringProperty Start;
    private final StringProperty End;
    private final StringProperty Drill;
    private final StringProperty Notes;
    /**
     * Default constructor.
     */
    public Plan() {
        this(null, null,null,null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param Name
     * @param Type
     */
    public Plan(String Name, String Type,String Drill, String Notes) {
        this.Start = new SimpleStringProperty(Name);
        this.End = new SimpleStringProperty(Type);

        // Some initial dummy data, just for convenient testing.
        this.Drill = new SimpleStringProperty(Drill);
        this.Notes = new SimpleStringProperty(Notes);
       

    }

   

    public String getStart() {
        return Start.get();
    }

    public void setStart(String Start) {
        this.Start.set(Start);
    }

    public StringProperty StartProperty() {
        return Start;
    }

    public String getEnd() {
        return End.get();
    }

    public void setEnd(String End) {
        this.End.set(End);
    }

    public StringProperty EndProperty() {
        return End;
    }

    public String getDrill() {
        return Drill.get();
    }

    public void setDrill(String Drill) {
        this.Drill.set(Drill);
    }

    public StringProperty DrillProperty() {
        return Drill;
    }

    public String getNotes() {
        return Notes.get();
    }

    public void setNotes(String Notes) {
        this.Notes.set(Notes);
    }

    public StringProperty NotesProperty() {
        return Notes;
    }

    

}
