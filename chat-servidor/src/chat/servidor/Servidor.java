package chat.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private List<RecebeMensagemCliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.aguardarConexoes();
    }

    private void aguardarConexoes() {
        try (ServerSocket serverSocket = new ServerSocket(3333)) {
            System.out.println("Servidor iniciado na porta 3333");

            while (true) {
                System.out.println("Aguardando conexão...");

                Socket socket = serverSocket.accept();

                RecebeMensagemCliente recebeMensagemCliente = new RecebeMensagemCliente(socket, this);
                new Thread(recebeMensagemCliente).start();

                this.clientes.add(recebeMensagemCliente);
                System.out.println("Novo cliente conectado: " + socket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void enviarMensagem(String mensagem) {
        for (RecebeMensagemCliente cliente : this.clientes) {
            cliente.enviarMensagem(mensagem);
        }
    }
}
