package hr.algebra.advanced_interoperability_project.serialization;

import hr.algebra.advanced_interoperability_project.dto.MobileDTO;
import hr.algebra.advanced_interoperability_project.exception.DeserializationWhitelistException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class MobileSerializationUtils {
    private static final String FILE_NAME = "mobile.ser";

    private MobileSerializationUtils() {
        // Prevent instantiation
    }

    public static void serializeMobileToFile(MobileDTO mobileDTO) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(mobileDTO);
        } catch (IOException e) {
            throw new RuntimeException("Error serializing mobile object", e);
        }
    }

    public static MobileDTO deserializeMobileFromFileAndValidate() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            validateWhiteListedObjects(obj);
            return (MobileDTO) obj;
        } catch (IOException | ClassNotFoundException | DeserializationWhitelistException e) {
            throw new RuntimeException("Error deserializing mobile object", e);
        }
    }

    private static void validateWhiteListedObjects(Object obj) throws DeserializationWhitelistException {
        Set<String> whitelist = new HashSet<>();
        whitelist.add(MobileDTO.class.getName());

        String className = obj.getClass().getName();

        if (!whitelist.contains(className)) {
            throw new DeserializationWhitelistException("Class " + className + " is not whitelisted for deserialization.");
        }
    }
}
