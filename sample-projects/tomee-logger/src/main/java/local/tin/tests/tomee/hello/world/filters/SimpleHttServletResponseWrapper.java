package local.tin.tests.tomee.hello.world.filters;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author benitodarder
 */
public class SimpleHttServletResponseWrapper extends HttpServletResponseWrapper {

	private final SimpleServletOutputStream loggingServletOutpuStream = new SimpleServletOutputStream();

	private final HttpServletResponse delegate;

	public SimpleHttServletResponseWrapper(HttpServletResponse response) {
		super(response);
		delegate = response;
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return loggingServletOutpuStream;
	}

	@Override
	public PrintWriter getWriter() {
		return new PrintWriter(loggingServletOutpuStream.getBaos());
	}

	public Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<>(0);
		for (String headerName : getHeaderNames()) {
			headers.put(headerName, getHeader(headerName));
		}
		return headers;
	}

	public String getContent() {
		try {
			String responseEncoding = delegate.getCharacterEncoding();
			return loggingServletOutpuStream.getBaos().toString(responseEncoding != null ? responseEncoding : UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			return "[UNSUPPORTED ENCODING]";
		}
	}

	public byte[] getContentAsBytes() {
		return loggingServletOutpuStream.getBaos().toByteArray();
	}
}