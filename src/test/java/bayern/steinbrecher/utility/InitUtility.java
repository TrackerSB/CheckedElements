package bayern.steinbrecher.utility;

import javafx.embed.swing.JFXPanel;

import javax.swing.SwingUtilities;
import java.util.concurrent.CountDownLatch;

public final class InitUtility {
    private static boolean isJavaFXUninitialized = true;

    private InitUtility() {
        throw new UnsupportedOperationException("Construction of objects is prohibited");
    }

    public static void initJavaFX() throws InterruptedException {
        if(isJavaFXUninitialized){
            final CountDownLatch latch = new CountDownLatch(1);
            SwingUtilities.invokeLater(() -> {
                // initializes JavaFX environment
                new JFXPanel();

                latch.countDown();
            });
            latch.await();

            isJavaFXUninitialized = false;
        }
    }
}
