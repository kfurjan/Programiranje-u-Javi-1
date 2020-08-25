package hr.algebra.model;

import java.util.List;
import java.util.Optional;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Kevin Furjan
 */
public class DirectorTableModel extends AbstractTableModel {

    List<Director> directors;

    private static final String DELIMITER = "; ";
    private static final String[] COLUMN_NAMES = {"ID", "Firstname", "Lastname", "Movies"};

    public DirectorTableModel(List<Director> directors) {
        this.directors = directors;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    @Override
    public int getRowCount() {
        return directors.size();
    }

    @Override
    public int getColumnCount() {
        return Director.class.getDeclaredFields().length
                + Person.class.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return directors.get(rowIndex).getId();
            case 1:
                return directors.get(rowIndex).getFirstName();
            case 2:
                return directors.get(rowIndex).getLastName();
            case 3:
                Optional<String> actorMovies = directors.get(rowIndex).getMovies()
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
