package local.tin.tests.log.aggregates;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import local.tin.tests.log.aggregates.tasks.Appender;
import local.tin.tests.log.aggregates.tasks.Finalizer;
import local.tin.tests.log.aggregates.tasks.Initializer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
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
    private Future<LogEntry> mockcedFutureInitializer;
    
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
        mockcedFutureInitializer = mock(Future.class);
        reset(mockedLogEntriesPool);
    }

    @Test
    public void initialize_puts_new_logentry_when_logstep_uuid_not_in_use() throws LogException, InterruptedException, ExecutionException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(null);
        when(mockedExecutorService.submit(any(Callable.class))).thenReturn(mockcedFutureInitializer);
        when(mockcedFutureInitializer.get()).thenReturn(mockedLogEntry);
        
        logEntriesExecutor.initialize(mockedLogStep);

        verify(mockedLogEntriesPool).put(SAMPLE_UUID, mockedLogEntry);
    }

    @Test(expected = LogException.class)
    public void initialize_throws_exception_if_provided_uuid_already_present() throws LogException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(mockedLogEntry);

        logEntriesExecutor.initialize(mockedLogStep);

    }
    
    @Test
    public void initialize_new_logentry_with_logstep() throws LogException, InterruptedException, ExecutionException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(null);
        when(mockedExecutorService.submit(any(Initializer.class))).thenReturn(mockcedFutureInitializer);
        when(mockcedFutureInitializer.get()).thenReturn(mockedLogEntry);
        
        logEntriesExecutor.initialize(mockedLogStep);

        verify(mockedLogEntriesPool).put(SAMPLE_UUID, mockedLogEntry);
    }
    
   @Test(expected = LogException.class)
    public void append_throws_exception_if_log_step_does_not_include_uuid() throws LogException, InterruptedException, ExecutionException {
        when(mockedLogStep.getId()).thenReturn(null);
        
        logEntriesExecutor.append(mockedLogStep);

    }

   @Test(expected = LogException.class)
    public void append_throws_exception_if_log_step_no_found_on_pool() throws LogException, InterruptedException, ExecutionException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(null);
        
        logEntriesExecutor.append(mockedLogStep);

    }

    
    @Test
    public void append_submits_task() throws LogException, InterruptedException, ExecutionException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(mockedLogEntry);
        when(mockedExecutorService.submit(any(Appender.class))).thenReturn(mockcedFutureInitializer);
        when(mockcedFutureInitializer.get()).thenReturn(mockedLogEntry);
        
        logEntriesExecutor.append(mockedLogStep);

        verify(mockedExecutorService).submit(any(Appender.class));
    }   
    
   @Test(expected = LogException.class)
    public void finalize_throws_exception_if_log_step_does_not_include_uuid() throws LogException, InterruptedException, ExecutionException {
        when(mockedLogStep.getId()).thenReturn(null);
        
        logEntriesExecutor.finalize(mockedLogStep);

    }

   @Test(expected = LogException.class)
    public void finalize_throws_exception_if_log_step_no_found_on_pool() throws LogException, InterruptedException, ExecutionException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(null);
        
        logEntriesExecutor.finalize(mockedLogStep);

    }

    
    @Test
    public void finalize_submits_task() throws LogException, InterruptedException, ExecutionException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);
        when(mockedLogEntriesPool.get(SAMPLE_UUID)).thenReturn(mockedLogEntry);
        when(mockedExecutorService.submit(any(Finalizer.class))).thenReturn(mockcedFutureInitializer);
        when(mockcedFutureInitializer.get()).thenReturn(mockedLogEntry);
        
        logEntriesExecutor.finalize(mockedLogStep);

        verify(mockedExecutorService).submit(any(Finalizer.class));
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
