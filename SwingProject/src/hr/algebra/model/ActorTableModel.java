package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Kevin Furjan
 */
public class ActorTableModel extends AbstractTableModel {

    List<Actor> actors;
    private static final String DELIMITER = "; ";

    private static final String[] COLUMN_NAMES = {"ID", "Firstname", "Lastname", "Movies"};

    public ActorTableModel(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public int getRowCount() {
        return actors.size();
    }

    @Override
    public int getColumnCount() {
        return Actor.class.getDeclaredFields().length
                + Person.class.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return actors.get(rowIndex).getId();
            case 1:
                return actors.get(rowIndex).getFirstName();
            case 2:
                return actors.get(rowIndex).getLastName();
            case 3:
                return actors.get(rowIndex).getMovies()
                        .stream()
                        .map(Movie::getTitle)
                        .reduce((partial, title) -> partial + DELIMITER + title)
                        .get();
            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
