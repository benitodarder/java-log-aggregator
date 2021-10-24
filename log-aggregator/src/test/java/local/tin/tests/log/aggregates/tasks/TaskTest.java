package local.tin.tests.log.aggregates.tasks;

import local.tin.tests.log.aggregates.LogEntry;
import local.tin.tests.log.aggregates.LogStep;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author benitodarder
 */
public class TaskTest {

    private LogEntry mockedLogEntry;
    private LogStep mockedLogStep;
    private Task task;
    
    @Before
    public void setUp() {
        mockedLogEntry = mock(LogEntry.class);
        mockedLogStep = mock(LogStep.class);
        task = new TaskWrapper(mockedLogEntry, mockedLogStep);
    }
    
    @Test
    public void getLogEntry_returns_expected_value() {
        assertEquals(mockedLogEntry, task.getLogEntry());
    }
    
    @Test
    public void getLogStep_returns_expecte_value() {
        assertEquals(mockedLogStep, task.getLogStep());
    }
}

class TaskWrapper extends Task {

    public TaskWrapper(LogEntry logEntry, LogStep logStep) {
        super(logEntry, logStep);
    }

    @Override
    public LogEntry call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
