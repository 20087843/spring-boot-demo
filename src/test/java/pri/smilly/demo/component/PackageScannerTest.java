package pri.smilly.demo.component;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import pri.smilly.demo.BaseTester;
import pri.smilly.demo.scanner.PackageScanner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;

public class PackageScannerTest extends BaseTester {

    private PackageScanner packageScanner;

    @Before
    public void setUp() {
        packageScanner = new PackageScanner();
    }

    @Test
    public void scanTest() throws Exception {
        Set<String> classes = packageScanner.scan("pri.smilly.demo.component");

        log.println(Arrays.toString(classes.toArray()));
    }

    @Test
    public void matchTest() {
        AntPathMatcher matcher = new AntPathMatcher();
        Assert.assertTrue(matcher.match("**/*.class", "PackageScaner.class"));
    }

}
