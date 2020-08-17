package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Kevin Furjan
 */
public class MovieTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES
            = {"Title", "Published date", "Description", "Original name",
                "Length", "Picture path", "Link", "Start date"};

    List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return Movie.class.getDeclaredFields().length - 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getTitle();
            case 1:
                return movies.get(rowIndex).getPublishedDate().format(Movie.DATE_FORMATTER);
            case 2:
                return movies.get(rowIndex).getDescription();
            case 3:
                return movies.get(rowIndex).getOriginalName();
            case 4:
                return movies.get(rowIndex).getLength();
            case 5:
                return movies.get(rowIndex).getPicturePath();
            case 6:
                return movies.get(rowIndex).getLink();
            case 7:
                return movies.get(rowIndex).getStartDate();
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
