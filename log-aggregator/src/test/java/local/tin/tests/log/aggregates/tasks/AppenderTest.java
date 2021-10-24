package local.tin.tests.log.aggregates.tasks;

import local.tin.tests.log.aggregates.LogEntry;
import local.tin.tests.log.aggregates.LogStep;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author benitodarder
 */
public class AppenderTest {

    private LogEntry mockedLogEntry;
    private LogStep mockedLogStep;
    private Appender task;
    
    @Before
    public void setUp() {
        mockedLogEntry = mock(LogEntry.class);
        mockedLogStep = mock(LogStep.class);
        task = new Appender(mockedLogEntry, mockedLogStep);
    }
    
    @Test
    public void call_uses_expected_method() throws Exception {
        
        task.call();
        
        verify(mockedLogEntry).append(mockedLogStep);
    }
}


