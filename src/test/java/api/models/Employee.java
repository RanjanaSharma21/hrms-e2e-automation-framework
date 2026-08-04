package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                  // Generates all getters (getFirstName(), getGender(), etc.), setters, toString, and equals
@NoArgsConstructor     // Generates the default blank constructor (needed for JSON parsing)
@AllArgsConstructor    // Generates the constructor with all fields (used when writing: new Employee(...))
@JsonIgnoreProperties(ignoreUnknown = true) // <-- CRITICAL FIX: Tells Jackson to ignore extra API fields like empNumber, employeeId, etc.

public class Employee {

    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String birthday;
    //private String jobTitle; // Match the Java camelCase naming convention
    // Use Jackson to map Java camelCase to API snake_case
    @JsonProperty("job_title")
    private String jobTitle;

}

// What is a POJO? A Plain Old Java Object is an ordinary Java object used as a data model or data container.
// In API testing, POJOs are used to represent JSON request bodies (Serialization) or
// store API response bodies (Deserialization).
// Professional Status: Mandatory. Every modern framework uses POJOs to map JSON payloads.

