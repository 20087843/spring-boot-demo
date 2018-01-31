package pri.smilly.demo.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pri.smilly.demo.server.common.domain.PageBean;
import pri.smilly.demo.server.common.domain.User;
import pri.smilly.demo.server.mapper.UserMapper;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper mapper;

    public User getUser(User condition) {
        return mapper.getUser(condition);
    }

    public User getUserById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    public PageBean<User> getUsers(Integer pageNum, Integer pageSize,User condition) {
        PageHelper.startPage(pageNum, pageSize);
        List list = mapper.getUsers(condition);
        return new PageBean<>(list);
    }

    public boolean save(User domain) {
        return mapper.insert(domain) == 1;
    }

}
