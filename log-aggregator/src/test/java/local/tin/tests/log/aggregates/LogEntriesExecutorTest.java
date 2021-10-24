package local.tin.tests.log.aggregates;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import local.tin.tests.log.aggregates.tasks.Initializer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.reflect.Whitebox;

/**
 *
 * @author benitodarder
 */
public class LogEntriesExecutorTest {

    private static final UUID SAMPLE_UUID = UUID.randomUUID();
    protected static ILogEntriesPool mockedLogEntriesPool;
    protected static LogEntry mockedLogEntry;
    private LogEntriesExecutor logEntriesExecutor;
    private LogStep mockedLogStep;
    private ExecutorService mockedExecutorService;

    @BeforeClass
    public static void setUpClass() {
        mockedLogEntriesPool = mock(ILogEntriesPool.class);
        mockedLogEntry = mock(LogEntry.class);
    }

    @Before
    public void setUp() {
        logEntriesExecutor = new LogEntriesExecutorWrapper();
        mockedExecutorService = mock(ExecutorService.class);
        Whitebox.setInternalState(logEntriesExecutor, "executorService", mockedExecutorService);
        mockedLogStep = mock(LogStep.class);

    }

    @Test
    public void initialize_puts_new_logentry_when_logstep_uuid_not_in_use() throws LogException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(null);

        logEntriesExecutor.initialize(mockedLogStep);

        verify(mockedLogEntriesPool).put(eq(SAMPLE_UUID), any(LogEntry.class));
    }

    @Test(expected = LogException.class)
    public void initialize_throws_exception_if_provided_uuid_already_present() throws LogException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(mockedLogEntry);

        logEntriesExecutor.initialize(mockedLogStep);

    }
    
    @Test
    public void initialize_new_logentry_with_logstep() throws LogException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(null);

        logEntriesExecutor.initialize(mockedLogStep);

        verify(mockedExecutorService).submit(any(Initializer.class));
    }
}

class LogEntryWrapperToo extends LogEntry {

    @Override
    protected void initializeSpecifics(LogStep logstep) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void append(LogStep logstep) throws LogException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void finalize(LogStep logstep) throws LogException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class LogEntriesExecutorWrapper extends LogEntriesExecutor {

    @Override
    protected ILogEntriesPool getLogEntriesPool() {
        return LogEntriesExecutorTest.mockedLogEntriesPool;
    }

    @Override
    protected LogEntry getNewLogEntryInstance() {
        return LogEntriesExecutorTest.mockedLogEntry;
    }

}
