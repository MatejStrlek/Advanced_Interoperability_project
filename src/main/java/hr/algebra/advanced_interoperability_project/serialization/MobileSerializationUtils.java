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
            //oos.writeObject(new HackerDTO("Trying to exploit"));
        } catch (IOException e) {
            throw new RuntimeException("Error serializing mobile object", e);
        }
    }

    public static MobileDTO deserializeMobileFromFileAndValidate() {
        MobileDTO mobileDTO = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj;
            boolean hasMoreObjects = true;

            while (hasMoreObjects) {
                try {
                    obj = ois.readObject();
                    validateWhiteListedObjects(obj);
                    if (obj instanceof MobileDTO && mobileDTO == null) {
                        mobileDTO = (MobileDTO) obj;
                    }
                } catch (EOFException eof) {
                    hasMoreObjects = false;
                }
            }

            if (mobileDTO == null) {
                throw new RuntimeException("No MobileDTO object found in stream.");
            }

            return mobileDTO;
        } catch (IOException | ClassNotFoundException | DeserializationWhitelistException e) {
            throw new RuntimeException("Error deserializing mobile object", e);
        }
    }

    private static void validateWhiteListedObjects(Object obj) throws DeserializationWhitelistException {
        Set<String> whitelist = new HashSet<>();
        whitelist.add(MobileDTO.class.getName());
        whitelist.add(String.class.getName());

        String className = obj.getClass().getName();

        if (!whitelist.contains(className)) {
            throw new DeserializationWhitelistException(className + " is not whitelisted for deserialization.");
        }
    }
}