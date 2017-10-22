package chatops;

/**
 * @author Serhii Solohub
 *         created on: 26.09.16
 */
public class Utils {


    public static String getEnvValue(String envVarName, String defaultValue) {
        String envVar = System.getenv(envVarName);
        if (envVar != null && envVar.length() > 0) {
            return envVar;
        } else {
            return defaultValue;
        }
    }
}
