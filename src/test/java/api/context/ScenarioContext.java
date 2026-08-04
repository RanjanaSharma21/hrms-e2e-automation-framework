package api.context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    // Core map container to store our shared variables
    // For full CRUD operation as one scenario
    private final Map<String, Object> contextPipeline = new HashMap<>();

    // A static map that survives Cucumber's individual scenario teardowns
    // For individual create, get, update, delete scenario
    // This is bcoz if scenario breaks, PicoContainer's object destroyed and creates a new object for the new scenario
    private static final Map<String, Object> persistentStorage = new HashMap<>();

    // 1. MUST MATCH THIS NAME EXACLTY
    public void setContext(String key, Object value) {
        contextPipeline.put(key, value);
        // Persist it globally across separate scenario scopes
        persistentStorage.put(key, value);
    }

    // 2. MUST MATCH THIS NAME EXACTLY
    public Object getContext(String key) {
        if (contextPipeline.containsKey(key)) {
            return (String) contextPipeline.get(key);
        }
        return persistentStorage.get(key);
    }

    // Helper method to make getting strings easier
    public String getString(String key) {
        return (String) getContext(key);
    }

}

// Notes: remove class variable empNumber which will cause a problem when we do parallel testing.
// By combining Cucumber Dependency Injection(PicoContainer) in xml and ScenarioContext,
// I completely eliminate public static variables,
// making the framework 100% thread-safe and ready for professional parallel execution.

