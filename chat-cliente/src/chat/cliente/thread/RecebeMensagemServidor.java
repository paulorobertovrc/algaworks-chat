package chat.cliente.thread;

import chat.cliente.gui.JanelaGui;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class RecebeMensagemServidor implements Runnable {

    private Socket socket;
    private JanelaGui janela;

    public RecebeMensagemServidor(Socket socket, JanelaGui janela) {
        this.socket = socket;
        this.janela = janela;
    }

    @Override
    public void run() {
        while (true) {
            try {
                InputStream in = this.socket.getInputStream();
                DataInput dataIn = new DataInputStream(in);
                String mensagemRecebida = dataIn.readUTF();

                janela.adicionarMensagem(mensagemRecebida);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
       }
    }

}
