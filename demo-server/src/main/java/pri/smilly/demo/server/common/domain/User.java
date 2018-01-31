package pri.smilly.demo.server.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import pri.smilly.demo.domain.BaseDomain;

@Setter
@Getter
@ApiModel
public class User extends BaseDomain<User> {
    private Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private String password;

    @ApiModelProperty
    private String phone;

}