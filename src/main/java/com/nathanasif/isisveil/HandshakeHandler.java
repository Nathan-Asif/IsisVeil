package com.nathanasif.isisveil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class HandshakeHandler {
    private int handshakeCount = 0;
    private static final String OUTPUT_FILE = "captured_handshake.cap";

    public void processPacket(byte[] rawData) {
        handshakeCount++;
        System.out.println("[+] EAPOL Handshake Frame Detected! [# " + handshakeCount + "] at " + new Date());
        System.out.println("    Payload Size: " + rawData.length + " bytes");

        printHexSnippet(rawData);
        saveToFile(rawData);
    }

    private void printHexSnippet(byte[] data) {
        StringBuilder sb = new StringBuilder();
        int limit = Math.min(data.length, 32);
        for (int i = 0; i < limit; i++) {
            sb.append(String.format("%02X ", data[i]));
        }
        System.out.println("    Snippet: " + sb.toString());
        System.out.println("    ------------------------------------------");
    }

    private void saveToFile(byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE, true)) {
            fos.write(data);
            fos.write(System.lineSeparator().getBytes());
            System.out.println("[*] Frame appended to " + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("[-] Failed to write packet to disk: " + e.getMessage());
        }
    }
}