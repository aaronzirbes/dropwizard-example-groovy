import org.junit.Test;

/**
 * This class exists because there is a bug in the groovy maven plugin that prevents it from
 * seeing groovy test source if there is no java source.
 */
public class DummyTest {

    @Test
    public void assertTrue() {
        assert true;
    }
}
