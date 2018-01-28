package pri.smilly.demo.server.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pri.smilly.demo.domain.BaseDomain;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HelloMessage extends BaseDomain<HelloMessage> {
    private String name;
}
