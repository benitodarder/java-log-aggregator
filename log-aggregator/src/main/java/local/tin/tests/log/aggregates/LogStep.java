package local.tin.tests.log.aggregates;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author benitodarder
 */
public class LogStep {

    private UUID id;
    private String message;
    private long timestamp;
    private Map<String, Object> additionals; 

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getAdditionals() {
        if (additionals == null) {
            additionals = new HashMap<>();
        }
        return additionals;
    }

    public void setAdditionals(Map<String, Object> additionals) {
        this.additionals = additionals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.message);
        hash = 67 * hash + (int) (this.timestamp ^ (this.timestamp >>> 32));
        hash = 67 * hash + Objects.hashCode(this.additionals);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogStep other = (LogStep) obj;
        if (this.timestamp != other.timestamp) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return !Objects.equals(this.additionals, other.additionals);
    }
    
    
}
