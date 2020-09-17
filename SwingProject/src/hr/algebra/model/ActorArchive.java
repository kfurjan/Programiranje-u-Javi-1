package hr.algebra.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Kevin Furjan
 */
@XmlRootElement(name = "actorarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActorArchive {

    @XmlElementWrapper
    @XmlElement(name = "actor")
    private List<Actor> actors;
    
    public ActorArchive() { }

    public ActorArchive(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Actor> getActors() {
        return actors;
    }

}
