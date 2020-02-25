import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserController {
    private LoginPage theModel;
    private Main theView;

    public UserController(LoginPage theModel, Main theView) {
        this.theModel = theModel;
        this.theView = theView;

        this.theModel.addLogInListener(new LogInListener());
    }

    class LogInListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            User currentUser = theModel.getLoggedInUser();
            theView.setCurrentUser(currentUser);
            theView.displayCurrentUser(currentUser);
        }
    }
}
