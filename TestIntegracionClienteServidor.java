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
        "Está despejado",
        "ALERTA: Vientos fuertes",
        "Easter egg",
        "Con probabilidad de granizo"
    );

    private final List<String> respuestasHoroscopo = Arrays.asList(
        "Libra: Hoy encontrarás el equilibrio que buscabas.",
        "Géminis: Tu curiosidad te llevará a nuevas oportunidades.",
        "Acuario: Tu creatividad estará en su punto máximo hoy.Si tu nombre empieza con E serias mas feliz que nunca!"
    );

    @Test
    public void testConsultaClima() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 20000), 3000);
        socket.setSoTimeout(3000);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println("c");
        String respuesta = in.readLine();
        System.out.println("Respuesta clima: " + respuesta);
        assertNotNull(respuesta);
        assertTrue(respuesta.startsWith("Devuelvo al cliente: "));
        String soloClima = respuesta.replace("Devuelvo al cliente: ", "").trim();
        assertTrue(respuestasClima.contains(soloClima), "Respuesta de clima no reconocida");

        out.println("salir");
        in.readLine();

        in.close();
        out.close();
        socket.close();
    }

    @Test
    public void testConsultaHoroscopo() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 20000), 3000);
        socket.setSoTimeout(3000);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println("h");
        String respuesta = in.readLine();
        System.out.println("🔮 Respuesta horóscopo: " + respuesta);
        assertNotNull(respuesta);
        assertTrue(respuesta.startsWith("Devuelvo al cliente: "));
        String soloHoroscopo = respuesta.replace("Devuelvo al cliente: ", "").trim();
        assertTrue(respuestasHoroscopo.contains(soloHoroscopo), "Respuesta de horóscopo no reconocida");

        out.println("salir");
        in.readLine();

        in.close();
        out.close();
        socket.close();
    }
}
