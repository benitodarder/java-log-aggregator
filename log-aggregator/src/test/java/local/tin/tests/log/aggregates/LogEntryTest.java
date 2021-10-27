package local.tin.tests.log.aggregates;

import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author benitodarder
 */
public class LogEntryTest {

    private static final String SAMPLE_UUID = UUID.randomUUID().toString();
    private LogEntry logEntry;
    private LogStep mockedLogStep;

    @Before
    public void setUp() {
        logEntry = new LogEntryWrapper();
        mockedLogStep = mock(LogStep.class);
    }

    @Test
    public void initialize_assigns_log_entry_uuid_when_set() throws LogException {
        when(mockedLogStep.getId()).thenReturn(SAMPLE_UUID);

        logEntry.initialize(mockedLogStep);

        assertEquals(SAMPLE_UUID, logEntry.getId());
    }

    @Test
    public void initialize_assigns_new_uuid_when_not_set_in_log_step() throws LogException {
        when(mockedLogStep.getId()).thenReturn(null, SAMPLE_UUID);

        logEntry.initialize(mockedLogStep);

        assertNotNull(logEntry.getId());
    }

    @Test
    public void initialize_assigns_generated_uuid_to_log_step_when_not_set_in_log_step() throws LogException {
        when(mockedLogStep.getId()).thenReturn(null);

        logEntry.initialize(mockedLogStep);

        verify(mockedLogStep).setId(anyString());
    }

    @Test
    public void initialize_calls_initializeSpecifics() throws LogException {
        ((LogEntryWrapper) logEntry).initalized = false;

        logEntry.initialize(mockedLogStep);

        assertTrue(((LogEntryWrapper) logEntry).initalized);
    }
}

class LogEntryWrapper extends LogEntry {

    protected boolean initalized = false;

    @Override
    protected void initializeSpecifics(LogStep logstep) {
        initalized = true;
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
