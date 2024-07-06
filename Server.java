import javax.swing.*;
import java.awt.*;

public class Server extends JFrame {

    Server(){
        setLayout(null);

        JPanel pl=new JPanel();
        pl.setBackground(new Color(0x812AD11D));
        pl.setBounds(0,0,400, 70);
        pl.setLayout(null);
        add(pl);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("IMG_2484.HEIC"));
        Image img1= i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i2= new ImageIcon(img1);
        JLabel jl= new JLabel(i2);
        jl.setBounds(5, 20,250, 250);
        pl.add(jl);

        setSize(400,700);
        setLocation(200, 100);
        getContentPane().setBackground(Color.white);

        setVisible(true);//setVisible should always be used at the end statement so user can see all changes
    }

    public static void main(String[] args){
        Server s=new Server();
    }
}
