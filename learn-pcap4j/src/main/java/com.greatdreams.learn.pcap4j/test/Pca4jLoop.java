package com.greatdreams.learn.pcap4j.test;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.LinkLayerAddress;
import org.pcap4j.util.NifSelector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Pca4jLoop {
    private static String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String [] args) throws PcapNativeException, IOException, NotOpenException, InterruptedException {
        String filter = null;
        if (args.length != 0) {
            filter = args[0];
        }

        PcapNetworkInterface nif = new NifSelector().selectNetworkInterface();
        if (nif == null) {
            System.exit(1);
        }

        final PcapHandle handle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10000);

        if (filter != null && filter.length() != 0) {
            handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
        }

        PacketListener listener = packet -> printPacket(packet, handle);

        handle.loop(1000, listener);
    }

    private static void printPacket(Packet packet, PcapHandle ph) {
        StringBuilder sb = new StringBuilder();
        sb.append("A packet captured at ")
                .append(ph.getTimestamp())
                .append(":");
        System.out.println(sb);
        System.out.println(packet);
    }
}
