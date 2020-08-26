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
@XmlRootElement(name = "actorarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActorArchive {

    @XmlElementWrapper
    @XmlElement(name = "actor")
    private List<Actor> actors;

    public ActorArchive() {
    }

    public ActorArchive(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Actor> getActors() {
        return actors;
    }

}
