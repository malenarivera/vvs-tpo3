package mutacionalVvs;

import org.testng.annotations.*;
import java.io.*;
import java.net.*;
import java.util.*;

import static org.testng.Assert.*;

public class TestIntegracionClienteServidor {

    private final List<String> respuestasClima = Arrays.asList(
        "Está lloviendo",
        "Está nublado",
        "Hace un calor tremendo",  // probando para que falle
        "ALERTA: Vientos fuertes",
        "Easter egg",
        "Con probabilidad de granizo"
    );

    private final List<String> respuestasHoroscopo = Arrays.asList(
        "Libra: Hoy encontrarás el equilibrio que buscabas.",
        "Géminis: Tu curiosidad te llevará a nuevas oportunidades.",
        "Acuario: Tu creatividad estará en su punto máximo hoy. Si tu nombre empieza con E, serás más feliz que nunca!"
    );

    @Test
    public void testConsultaClima() throws Exception {
        try (
            Socket socket = new Socket("localhost", 20000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            socket.setSoTimeout(3000);

            out.println("c");
            String respuesta = in.readLine();
            System.out.println("☁️ Respuesta clima: " + respuesta);

            assertNotNull(respuesta);
            assertTrue(respuesta.startsWith("Devuelvo al cliente: "));
            String soloClima = respuesta.replace("Devuelvo al cliente: ", "").trim();
            assertTrue(respuestasClima.contains(soloClima), "Respuesta de clima no reconocida: " + soloClima);

            out.println("salir");
            in.readLine(); // Leer cierre
        }
    }

    @Test
    public void testConsultaHoroscopo() throws Exception {
        try (
            Socket socket = new Socket("localhost", 20000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            socket.setSoTimeout(3000);

            out.println("h");
            String respuesta = in.readLine();
            System.out.println("🔮 Respuesta horóscopo: " + respuesta);

            assertNotNull(respuesta);
            assertTrue(respuesta.startsWith("Devuelvo al cliente: "));
            String soloHoroscopo = respuesta.replace("Devuelvo al cliente: ", "").trim();
            assertTrue(respuestasHoroscopo.contains(soloHoroscopo), "Respuesta de horóscopo no reconocida: " + soloHoroscopo);

            out.println("salir");
            in.readLine(); // Leer cierre
        }
    }

    @Test
    public void testMultiplesClientesConcurrentes() throws Exception {
        int cantidadClientes = 5;
        List<Thread> hilos = new ArrayList<>();
        List<String> errores = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < cantidadClientes; i++) {
            Thread hilo = new Thread(() -> {
                try (
                    Socket socket = new Socket("localhost", 20000);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    String consulta = Math.random() < 0.5 ? "h" : "c";
                    out.println(consulta);
                    String respuesta = in.readLine();
                    System.out.println("🧵 Cliente hilo recibió: " + respuesta);

                    if (respuesta == null || !respuesta.startsWith("Devuelvo al cliente: ")) {
                        errores.add("Respuesta nula o malformada: " + respuesta);
                        return;
                    }

                    String contenido = respuesta.replace("Devuelvo al cliente: ", "").trim();

                    if (consulta.equals("h")) {
                        if (!respuestasHoroscopo.contains(contenido)) {
                            errores.add("Respuesta horóscopo no válida: " + contenido);
                        }
                    } else {
                        if (!respuestasClima.contains(contenido)) {
                            errores.add("Respuesta clima no válida: " + contenido);
                        }
                    }

                    out.println("salir");
                    in.readLine(); // Leer cierre

                } catch (IOException e) {
                    errores.add("Excepción en hilo cliente: " + e.getMessage());
                }
            });
            hilos.add(hilo);
        }

        // Iniciar todos los hilos
        for (Thread t : hilos) {
            t.start();
        }

        // Esperar a que todos terminen
        for (Thread t : hilos) {
            t.join();
        }

        // Verificar que no haya errores
        assertTrue(errores.isEmpty(), "Se encontraron errores en los clientes:\n" + String.join("\n", errores));
    }
}
