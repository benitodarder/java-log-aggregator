package local.tin.tests.log.aggregates.spring.boot.filters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 *
 * @author benitodarder
 */
public class SimpleServletOutputStream extends ServletOutputStream {

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        // not used
    }

    @Override
    public void write(int b) {
        baos.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        baos.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        baos.write(b, off, len);
    }

    public ByteArrayOutputStream getBaos() {
        return baos;
    }
    
    
}
