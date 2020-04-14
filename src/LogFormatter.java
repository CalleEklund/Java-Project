import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter
{

    @Override public String format(final LogRecord logRecord) {
	return logRecord.getThreadID() + "::" + logRecord.getSourceClassName() + "::"
	       + logRecord.getSourceMethodName() + "::"
	       + new Date(logRecord.getMillis()) + "::"
	       + logRecord.getMessage() + "\n";
    }
}
