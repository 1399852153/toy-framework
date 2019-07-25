package test.demo.dao;

import test.demo.model.User;

import java.util.List;

/**
 * Program Name: toy-framework
 * <p>
 * Description:  User dao
 * <p>
 *
 * @author zhangjianwei
 * @version 1.0
 * @date 2019/7/23 8:05 PM
 */
public interface UserDAO {
    User getUser(User param);

    List<User> getUserList(User param);
}
