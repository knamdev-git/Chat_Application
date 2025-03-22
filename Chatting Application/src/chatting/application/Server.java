/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatting.application;

/**
 *
 * @author LENOVO
 */
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server  implements ActionListener {
    
    static JFrame frame = new JFrame();
    JTextField textBox ;        // because we want to use it outside the constructor 
    JPanel midArea;
    static DataOutputStream dout;
    static Box verticalBox = Box.createVerticalBox(); // we have created the vertically aligned Box
// constructor 
    Server(){
        // add components on the frame 
        frame.setLayout(null);
        
        //panel for do some task on the frame 
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(0,128,128)); 
        panel1.setBounds(0,0,450,50);         //because we place null to the frame so we have to set the bounds of the panel , SWing will not automatically add panel to the frame
        panel1.setLayout(null);
        
        
        // Label for holding the back icons 
        ImageIcon back = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = back.getImage().getScaledInstance(25, 25, Image.SCALE_REPLICATE);
        back = new ImageIcon(i2);
      
        JLabel label = new JLabel(back);
        label.setBounds(8,15,20,20);
        
        // this label will be perform when we click on the back image Icon 
        label.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
               System.exit(0);
            }
        });
        
        // add profileImage to the label 
        ImageIcon profile = new ImageIcon(ClassLoader.getSystemResource("icons/gaitonde.jpeg"));
        Image i3 = profile.getImage().getScaledInstance(35, 25, Image.SCALE_REPLICATE);
        profile = new ImageIcon(i3);
        
        JLabel label2 = new JLabel(profile);         // label 2 for holding the profile image 
        label2.setBounds(40, 15, 25, 25);
        
        // label 3 for adding call logo
        ImageIcon callLog = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i4 = callLog.getImage().getScaledInstance(30, 25, Image.SCALE_REPLICATE);
        callLog = new ImageIcon(i4);
        
        JLabel label3 = new JLabel(callLog);
        label3.setBounds(250, 15, 25, 25);

        // label 4 for adding video call logo
        ImageIcon vdoCallLog = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i5 = vdoCallLog.getImage().getScaledInstance(30, 25, Image.SCALE_REPLICATE);
        vdoCallLog = new ImageIcon(i5);
        
        JLabel label4 = new JLabel(vdoCallLog);
        label4.setBounds(320, 15, 25, 25);
        
        // label 5 for adding video call logo
        ImageIcon morevert = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i6 = morevert.getImage().getScaledInstance(10, 20, Image.SCALE_REPLICATE);
        morevert = new ImageIcon(i6);
        
        JLabel label5 = new JLabel(morevert);
        label5.setBounds(380, 15, 25, 25);
        
        //label 6 for Activation Status
        JLabel name = new JLabel();
        name.setText("Ostrin Mylos");
        name.setForeground(Color.WHITE);
        name.setBounds(80, 5, 100, 25);
        name.setFont(new Font("Calibiri",10,15));
        
        //label 6 for Activation Status
        JLabel status = new JLabel();
        status.setText("Online");
        status.setForeground(new Color(50,205,50)); 
        status.setBounds(80,22, 100, 25);
        status.setFont(new Font("consolas",8,10));
        
        //mid Area of the application where our messages box will be display
        midArea = new JPanel();
        midArea.setBounds(5,75,440,570);
        frame.add(midArea);
        
        // now adding the textField to the bottom of the frame 
        textBox = new JTextField();
        textBox.setBounds(0,650,350,30);
        textBox.setBackground(new Color(245,230,218)); 
        
        // button beside the textBox
        JButton sendButton = new JButton("Send");
        sendButton.setBounds(350,650,100,30);
        sendButton.setFocusable(false);
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(0,200,83)); // Light Green Color
        sendButton.addActionListener(this);
        
        //adding images and components to the panel through Labels
        panel1.add(label);
        panel1.add(label2);
        panel1.add(label3);
        panel1.add(label4);
        panel1.add(label5);
        panel1.add(name);
        panel1.add(status);
        
        // Operations on Frame add components to it         
        frame.add(panel1);
        frame.add(textBox);
        frame.add(sendButton);
        frame.setSize(450,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(200,30);         // set Location of the frame because i want to display it on center of the screen 
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(new Color(245,245,245));
        frame.setResizable(false);
        frame.setVisible(true);
        }
  
    public void actionPerformed(ActionEvent e){         
    String message = textBox.getText();

    if (!message.isEmpty()) { // Ensure message is not empty
        midArea.setLayout(new BorderLayout());

        JPanel formattedMessage = formatLabel(message); // Apply formatting

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(formattedMessage, BorderLayout.LINE_END); // Align to right

        verticalBox.add(rightPanel);
        verticalBox.add(Box.createVerticalStrut(15)); // Add spacing

        midArea.add(verticalBox, BorderLayout.PAGE_START); // Align to top-left

        midArea.revalidate();
        midArea.repaint();

        // send msg 
        try{
            dout.writeUTF(message); // we are just sending the msg to the client class     
        } 
        catch(Exception ex){
            ex.printStackTrace();
        }    
        
        textBox.setText(""); // Clear input field
    }
}
     
     public static JPanel formatLabel(String message){
         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
         
         JLabel label =  new JLabel("<html><p style=\"width = 150px\">"+message+"</p></html>");
         
         // now do some styling to the label 
         label.setFont(new Font("Tahoma", Font.PLAIN, 15));
         label.setBackground(new Color(255,229,127)); // Light gold
         label.setBorder(new EmptyBorder(15,15,15,50));
         
         label.setOpaque(true);
         
         Calendar cal = Calendar.getInstance();
         
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // we can't do hard code here , we have to set the value dynamically 
         
         JLabel  time = new JLabel();
         time.setText(sdf.format(cal.getTime()));

         panel.add(label);
         panel.add(time);
         return panel;
     }

    // main method
       public static void main(String[] args){
         new Server();   
         
         // making server With ServerSocket class so, 
         try{
           ServerSocket sk = new ServerSocket(6001); // this starts server
           
           while(true){ // with while we are accepting messages infinitely
               Socket s = sk.accept(); // accept msgs from client 
               DataInputStream din = new DataInputStream(s.getInputStream()); // to recieve all msg
                dout = new DataOutputStream(s.getOutputStream()); // to send msg we use getOutputStream 
               
               while(true){
                   
                   // all below steps for the incoming msgs from client class 
                   
                   String msg = din.readUTF() ; // read the data as input
                   // we want to display on frame 
                   JPanel panel = formatLabel(msg); // just format the size and decorate the msg bubble 
                   
                   JPanel msgBoxLeft = new JPanel(new BorderLayout());
                   msgBoxLeft.add(panel, BorderLayout.LINE_START ); // add the incoming message to the left of the box 
                   verticalBox.add(msgBoxLeft); // add the new client msg to the verticall box
                   
                   frame.validate();
                   
                    
               }
            }
         }
         catch(Exception e){
             e.printStackTrace();
         }
       }
         
}


