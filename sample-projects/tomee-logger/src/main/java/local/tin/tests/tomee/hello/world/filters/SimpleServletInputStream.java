package local.tin.tests.tomee.hello.world.filters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author benitodarder
 */
public class SimpleServletInputStream extends ServletInputStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleServletInputStream.class);
    private final InputStream is;

    public SimpleServletInputStream(byte[] content) {
        this.is = new ByteArrayInputStream(content);
    }

    @Override
    public boolean isFinished() {
        try {
            return is.available() == 0;
        } catch (IOException ex) {
            LOGGER.warn("Unexpected IOException on isFinished, returning true.", ex);
            return true;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        // not used
    }

    @Override
    public int read() throws IOException {
        return this.is.read();
    }

    @Override
    public void close() throws IOException {
        super.close();
        is.close();
    }
}
