package local.tin.tests.log.aggregates;

import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author benitodarder
 */
public class LogEntriesPoolTest {

    private static final String SAMPLE_UUID = UUID.randomUUID().toString();
    private LogEntriesPool logEntriesPool;
    private LogEntry mockedLogEntry;

    @Before
    public void setUp() {
        logEntriesPool = new LogEntriesPoolWrapper();
        mockedLogEntry = mock(LogEntry.class);
    }

    @Test
    public void get_retrieves_element_previously_put() {
        logEntriesPool.put(SAMPLE_UUID, mockedLogEntry);

        assertEquals(mockedLogEntry, logEntriesPool.get(SAMPLE_UUID));
    }

    @Test
    public void remove_removes_element_previously_put() {
        logEntriesPool.put(SAMPLE_UUID, mockedLogEntry);

        logEntriesPool.remove(SAMPLE_UUID);

        assertNull(logEntriesPool.get(SAMPLE_UUID));
    }

    @Test
    public void resetPool_empties_pool() {
        logEntriesPool.put(SAMPLE_UUID, mockedLogEntry);

        logEntriesPool.resetPool();
        
        assertNull(logEntriesPool.get(SAMPLE_UUID));
    }
}



class LogEntriesPoolWrapper extends LogEntriesPool<LogEntryWrapperToo> {



}
