package llcweb.com.dao.repository;


import llcweb.com.domain.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by:Ricardo
 * Description: 用户类的repository类
 * Date: 2018/8/21
 * Time: 13:36
 */
public interface UsersRepository extends JpaRepository<Users,Integer>{ //Integer 是id 的类型

    /**
     *分页+动态查询
     **/
    Page<Users> findAll(Specification<Users> spec, Pageable pageable);

    /**
     *按用户名、密码查询
     */
    Users findByUsernameAndPassword(String userName, String password);
    Users findByUsername(String userName);

    /**
     * 模糊查询
     **/
    @Query("from Users j where j.username like %?1%")
    Page<Users> findByOneKey(String key,Pageable pageable);

    /**
     * @Author haien
     * @Description 添加用户
     * @Date 2019/1/17
     * @Param [username, password, update_time, peopleId]
     * @return int
     **/
    @Modifying
    @Transactional
    @Query(value = "insert into users(username,password,update_time,people_id) value(?1,SUBSTRING(md5(?2),1,16),?3,?4)",nativeQuery = true)
    int saveUsers(String username, String password, Date update_time, int peopleId);

    /**
     * @Author haien
     * @Description 根据id更新用户
     * @Date 2019/1/17
     * @Param [username, password, update_time, peopleId, id]
     * @return int
     **/
    @Modifying
    @Transactional
    @Query(value = "update users set username=?1,password=SUBSTRING(md5(?2),1,16),update_time=?3,people_id=?4 where id=?5",nativeQuery = true)
    int updateUsers(String username, String password, Date update_time, int peopleId,int id);

    /**
     * @Author haien
     * @Description 保存新用户的角色到用户_角色表
     * @Date 2019/1/17
     * @Param [userId, roleId]
     * @return int
     **/
    @Modifying
    @Transactional
    @Query(value = "insert into users_roles(ur_user_id,ur_role_id) value(?1,?2)",nativeQuery = true)
    int addUsersRoles(int userId,int roleId);

    /**
     * @Author haien
     * @Description 更新用户的角色到用户角色表
     * @Date 2019/1/17
     * @Param [roleId, userId]
     * @return int
     **/
    @Modifying
    @Transactional
    @Query(value = "update users_roles set ur_role_id=?1 where ur_user_id=?2",nativeQuery = true)
    int updateUsersRoles(int roleId,int userId);

    @Modifying
    @Transactional
    @Query(value = "update users set password=SUBSTRING(md5(?1),1,16) where id=?2",nativeQuery = true)
    int updatePassword(String password,int id);
}
