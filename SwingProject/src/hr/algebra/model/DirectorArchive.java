package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kevin Furjan
 */
@XmlRootElement(name = "directorarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class DirectorArchive {

    @XmlElementWrapper
    @XmlElement(name = "director")
    private List<Director> directors;

    public DirectorArchive() {
    }

    public DirectorArchive(List<Director> directors) {
        this.directors = directors;
    }

    public List<Director> getDirectors() {
        return directors;
    }
}
