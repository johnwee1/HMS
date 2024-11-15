import org.junit.jupiter.api.Test;
import utils.InputValidater;
import utils.PasswordHelper;

import java.util.Scanner;

public class TestLogin {

    @Test
    public void hashPassword() {
        String password = "password";
        String hashed = PasswordHelper.hash(password);
        System.out.println(hashed);
    }
}
