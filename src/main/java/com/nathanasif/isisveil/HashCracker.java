package com.nathanasif.isisveil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HashCracker {
    private final String captureFilePath;
    private final String wordlistPath;

    public HashCracker(String captureFilePath, String wordlistPath) {
        this.captureFilePath = captureFilePath;
        this.wordlistPath = wordlistPath;
    }

    public void initiateCrack() {
        System.out.println("[*] Initiating offline analysis on " + captureFilePath);
        
        ProcessBuilder pb = new ProcessBuilder("aircrack-ng", captureFilePath, "-w", wordlistPath);
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Aircrack] " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("[+] Cracker process exited with code: " + exitCode);

        } catch (Exception e) {
            System.err.println("[-] Failed to execute offline cracker: " + e.getMessage());
            System.err.println("[-] Ensure aircrack-ng is installed in your Lubuntu environment.");
        }
    }
}