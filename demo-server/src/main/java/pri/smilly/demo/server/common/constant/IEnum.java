package pri.smilly.demo.server.common.constant;

public interface IEnum<E extends IEnum> {

    int getCode();

    E parser(int code);

}
