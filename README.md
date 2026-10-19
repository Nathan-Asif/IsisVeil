# Isis Veil

**Isis Veil** is an object-oriented Java wireless hacking tool designed to intercept over-the-air 802.11 traffic. By interfacing directly with raw packet capture streams, the tool isolates and sniffs WPA/WPA2 EAPOL 4-way handshakes to extract cryptographic material for offline password auditing.

## Features

* **Object-Oriented Architecture:** Modular design separating network interface management, frame capture, payload parsing, and offline cracking execution.
* **Low-Level Packet Capture:** Leverages **Pcap4J** and native `libpcap` bindings to stream raw wireless frames from adapters operating in monitor mode.
* **Optimized BPF Filtering:** Applies Berkeley Packet Filters (`ether proto 0x888e`) directly at the kernel layer to target EAPOL frames instantly.
* **Cracker Integration:** Programmatically interfaces with offline recovery tools like `aircrack-ng` via Java's `ProcessBuilder`.

## Tech Stack

* **Language:** Java 17+
* **Packet Capture Library:** Pcap4J (Core & Factory Static)
* **Build Tool:** Maven
* **Environment:** Lubuntu Linux (VirtualBox) with Monitor Mode wireless adapters (Realtek RTL8188 series)

## Project Structure

```text
IsisVeil/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── nathanasif/
                    └── isisveil/
                        ├── IsisVeil.java          # Main Application Entry Point
                        ├── MonitorManager.java    # Handles Network Adapter & Pcap4J Capture
                        ├── HandshakeHandler.java  # Parses EAPOL Frames & Saves Hashes
                        └── HashCracker.java       # Manages Offline Cracking Integration
