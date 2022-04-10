import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatUI extends JFrame {
    private JTextArea chatArea;
    private JTextField msgInputField;

    public ChatUI(ActionListener sendButtonListener){
        // Параметры окна
        setBounds(300,400, 500, 500);
        setTitle("Чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Текстовое поле для вывода сообщений
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Нижняя панель с полем для ввода сообщений и кнопкой отправки сообщений
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Отправить");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);
        btnSendMsg.addActionListener(sendButtonListener);
        msgInputField.addActionListener(sendButtonListener);
        setVisible(true);

    }
    public void addMessage(String message){
        chatArea.setText(chatArea.getText()+"\n"+message);
    }
    public String getNewMessage(){
        return msgInputField.getText();
    }
    public void clearInputField(){
        msgInputField.setText("");
    }
}