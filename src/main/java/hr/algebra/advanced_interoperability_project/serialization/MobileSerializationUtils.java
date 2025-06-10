package hr.algebra.advanced_interoperability_project.serialization;

import hr.algebra.advanced_interoperability_project.dto.HackerDTO;
import hr.algebra.advanced_interoperability_project.dto.MobileDTO;
import hr.algebra.advanced_interoperability_project.exception.DeserializationWhitelistException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class MobileSerializationUtils {
    private static final String FILE_NAME = "mobile.ser";
    private static final HackerDTO HACKER_DTO = new HackerDTO("Trying to exploit");

    private MobileSerializationUtils() {}

    public static void serializeMobileToFile(MobileDTO mobileDTO) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(mobileDTO);
            //oos.writeObject(HACKER_DTO);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred during serialization to " + FILE_NAME, e);
        }
    }

    public static MobileDTO deserializeMobileFromFileAndValidate() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return readMobileDtoFromStream(ois);
        } catch (IOException | ClassNotFoundException | DeserializationWhitelistException e) {
            throw new IllegalStateException("Error deserializing mobile object", e);
        }
    }

    private static MobileDTO readMobileDtoFromStream(ObjectInputStream ois)
            throws IOException,
            ClassNotFoundException,
            DeserializationWhitelistException {
        MobileDTO mobileDTO = null;
        boolean eofReached = false;

        while (!eofReached) {
            try {
                Object currentObject = ois.readObject();
                validateWhiteListedObjects(currentObject);
                if (currentObject instanceof MobileDTO) {
                    mobileDTO = (MobileDTO) currentObject;
                }
            } catch (EOFException e) {
                eofReached = true;
            }
        }

        return mobileDTO;
    }

    private static void validateWhiteListedObjects(Object obj) throws DeserializationWhitelistException {
        Set<String> whitelist = new HashSet<>();
        whitelist.add(MobileDTO.class.getName());
        whitelist.add(String.class.getName());

        String className = obj.getClass().getName();

        if (!whitelist.contains(className)) throw new DeserializationWhitelistException(className);
    }
}