package dsekercioglu.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import dsekercioglu.general.characters.BlackMarlin;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.characters.Marlin;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.multiPlayer.CharacterInfo;
import dsekercioglu.general.multiPlayer.ControlInfo;
import dsekercioglu.general.multiPlayer.PlayerInfo;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WildNatureServer {

    static HashMap<String, ControlInfo> currentControls = new HashMap();
    String s = "192.168.1.20";
    static Environment env = new Environment();
    static Server server;

    public static void main(String[] args) throws IOException {
        server = new Server();
        server.start();
        server.bind(54556, 54778);

        System.out.println(InetAddress.getLocalHost().getHostAddress());

        Kryo kryo = server.getKryo();
        kryo.register(ControlInfo.class);
        kryo.register(CharacterInfo.class);
        kryo.register(PlayerInfo.class);
        kryo.register(ArrayList.class);
        kryo.register(DrawInfo.class);

        server.addListener(new Listener() {
            public void connected(Connection cnctn) {
                super.connected(cnctn);
                System.out.println("A player is connected.");
            }

            public void received(Connection connection, Object object) {
                if ((object instanceof String)) {
                    String s = (String) object;
                    String name = s.substring(0, s.indexOf("/"));
                    s = s.replace(name + "/", "");
                    Swimmer p = null;
                    if ("Marlin".equals(s)) {
                        p = new Marlin(name, 0.0F, 0.0F, null);
                    } else if ("BlackMarlin".equals(s)) {
                        p = new BlackMarlin(name, 0.0F, 0.0F, null);
                    }
                    if (p != null) {
                        WildNatureServer.env.addCharacter(p);
                        ControlInfo c = new ControlInfo();
                        c.mouseX = 0;
                        c.mouseY = 0;
                        c.mousePressed = false;
                        c.name = name;
                        WildNatureServer.currentControls.put(c.name, c);
                    }
                } else if ((object instanceof ControlInfo)) {
                    ControlInfo controlInfo = (ControlInfo) object;
                    WildNatureServer.currentControls.put(controlInfo.name, controlInfo);
                }
            }

            public void disconnected(Connection connection) {
                System.out.println(System.currentTimeMillis());
                System.out.println("Client disconnected.");
            }
        });
        System.out.println("done: " + System.currentTimeMillis());
        for (;;) {
            if (!env.characters.isEmpty()) {
                env.update(currentControls);
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException ex) {
                Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}