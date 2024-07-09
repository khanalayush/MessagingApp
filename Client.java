import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client implements ActionListener {

    JTextField text;
    static JPanel jp;
    static Box vertical= Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f= new JFrame();
    static int MessageIdCounter= 1;

    Client(){
        f.setLayout(null);

        JPanel pl=new JPanel();
        pl.setBackground(new Color(0x093F67));
        pl.setBounds(0,0,500, 70);
        pl.setLayout(null);
        f.add(pl);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image img1= i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i2= new ImageIcon(img1);
        JLabel back= new JLabel(i2);
        back.setBounds(5, 20,25, 25);
        pl.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("icons/goli.png"));
        Image img2= i3.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i4= new ImageIcon(img2);
        JLabel profile= new JLabel(i4);
        profile.setBounds(40, 10,50, 50);
        pl.add(profile);

        ImageIcon i5= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image img3 = i5.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(img3);
        JLabel video= new JLabel(i6);
        video.setBounds(300, 20,30, 30);
        pl.add(video);

        ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image img4 = i7.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i8 = new ImageIcon(img4);
        JLabel phone= new JLabel(i8);
        phone.setBounds(360, 20,35, 30);
        pl.add(phone);

        ImageIcon i9= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image img5 = i9.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i10 = new ImageIcon(img5);
        JLabel dots= new JLabel(i10);
        dots.setBounds(420, 20,10, 25);
        pl.add(dots);

        JLabel name= new JLabel("Goli");
        name.setBounds(110,15, 100, 20);
        name.setForeground(Color.white);
        name.setFont(new Font("SAS_SERIF", Font.BOLD , 18));
        pl.add(name);

        JLabel status= new JLabel("Active Now");
        status.setBounds(110,35, 100, 15);
        status.setForeground(Color.white);
        status.setFont(new Font("SAS_SERIF", Font.BOLD , 12));
        pl.add(status);

        jp= new JPanel();
        jp.setBounds(5, 75, 440, 570);
        f.add(jp);

        text= new JTextField();
        text.setBounds(5, 655, 310, 35);
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        f.add(text);

        JButton send= new JButton("Send");
        send.setBounds(320, 655, 123,35);
        send.setBackground(new Color(23, 145, 16));
        send.setForeground(Color.white);
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        send.addActionListener(this);
        f.add(send);

        f.setSize(450,700);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.white);

        f.setVisible(true);//setVisible should always be used at the end statement so user can see all changes
    }

    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();

            int messageId = MessageIdCounter++;

            JPanel p2 = formatLabel(messageId,out);

            jp.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            jp.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(int messageId, String out){
        JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel messageIdLabel = new JLabel("message id: " + messageId);
        panel.add(messageIdLabel);

        JLabel output= new JLabel("<html><p style=\"width: 150px\">" + out + "</p><html>");
        output.setFont(new Font("Tahoma", Font.PLAIN,16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10, 15, 10, 50));

        panel.add(output);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");

        JLabel time= new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args){
        Client c=new Client();

        try{
            Socket s= new Socket("127.0.0.1", 6001);
            DataInputStream din= new DataInputStream(s.getInputStream());
            dout= new DataOutputStream(s.getOutputStream());

            while(true) {
                jp.setLayout(new BorderLayout());
                String msg = din.readUTF();
                int messageId= MessageIdCounter++;
                JPanel panel = formatLabel(messageId,msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                jp.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
