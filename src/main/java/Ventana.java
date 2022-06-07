
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author carlos.perez
 */
public class Ventana extends JFrame{
    Cajero cajero = new Cajero();
    
    public Ventana(){
        setSize(500, 500);
        setTitle("ATM BANK");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        iniciarCompoenente();
    }
    
    public void iniciarCompoenente(){
        //PANEL
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setLayout(null);
        
        //ETIQUETAS
        JLabel etiqueta1 = new JLabel("INICIAR SESIÓN");
        etiqueta1.setBounds(10, 10, 50, 50);
        panel.add(etiqueta1);
        
        //CAMPOS DE TEXTO
        JTextField texto1 = new JTextField();
        texto1.setBounds(50, 50, 100, 100);
        panel.add(texto1);
        //BOTONES
        JButton boton1 = new JButton("CONTINUAR");
        boton1.setBounds(100, 100, 100, 100);
        panel.add(boton1);
        //EVENTOS
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
                //cajero.validateUserId(datos, user_id);
            }
        };
        
        boton1.addActionListener(evento);
    }

}
