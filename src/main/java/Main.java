/**
 *
 * @author carlos.perez
 * @author kevin.payanene
 * @author harold.quiceno
 */
import java.util.Random;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int [] users_id = {};
        String [] users_names = {};
        int [] passwords = {};
        
        menu();
        System.out.println("\nIngrese una opción: ");
        int opcion = teclado.nextInt();
        
        switch(opcion){
            case 1:
                System.out.println("Ingrese el id del usuario: ");
                int user_id = teclado.nextInt();
                if(validateUserId(user_id, users_id) == false){
                    System.out.println("El usuario no tiene cuenta bancaria.");
                }else {
                    System.out.println("Ingrese la contraseña: ");
                    int password = teclado.nextInt();

                    if(validatePassword(password) == false){
                        System.out.println("Contraseña invalida.");
                    }else{
                        System.out.println("Bienvenido usuario: "+user_id);
                    }
                }
            
            case 2:
                makeRegister(users_id, passwords);
                break;

            case 3:
                System.out.println("Programa finalizado.");
                break;
        }
   
        
    }
    public static void menu(){
        System.out.println("BIENVENIDO A ATM BANK\n");
        System.out.println("1.Ingresar");
        System.out.println("2.Registrarse");
        System.out.println("3.Salir");
                        
    }
    
    public static boolean validateUserId(int user_id, int[] users_id){
        boolean bandera = false;
        
        for (int user: users_id) {
            if(user_id == user){
                bandera = true;
            }
        }
        
        return bandera;
    }
    
    public static boolean validatePassword(int password){
        boolean bandera = false;
        
        return bandera;
    }
    public static void makeRegister(int [] users_id, int [] passwords){
        Scanner teclado = new Scanner(System.in);
        String name;
        int userId, password;
        boolean bandera = true;
        int contador = 0;
        
        System.out.println("Bienvenido al registro de su cuenta bancaria: ");
        System.out.println("Ingrese la cedula: ");
        userId = teclado.nextInt();
        System.out.println("Ingrese el nombre: ");
        name = teclado.next();
        System.out.println("Ingrese la contraseña: ");
        password = teclado.nextInt();
        
        while(bandera){
            if (users_id[contador] == 0){
                users_id[contador ] = userId;
                bandera = false;
            }
            passwords[contador] = password;
        
        }
        
    }
}
