package llcweb.com.dao.repository;

import llcweb.com.domain.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author haien
 * @Description 角色类的repository
 * @Date 2019/1/17
 **/
public interface RolesRepository extends JpaRepository<Roles,Integer> {

    /**
     * @Author haien
     * @Description 根据角色名查找角色
     * @Date 2019/1/17
     * @Param [rName]
     * @return llcweb.com.domain.models.Roles
     **/
    public Roles findByRName(String rName);
}
