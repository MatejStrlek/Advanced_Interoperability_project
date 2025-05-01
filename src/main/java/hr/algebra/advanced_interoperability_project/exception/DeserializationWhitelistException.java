package hr.algebra.advanced_interoperability_project.exception;

public class DeserializationWhitelistException extends RuntimeException {
    public DeserializationWhitelistException(String className) {
        super("Class " + className + " is not in the whitelist for deserialization.");
    }
}
