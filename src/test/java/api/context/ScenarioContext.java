package api.context;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    @Setter
    @Getter
    private String token;

    @Setter
    @Getter
    private String employeeId;

    // Core map container to store our shared variables
    // For full CRUD operation as one scenario
    private final Map<String, Object> context = new HashMap<>();

    public void setContext(String key, Object value) {
        context.put(key, value);
    }

    public Object getContext(String key) {
            return context.get(key);
    }

    // Helper method to make getting strings easier
    public String getString(String key) {
        return (String) getContext(key);
    }
}

// Notes: remove class variable employee id which will cause a problem when we do parallel testing.
// By combining Cucumber Dependency Injection(PicoContainer) in xml and ScenarioContext,
// I completely eliminate public static variables,
// making the framework 100% thread-safe and ready for professional parallel execution.

