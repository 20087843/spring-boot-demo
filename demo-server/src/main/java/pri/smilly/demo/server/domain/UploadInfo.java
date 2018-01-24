package pri.smilly.demo.server.domain;

import pri.smilly.demo.domain.BaseDomain;

import java.util.Objects;

public class UploadInfo extends BaseDomain<UploadInfo> {

    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadInfo that = (UploadInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
