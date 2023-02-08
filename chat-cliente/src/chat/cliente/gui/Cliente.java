package chat.cliente.gui;

import chat.cliente.thread.RecebeMensagemServidor;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Cliente extends JanelaGui {

    private Socket socket;

    public static void main(String[] args) {
        new Cliente();
    }

    @Override
    protected boolean conectar() {
        System.out.println("Conectando ao servidor...");

        try {
            this.socket = new Socket("localhost", 3333);

            RecebeMensagemServidor recebeMensagemServidor = new RecebeMensagemServidor(this.socket, this);
            new Thread(recebeMensagemServidor).start();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void sendMessage(String message) {
        System.out.println("Enviando mensagem: " + message);

        try {
            OutputStream out = socket.getOutputStream();
            DataOutput dataOut = new DataOutputStream(out);
            dataOut.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
