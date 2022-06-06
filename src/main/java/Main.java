/**
 *
 * @author carlos.perez
 * @author kevin.payanene
 * @author harold.quiceno
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        crearArchivo();

        String[][] datos = new String[5][4];
        int [] billetesCajero = new int[4];
        billetesCajero = llenarCajero(billetesCajero);
        
        
        
        datos = cargarDatos();
        Scanner teclado = new Scanner(System.in);
        String user_id, password, new_user, new_password, new_name;
        boolean bandera = true;
        
        while(bandera){
            
            for (int i = 0; i < billetesCajero.length; i++) {
                System.out.print(billetesCajero[i]+"|");
            }
            System.out.println("");
            
        menu();
        System.out.println("\nIngrese una opción: ");
        int opcion = teclado.nextInt();
        int fila = 0;
        switch (opcion) {
            case 1:
                System.out.println("Ingrese el id del usuario: ");
                user_id = teclado.next();

                if (validateUserId(datos, user_id) == false) {
                    System.out.println("El usuario no tiene cuenta bancaria.");
                } else {
                    System.out.println("Ingrese la contraseña: ");
                    password = teclado.next();

                    if (validatePassword(datos, password) == false) {
                        System.out.println("Contraseña invalida.");
                    } else {
                        fila = posicion(datos, user_id);

                        if (fila == -1){
                            System.out.println("No se encontro el usuario");
                        }else {
                            System.out.println("=======================================================");
                            System.out.println("Bienvenido usuario: "+ datos[fila][0].toUpperCase());
                            System.out.println("=======================================================");
                            System.out.println("");
                            System.out.println("¿qué transacción desea realizar?");
                            menuCajero();
                            int accion = teclado.nextInt();
                            switch(accion){
                                case 1:
                                    System.out.println("CONSULTA DE SALDO");
                                    System.out.println("=======================================================");
                                    System.out.println("El saldo de la cueta "+datos[fila][1]+ " es: $"+datos[fila][3]);
                                    System.out.println("Transacción finalizada. Vuelva pronto...");
                                    System.out.println("=======================================================");
                                    break;
                                case 2: 
                                    System.out.println("RETIRO DE DINERO");
                                    System.out.println("Ingrese la cantidad que desea retirar: $");
                                    int cantidadRetirar = teclado.nextInt();
                                    if (retirarDinero(billetesCajero, cantidadRetirar, datos, fila) == false){
                                        System.out.println("=======================================================");
                                        System.out.println("No tiene saldo suficiente");
                                        System.out.println("Transacción finalizada. Vuelva pronto...");                                        
                                        System.out.println("=======================================================");
                                    }else{
                                        int saldoActual = Integer.parseInt (datos[fila][3]);
                                        saldoActual -= cantidadRetirar;
                                        String saldoFinal = String.valueOf(saldoActual);
                                        datos[fila][3] = saldoFinal;
                                        System.out.println("");
                                        System.out.println("=======================================================");
                                        System.out.println("El valor del retiro fue de: $"+cantidadRetirar);
                                        System.out.println("El saldo actual de la cuenta es de: $"+datos[fila][3]);
                                        System.out.println("Transacción finalizada. Vuelva pronto...");
                                        System.out.println("=======================================================");
                                    };
                                    break;
                                case 3:
                                    System.out.println("CONSIGNACION DE DINERO");
                                    System.out.println("Ingrese la cantidad que desea consignar: $");
                                    int cantidadConsignar = teclado.nextInt();
                                    int saldoActual = Integer.parseInt (datos[fila][3]);
                                    saldoActual += cantidadConsignar;
                                    String saldoFinal= String.valueOf(saldoActual);
                                    datos[fila][3] = saldoFinal;
                                    System.out.println("");
                                    System.out.println("=======================================================");
                                    System.out.println("El valor de la consignación fue de: $"+cantidadConsignar);
                                    System.out.println("El saldo actual de la cuenta es de: $"+datos[fila][3]);
                                    System.out.println("Transacción finalizada. Vuelva pronto...");
                                    System.out.println("=======================================================");
                                    break;
                                default:
                                    System.out.println("NO SE RECONOCE LA ENTRADA");
                            }
                        }
                    }
                }
                break;
            case 2:
                System.out.println("Ingrese el Id para su registro: ");
                new_user = teclado.next();
                System.out.println("Ingrese su nombre para su registro: ");
                new_name = teclado.next();
                System.out.println("Cree una nueva contraseña para su cuenta: ");
                new_password = teclado.next();
                if (new_password.length() != 4){
                    System.out.println("La contraseña de la cuenta bancaria debe tener 4 digitos.");
                }else {
                    System.out.println("Transacción finalizada.");
                    datos = makeRegister(new_user, new_name, new_password, datos);
                }
                break;

            case 3:
                System.out.println("Programa finalizado.");
                bandera = false;
                break;
                
            default:
                System.out.println("No se reococe la entrada.");
        }
        guardarDatos(datos);
    }        
    }

	public static void menu() {
            System.out.println("\nBIENVENIDO A ATM BANK");
            System.out.println("1.Ingresar");
            System.out.println("2.Registrarse");
            System.out.println("3.Salir");

	}

	public static boolean validateUserId(String datos[][], String user_id) {            
            for (int fila = 0; fila < datos.length; fila++) {
                if(datos[fila][1] == ""){
                    if(datos[fila][1] == user_id){
                        return true;
                    }
                }else{
                    if(datos[fila][1].equals(user_id)){
                        return true;
                    }
                }
            }
            return false;    
        }

        public static int posicion(String datos[][], String user_id){
            int fila = 0;
            
            for (int i = 0; i < 5; i++) {
                if (datos[i][1].equals(user_id))
                    return i;
            }
            return -1;
        }

	public static boolean validatePassword(String datos[][], String password) {
		for (int fila = 0; fila < 5; fila++) {
                if (datos[fila][2].equals(password))
                    return true;
            }
            return false;
	}

	public static String[][] makeRegister(String new_user, String new_name, String new_password, String [][] datos ) {
            
            for (int fila = 0; fila < 5; fila++) {
                if ("null".equals(datos[fila][0])){
                    datos[fila][0] = new_name;
                    datos[fila][1] = new_user;
                    datos[fila][2] = new_password;
                    datos[fila][3] = "0";
                    System.out.println("Se añadio CORRECTAMENTE el usuario al registro del banco.");
                    
                    fila = 5;
                }
            }
        
	return datos;
	}

	public static boolean crearArchivo() {
            boolean bandera = false;
            try {
                    File file = new File("datos.txt");
                    if (!file.exists()) {
                            file.createNewFile();
                            bandera = true;
                    }
            } catch (IOException e) {
                    e.printStackTrace();
                    return bandera;
            }
            return bandera;

	}

	public static boolean guardarDatos(String[][] datos) {
            boolean bandera = true;

            FileWriter fichero = null;
            PrintWriter pw = null;
            try {
                fichero = new FileWriter("datos.txt");
                pw = new PrintWriter(fichero);
                String cadena = "";
                for (int fila = 0; fila < datos.length; fila++) {
                        cadena = datos[fila][0] + "," + datos[fila][1] + "," + datos[fila][2] + ","+ datos[fila][3] + "\n";
                        pw.print(cadena);
                }
                bandera = true;
            } catch (Exception e) {
                    e.printStackTrace();
            } finally {
                    try {
                        if (null != fichero)
                            fichero.close();
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
            }
            return bandera;
	}

	public static String[][] cargarDatos() {
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;
            String datos[][] = new String[5][4];

            try {
                archivo = new File("datos.txt");
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                String linea = "";
                String separador = Pattern.quote(",");

                int i = 0;
                while ((linea = br.readLine()) != null) {
                        String lineaDividida[] = linea.split(separador);
                        datos[i][0] = lineaDividida[0];
                        datos[i][1] = lineaDividida[1];
                        datos[i][2] = lineaDividida[2];
                        datos[i][3] = lineaDividida[3];
                        i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                        if (null != fr)
                                fr.close();
                } catch (Exception e2) {
                        e2.printStackTrace();
                }
            }
        return datos;
	}
        
    public static void menuCajero(){
        System.out.println("1. Consultar saldo.");
        System.out.println("2. Retirar dinero.");
        System.out.println("3. Consignar dinero.");
    }

    public static int[] llenarCajero(int [] v) {
        Random aleatorio = new Random();
        for (int i = 0; i < v.length; i++) {
            int a = aleatorio.nextInt(10)*10;
            if (a == 0){
                a = aleatorio.nextInt(10)*10;
            }
            v[i] = a;
        }
        return v;
    }

    public static boolean retirarDinero(int[] billetesCajero, int cantidadRetirar, String[][] datos, int posicion) {
        int saldoActual = Integer.parseInt (datos[posicion][3]);
        int [] dineroRetirado= new int[4];
        if (saldoActual < cantidadRetirar){
            return false;
        }else{
            while (cantidadRetirar > 0){
                if(cantidadRetirar >= 100){
                    if(billetesCajero[3] > 0){
                        dineroRetirado[3] += 1;
                        billetesCajero[3] -=1;
                        cantidadRetirar -= 100;
                    }else{
                        System.out.println("NO HAY BILLETES DE $100.");
                        dineroRetirado[2] += 2;
                        billetesCajero[2] -=2;
                        cantidadRetirar -= 100;
                    }
                }else{
                    if (cantidadRetirar >= 50){
                        
                        if(billetesCajero[2] > 0){
                            dineroRetirado[2] += 1;
                            billetesCajero[2] -=1;
                            cantidadRetirar -= 50;
                        }else {
                            dineroRetirado[1] += 2;
                            dineroRetirado[0] += 1;
                            billetesCajero[1] -= 2;
                            billetesCajero[0] -= 1;
                            cantidadRetirar -= 50;
                        }
                        
                    }else{
                        if (cantidadRetirar >= 20){
                            if(billetesCajero[1] > 0){
                                dineroRetirado[1] += 1;
                                billetesCajero[1] -=1;
                                cantidadRetirar -= 20;
                            }else{
                                dineroRetirado[0] +=2;
                                billetesCajero[0] -=2;
                                cantidadRetirar-=20;
                            }
                        }else{
                            if(billetesCajero[0] > 0){
                                dineroRetirado[0] += 1;
                                billetesCajero[0] -=1;
                                cantidadRetirar -= 100;
                            } else{
                                System.out.println("No hay billetes de $10, retire cantidad en multiplo de $20");
                            }
                        }
                    }
                }
            }
        }
        System.out.println("=======================================================");
        System.out.print("Este es el vector de billetes retirados: ");
        for (int i = 0; i < dineroRetirado.length; i++) {
            System.out.print(dineroRetirado[i]+"|");
        }
        System.out.println("");
        System.out.println("=======================================================");
        return true;
    }
}
