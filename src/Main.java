import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Socket salida = null;
        try {
            salida = new Socket("localhost", 8080);
            System.out.println("Conectado al servidor.");
        } catch (IOException e) {
            System.out.println("Hubo problemas en la conexion de red");
            System.exit(1);
        }
        try (
                PrintWriter escritor = new PrintWriter(salida.getOutputStream(), true);
                BufferedReader lector = new BufferedReader(new InputStreamReader(salida.getInputStream()));
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String mensajeServidor;
            while ((mensajeServidor = lector.readLine()) != null) {
                System.out.println("SERVIDOR: " + mensajeServidor);

                if (mensajeServidor.equals("FIN")) {
                    break;
                }


                if (mensajeServidor.contains("Escribe un número:") ||
                    mensajeServidor.contains("Hay perro, eres un master, ¿Quieres volver a jugar? (SI)(NO)")) {
                    String respuesta = teclado.readLine();
                    escritor.println(respuesta);
                }
            }
        } catch (IOException e) {
            System.out.println("Error de comunicacion entre los sockets");
            System.exit(2);
        } finally {
            try {
                salida.close();
            } catch (IOException e) {
                System.out.println("Hubo problemas en la conexion en la red");
                System.exit(1);
            }
        }
    }
}