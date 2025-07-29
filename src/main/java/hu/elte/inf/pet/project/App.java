package hu.elte.inf.pet.project;

import java.util.HashMap;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        Map<String, String> switches = parseArguments(args);
    }

    private static Map<String, String> parseArguments(String[] args) {
        Map<String, String> result = new HashMap<>();
        for(String arg : args){
            String[] parts = arg.split("=",2);
            if(parts.length == 2){
                String key = parts[0].trim();
                String value = parts[1].trim();
                result.put(key, value);
            } else {
                throw new IllegalArgumentException("Invalid argument format: " + arg);
            }
        }

        return result;
    }
}
