package llcweb.com.dao.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersRepositoryTest {

    @Resource
    private UsersRepository usersRepository;

    @Test
    public void saveUsersTest(){
        int result=usersRepository.saveUsers("test", "111111", new Date(), 1);
        Assert.assertThat(result,is(1));
    }

    @Test
    public void updateUsersTest(){
        int result=usersRepository.updateUsers("test", "222222", new Date(), 1,45);
        Assert.assertThat(result,is(1));
    }
}