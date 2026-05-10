package com.petwellnessplus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetWellnessPlusApplication {

    public static void main(String[] args) {
        rotateLogOnStartup();
        SpringApplication.run(PetWellnessPlusApplication.class, args);
    }

    private static void rotateLogOnStartup() {
        try {
            Path log = Paths.get("./logs/pet-wellness-plus.log");
            if (Files.exists(log)) {
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
                Path archiveDir = Paths.get("./logs/archive",
                        LocalDate.now().toString());
                Files.createDirectories(archiveDir);
                Files.move(log, archiveDir.resolve(timestamp + "-pet-wellness-plus.log"));
                System.out.println(">> OLD LOG ROTATED SUCCESSFULLY: " + timestamp);
            }
        } catch (Exception e) {
        	System.err.println("\n\n>> FAILED TO ROTATE LOG ON STARTUP: " + e.getMessage()+"\n\n");
            e.printStackTrace();
        }
    }
}
