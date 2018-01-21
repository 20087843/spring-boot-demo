package pri.smilly.demo.utils;

import org.junit.Assert;
import org.junit.Test;
import pri.smilly.demo.BaseTester;
import pri.smilly.demo.scanner.ClassUtil;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ClassUtilTest extends BaseTester {

    @Test
    public void getResourcesTest() throws Exception {
        List<URL> resources = ClassUtil.getResources("pri/smilly/demo/component");
        log.println(Arrays.toString(resources.toArray()));
        Assert.assertTrue(resources != null && resources.size() > 0);

        File file = new File(resources.get(0).toURI().getPath());
        log.println(file);
        Assert.assertTrue(file.exists());
    }

}
