import org.junit.jupiter.api.*;
import repository.UserRepository;
import utils.PasswordHelper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserRepository{
    final String filename = "test_users.csv";
    final String filepath = java.nio.file.Paths.get(System.getProperty("user.dir"),"test","resources", filename).toString();
    private UserRepository users = new UserRepository(filepath);

    @Test
    @Order(1)
    public void addUsers(){
        int num = users.db.size();
        for (int i = 0;i < 10; i++)
            users.createNewUser("Test_User_Repository_"+i,"testPassword"+i,"admin");
        Assertions.assertEquals(users.db.size(),num+10);
    }
    @Test
    @Order(2)
    public void validateUserPassword(){
        for (int i = 0; i < 10; i++)
            Assertions.assertEquals(users.getUserObject("Test_User_Repository_"+i).passwordHash, PasswordHelper.hash("testPassword" + i));
    }

    @Test
    @Order(3)
    public void deleteUsers(){
        int num = users.db.size();
        for (int i = 0;i < 10; i++)
            users.deleteUser("Test_User_Repository_"+i);
        Assertions.assertEquals(users.db.size(),num-10);
    }

    @Test
    public void changePassword(){
        String username = "Test_User_Repo_change_pwd";
        String password="password";
        users.createNewUser(username,password,"admin");
        Assertions.assertEquals(users.getUserObject(username).passwordHash, PasswordHelper.hash(password));
        users.updateUserPassword(username,"new_password");
        Assertions.assertNotEquals(users.getUserObject(username).passwordHash, PasswordHelper.hash(password));
        Assertions.assertEquals(users.getUserObject(username).passwordHash, PasswordHelper.hash("new_password"));
        users.deleteUser(username);
    }
}
