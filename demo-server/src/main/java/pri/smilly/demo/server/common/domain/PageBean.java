package pri.smilly.demo.server.common.domain;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pri.smilly.demo.util.ConverterUtil;

import java.util.List;

@Setter
@Getter
@ApiModel
public class PageBean<E> {

    @ApiModelProperty
    private int pageNum;
    @ApiModelProperty
    private int pageSize;
    @ApiModelProperty
    private int startRow;
    @ApiModelProperty
    private int endRow;
    @ApiModelProperty
    private long total;
    @ApiModelProperty
    private int pages;

    private List<?> datas;

    private PageBean() {
    }

    @SneakyThrows
    public PageBean(List<E> datas) {
        if (datas instanceof Page) {
            ConverterUtil.copyProps(this, datas);
        }
        this.datas = datas;
    }

    @SneakyThrows
    public <T> PageBean<T> convert(Class<T> target) {
        PageBean<T> page = new PageBean<T>();
        ConverterUtil.copyProps(page, this);
        page.setDatas(ConverterUtil.convert(page.getDatas(), target));
        return page;
    }
}
