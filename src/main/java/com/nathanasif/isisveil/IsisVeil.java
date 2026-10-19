package com.nathanasif.isisveil;

public class IsisVeil {
    public static void main(String[] args) {
        String interfaceName = args.length > 0 ? args[0] : "wlan0mon";

        System.out.println("==================================================");
        System.out.println("       ISIS VEIL : Object-Oriented Sniffer        ");
        System.out.println("==================================================");

        HandshakeHandler handler = new HandshakeHandler();
        MonitorManager monitor = new MonitorManager(interfaceName);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[!] Shutdown signal received. Cleaning up resources...");
            monitor.stopCapture();
        }));

        monitor.startCapture(handler);
    }
}