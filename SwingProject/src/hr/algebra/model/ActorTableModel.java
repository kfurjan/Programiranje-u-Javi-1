package hr.algebra.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Optional;

/**
 * @author Kevin Furjan
 */
public class ActorTableModel extends AbstractTableModel {

    private static final String DELIMITER = "; ";
    private static final String[] COLUMN_NAMES = {"ID", "Firstname", "Lastname", "Movies"};
    List<Actor> actors;

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
                Optional<String> actorMovies = actors.get(rowIndex).getMovies()
                        .stream()
                        .map(Movie::getTitle)
                        .reduce((partial, title) -> partial + DELIMITER + title);

                return actorMovies.isPresent() ? actorMovies.get() : "";
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
