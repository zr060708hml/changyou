package cn.changyou.gateway.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author xgl
 * @create 2020-01-04 13:06
 */
@ConfigurationProperties(prefix = "changyou.filter")
public class FilterProperties {
    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
