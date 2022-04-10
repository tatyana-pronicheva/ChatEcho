import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    ChatUI chatUI;

    public Client(){
        try {
            socket = new Socket("localhost", 8881);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            chatUI = new ChatUI(createSendButtonListener());
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String strFromServer = in.readUTF();
                            chatUI.addMessage("Ответ от сервера: "+strFromServer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }}

    private ActionListener createSendButtonListener(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    out.writeUTF(chatUI.getNewMessage());
                    chatUI.addMessage("Вы написали: "+ chatUI.getNewMessage());
                    chatUI.clearInputField();
                }
                catch(IOException io){
                    io.printStackTrace();
                }
            }
        };
    }

}