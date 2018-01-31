package pri.smilly.demo.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pri.smilly.demo.server.common.domain.User;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User getUser(User condition);

    List<User> getUsers(User condition);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}