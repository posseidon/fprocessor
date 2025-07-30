package hu.elte.inf.pet.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        logger.info("Starting fprocessor application");

        try {
            FileHandler fileHandler = new FileHandler("/Users/Thai_Binh_Nguyen/Downloads");
            fileHandler.processFiles();
        } catch (IOException e) {
            logger.error("Error initializing FileHandler: {}", e.getMessage(), e);
        }
        
        logger.info("fprocessor application finished");
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
