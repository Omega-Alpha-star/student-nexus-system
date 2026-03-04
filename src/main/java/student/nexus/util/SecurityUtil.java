package student.nexus.util;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtil {

    public static String hash(String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt());
    }

    public static boolean verify(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }
}
