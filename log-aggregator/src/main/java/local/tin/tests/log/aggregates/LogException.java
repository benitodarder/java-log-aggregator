package local.tin.tests.log.aggregates;

/**
 *
 * @author benitodarder
 */
public class LogException extends Exception {

    public LogException() {
    }

    public LogException(String string) {
        super(string);
    }

    public LogException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public LogException(Throwable thrwbl) {
        super(thrwbl);
    }

    public LogException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }


    
}
