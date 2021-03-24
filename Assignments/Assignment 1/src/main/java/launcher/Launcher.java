package launcher;

import database.Bootstrapper;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.SQLException;
import java.util.Collections;

public class Launcher {

    public static boolean BOOTSTRAP = false;

    public static void main(String[] args) {
        bootstrap();

        ComponentFactory componentFactory = ComponentFactory.instance(false);
        componentFactory.getLoginView().setVisible();
    }

    private static void bootstrap() {
        if (BOOTSTRAP) {
            try {
                new Bootstrapper().execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static void admin()
    {

    }
}
