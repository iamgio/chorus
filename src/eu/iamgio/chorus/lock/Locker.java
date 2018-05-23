package eu.iamgio.chorus.lock;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import it.sauronsoftware.junique.MessageHandler;
import javafx.application.Platform;

/**
 * @author Gio
 */
public class Locker {

    public static final String ID = "eu.iamgio.chorus",
                               RUNNING = "RUNNING",
                               ARG_PREFIX = "ARG:\0";

    private MessageHandler onSecondInstance;

    public void setOnSecondInstance(MessageHandler onSecondInstance) {
        this.onSecondInstance = onSecondInstance;
    }

    public boolean lock(String... args) {
        boolean alreadyRunning;
        try {
            JUnique.acquireLock(ID, message -> {
                Platform.runLater(() -> onSecondInstance.handle(message));
                return null;
            });
            alreadyRunning = false;
        } catch(AlreadyLockedException e) {
            alreadyRunning = true;
        }
        if(alreadyRunning) {
            JUnique.sendMessage(ID, RUNNING);
            for(String arg : args) {
                JUnique.sendMessage(ID, ARG_PREFIX + arg);
            }
        }
        return alreadyRunning;
    }
}
