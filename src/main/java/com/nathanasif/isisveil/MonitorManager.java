package com.nathanasif.isisveil;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

public class MonitorManager {
    private final String interfaceName;
    private PcapHandle handle;
    private boolean isRunning;

    public MonitorManager(String interfaceName) {
        this.interfaceName = interfaceName;
        this.isRunning = false;
    }

    public void startCapture(HandshakeHandler handshakeHandler) {
        try {
            PcapNetworkInterface nif = Pcaps.getDevByName(interfaceName);
            if (nif == null) {
                System.err.println("[-] Error: Interface " + interfaceName + " not found.");
                System.err.println("[-] Ensure your Realtek adapter is active in monitor mode.");
                return;
            }

            int snaplen = 65536;
            int timeout = 10;
            this.handle = nif.openLive(snaplen, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, timeout);

            // Apply BPF filter for EAPOL traffic (EtherType 0x888e)
            String filter = "ether proto 0x888e";
            handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
            
            System.out.println("[+] Interface bound successfully: " + nif.getName());
            System.out.println("[+] BPF Filter active: " + filter);
            System.out.println("[+] Isis Veil is listening for handshakes...\n");

            isRunning = true;
            PacketListener listener = packet -> {
                if (isRunning) {
                    byte[] rawData = packet.getRawData();
                    handshakeHandler.processPacket(rawData);
                }
            };

            handle.loop(0, listener);

        } catch (PcapNativeException | NotOpenException | InterruptedException e) {
            if (isRunning) {
                System.err.println("[-] Capture Error: " + e.getMessage());
            }
        }
    }

    public void stopCapture() {
        isRunning = false;
        if (handle != null && handle.isOpen()) {
            handle.close();
            System.out.println("[*] Monitor session closed safely.");
        }
    }
}