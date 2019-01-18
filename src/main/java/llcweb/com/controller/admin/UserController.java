package llcweb.com.controller.admin;

import llcweb.com.dao.repository.PeopleRepository;
import llcweb.com.dao.repository.RolesRepository;
import llcweb.com.dao.repository.UsersRepository;
import llcweb.com.domain.models.Roles;
import llcweb.com.domain.models.Users;
import llcweb.com.service.UsersService;
import llcweb.com.tools.PageParam;
import llcweb.com.tools.StringUtil;
import llcweb.com.tools.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Author: Ricardo
 *@Description: 此文件用于user类的数据接口
 *@Date: 15:14 2018/8/21
 **/
@Controller
@RequestMapping("/users")
@Transactional
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;
    @Resource
    private PeopleRepository peopleRepository;
    @Resource
    private RolesRepository rolesRepository;

    /**
     * @Author haien
     * @Description 查找用户
     * @Date 2019/1/14
     * @Param [request, response]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> page(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();

        //直接返回前台
        String draw = request.getParameter("draw");
        //当前数据的起始位置 ，如第10条
        String startIndex = request.getParameter("startIndex");
        //数据长度
        String pageSize = request.getParameter("pageSize");
        int size = Integer.parseInt(pageSize);
        int currentPage = Integer.parseInt(startIndex)/size+1;

        Users users = new Users();
        String fuzzy = request.getParameter("fuzzySearch");
        if("true".equals(fuzzy)){//模糊查找
            String searchValue = request.getParameter("fuzzy");
            if (searchValue!=null&&!searchValue.equals("")) {
                users.setUsername(searchValue);
            }
        }
        //高级查找
        else{
            String username = request.getParameter("username");
            if (username!=null&&!username.equals("")) {
                users.setUsername(username);
            }


        }
        Page<Users> usersPage = usersService.getPage(new PageParam(currentPage,size),users);
        List<Users> usersList = usersPage.getContent();
        //总记录数
        long total = usersPage.getTotalElements();
        map.put("pageData", usersList);
        map.put("total", total);
        map.put("draw", draw);
        map.put("result", 1);
        map.put("message", "hello");
        return map;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        //用户id
        String id = request.getParameter("id");

        //用户名、密码、关联人员id、角色权限
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String peopleId1 = request.getParameter("peopleId");
        String role = request.getParameter("role");
        int peopleId = StringUtil.isNull(peopleId1) ? 0 : Integer.parseInt(peopleId1); //id为null则非内部人员，赋默认值0

        //参数验证
        if (!ValidatorUtil.isUsername(username)) {
            map.put("result", 0);
            map.put("message", "用户名只能是不能超过15位的数字、字母或下划线！");
            return map;
        }
        if (!ValidatorUtil.isPasswd(password)) {
            map.put("result", 0);
            map.put("message", "密码只能是6-12位的数字或字母！");
            return map;
        }
        if (StringUtil.isNull(role)) {
            map.put("result", 0);
            map.put("message", "请为用户分配权限！");
            return map;
        }
        if (peopleId != 0) {
            if (peopleRepository.findOne(peopleId) == null) {
                map.put("result", 0);
                map.put("message", "关联人员不存在！");
                return map;
            }
        }

        //更新
        int userId;
        Users users = usersRepository.findByUsername(username);
        Roles roles = rolesRepository.findByRName(role);
        int roleId = roles.getrId();
        if (!StringUtil.isNull(id) && (userId = Integer.parseInt(id)) > 0) {
            if (users != null && users.getId() != userId) {
                map.put("result", 0);
                map.put("message", "用户名已存在！");
                return map;
            }
            logger.info("修改人员信息--id=" + id);
            int result = usersRepository.updateUsers(username, password, new Date(), peopleId, userId);
            if (result == 0) {
                map.put("result", 0);
                map.put("message", "无该用户记录！");
                return map;
            }
            usersRepository.updateUsersRoles(roleId, userId);
        }
        //添加
        else {
            if (users != null) {
                map.put("result", 0);
                map.put("message", "用户名已存在！");
                return map;
            }
            logger.info("新增记录--username=" + username + "peopleId=" + peopleId + "role=" + role);
            usersRepository.saveUsers(username, password, new Date(), peopleId);
            //获取新用户的id，username是唯一标识
            userId = usersRepository.findByUsername(username).getId();
            usersRepository.addUsersRoles(userId, roleId);
        }

        map.put("result", 1);
        map.put("message", "用户保存成功！");
        logger.info("用户保存成功！");
        return map;
    }

    @RequestMapping(value = "/isCurrentUserHasRole",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> delete(@RequestParam("role")String role){
        Map<String,Object> map=new HashMap<>();

        logger.info("isCurrentUserHasRole：role="+role);
        boolean hasRole = usersService.hasRole(role);

        if (!hasRole) {
            map.put("result", 0);
            map.put("data", 0);
            logger.info("没有该权限");
            map.put("message", "没有该权限！");
        }else{
            logger.info("有该权限");
            map.put("result", 1);
            map.put("data", 1);
            map.put("message", "有该权限！");
        }
        return map;
    }

}
