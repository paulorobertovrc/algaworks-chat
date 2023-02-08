package chat.servidor;

import java.io.*;
import java.net.Socket;

public class RecebeMensagemCliente implements Runnable {
    private Socket socket;
    private Servidor servidor;

    public RecebeMensagemCliente(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Aguardando mensagem do cliente...");

            try {
                DataInput dataInput = new DataInputStream(this.socket.getInputStream());
                String mensagem = dataInput.readUTF();
                this.servidor.enviarMensagem(mensagem);
            } catch (EOFException e) {
                System.out.println("Cliente desconectado: " + this.socket.getInetAddress().getHostAddress());
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void enviarMensagem(String mensagem) {
        try {
            OutputStream outputStream = this.socket.getOutputStream();
            DataOutput dataOutput = new DataOutputStream(outputStream);
            dataOutput.writeUTF(mensagem);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
