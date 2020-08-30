package hr.algebra.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author Kevin Furjan
 */
public class ActorTransferable implements Transferable {

    public static final DataFlavor ACTOR_FLAVOR = new DataFlavor(Actor.class, "Actor");
    public static final DataFlavor[] SUPPORTED_FLAVORS = {ACTOR_FLAVOR};
    private final Actor actor;

    public ActorTransferable(Actor actor) {
        this.actor = actor;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(ACTOR_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(ACTOR_FLAVOR)) {
            return actor;
        }
        throw new UnsupportedFlavorException(flavor);
    }
}
