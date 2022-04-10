
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    public void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress("localhost", 8881));
        channel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started");

        while (true) {
            selector.select();
            System.out.println("New selector event");
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    System.out.println("New selector acceptable event");
                    register(selector, channel);
                }

                if (selectionKey.isReadable()) {
                    System.out.println("New selector readable event");
                    readAndReply(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    public void register(Selector selector, ServerSocketChannel channel) throws IOException {
        SocketChannel client = channel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("New client is connected");
    }

    public void readAndReply(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        client.read(byteBuffer);
        String message = new String(byteBuffer.array());
        System.out.println("New message: " + message);
        byteBuffer.flip();
        client.write(byteBuffer);
    }

}