package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The javafx application.
 *
 */
public final class MetalShot extends Application {
    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     */
    @Override
    public void start(final Stage primaryStage) throws IOException {
    	try {
		    Parent root = FXMLLoader.load(getClass().getResource("/ManagersPane.fxml"));
		    primaryStage.setScene(new Scene(root));
		    primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
}
