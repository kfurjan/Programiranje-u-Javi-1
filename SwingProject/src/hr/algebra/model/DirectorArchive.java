package hr.algebra.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Kevin Furjan
 */
@XmlRootElement(name = "directorarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class DirectorArchive {

    @XmlElementWrapper
    @XmlElement(name = "director")
    private List<Director> directors;
    
    public DirectorArchive() { }

    public DirectorArchive(List<Director> directors) {
        this.directors = directors;
    }

    public List<Director> getDirectors() {
        return directors;
    }
}
