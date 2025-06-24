package mutacionalVvs;

import java.io.*;
import java.net.*;

public class ServidorCentral {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(20000);
            System.out.println("🟢 ServidorCentral escuchando en puerto 20000...");
        } catch (IOException e) {
            System.err.println("❌ No se puede escuchar en puerto: 20000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
        	  System.out.println("⏳ Esperando cliente...");
            clientSocket = serverSocket.accept();  System.out.println("✅ Cliente conectado al servidor central.");
        } catch (IOException e) {
            System.err.println("❌ Falla conexión");
            System.exit(1);
        }

        PrintWriter outServidorCentral = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader inServidorCentral = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

       
        Socket socketClima = null, socketHoroscopo = null;
        PrintWriter outClima = null, outHoroscopo = null;
        BufferedReader inClima = null, inHoroscopo = null;

        String inputLine, outputLine;

        while ((inputLine = inServidorCentral.readLine()) != null) {
            outputLine = inputLine;

            if (inputLine.equalsIgnoreCase("c")) { // Cliente quiere conectarse con el servidor del clima
                try {
                    if (socketClima == null) { 
                        socketClima = new Socket("localhost", 20001);
                        outClima = new PrintWriter(socketClima.getOutputStream(), true);
                        inClima = new BufferedReader(new InputStreamReader(socketClima.getInputStream()));
                    }
                    outClima.println(outputLine); 
                    outputLine = inClima.readLine(); 
                } catch (IOException e) {
                    System.err.println("No se puede conectar con el servidor del clima.");
                }
            } 
            
            
            else if (inputLine.equalsIgnoreCase("h")) { // Cliente quiere conectarse con el servidor del horóscopo
                try {
                    if (socketHoroscopo == null) { 
                        socketHoroscopo = new Socket("localhost", 20002);
                        outHoroscopo = new PrintWriter(socketHoroscopo.getOutputStream(), true);
                        inHoroscopo = new BufferedReader(new InputStreamReader(socketHoroscopo.getInputStream()));
                    }
                    outHoroscopo.println(outputLine); 
                    outputLine = inHoroscopo.readLine(); 
                } catch (IOException e) {
                    System.err.println("No se puede conectar con el servidor del horóscopo.");
                }
            }

            outServidorCentral.println("Devuelvo al cliente: " + outputLine);

            if (outputLine.equals("salir")) // Cierra la conexión si el mensaje recibido es "salir"
                break;
        }

        // Cerrar conexiones
        if (outClima != null) outClima.close();
        if (inClima != null) inClima.close();
        if (socketClima != null) socketClima.close();

        if (outHoroscopo != null) outHoroscopo.close();
        if (inHoroscopo != null) inHoroscopo.close();
        if (socketHoroscopo != null) socketHoroscopo.close();

        inServidorCentral.close();
        outServidorCentral.close();
        clientSocket.close();
        serverSocket.close();
    }
}