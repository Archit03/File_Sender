import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class client {
     public static void main(String[] args) {
        final File[] FileToSend = new File[1];

         JFrame jFrame = new JFrame("Archit's client");

         jFrame.setSize(450,450);

         jFrame.setLayout(new BoxLayout(jFrame.getContentPane(),BoxLayout.Y_AXIS));
         jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         JLabel jlTitle = new JLabel("Archit's file sender");
         jlTitle.setFont(new Font("Ariel", Font.BOLD,25));
         jlTitle.setBorder(new EmptyBorder(20,0,10,0));
         jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

         JLabel jlFileName = new JLabel("Choose a file to send");
         jlFileName.setFont(new Font("Arial", Font.BOLD,20));
         jlFileName.setBorder(new EmptyBorder( 50,0,0,0));
         jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

         JPanel jpButton = new JPanel();
         jpButton.setBorder(new EmptyBorder(75,0,10,0));

         JButton  jpSendFile = new JButton("Send File");
         jpSendFile.setPreferredSize(new Dimension(150,75));
         jpSendFile.setFont(new Font("Arial",Font.BOLD,20));

         JButton jbChooseFile = new JButton("Choose File");
         jbChooseFile.setPreferredSize(new Dimension(150,75));
         jbChooseFile.setFont(new Font("Arial", Font.BOLD, 20));

         jpButton.add(jpSendFile);
         jpButton.add(jbChooseFile);

         jbChooseFile.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
           JFileChooser jlFileChooser = new JFileChooser();
           jlFileChooser.setDialogTitle("Choose a file to send");

           if(jlFileChooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
                 FileToSend[0] = jlFileChooser.getSelectedFile();
                 jlFileName.setText("The file you want to send is:" + FileToSend[0].getName());
           }
          }
         });

         jpSendFile.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
           if(FileToSend[0] == null){
            jlFileName.setName("Please choose a file first");
           }else{
            try {
             FileInputStream fileInputStream = new FileInputStream(FileToSend[0].getAbsolutePath());
             Socket socket = new Socket("localhost", 1234);

             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

             String fileName = FileToSend[0].getName();
             byte[] fileNameBytes = fileName.getBytes();

             byte[] fileContentBytes = new byte[(int)FileToSend[0].length()];

             dataOutputStream.writeInt(fileNameBytes.length);
             dataOutputStream.write(fileNameBytes);

             dataOutputStream.writeInt(fileContentBytes.length);
             dataOutputStream.write(fileContentBytes);
            } catch (IOException ex) {
             ex.printStackTrace();
            }
           }
          }
         });
         jFrame.add(jlTitle);
         jFrame.add(jlFileName);
         jFrame.add(jpButton);
         jFrame.setVisible(true);

    }
}
