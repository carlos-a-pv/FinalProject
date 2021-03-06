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

public class Cajero {
    public static void main(String[] args) {
        //Ventana v1 = new Ventana();
        //v1.setVisible(true);
        
        crearArchivo();

        String[][] datos = new String[10][5];
        int [] billetesCajero = new int[4];
        billetesCajero = llenarCajero(billetesCajero);
        datos = cargarDatos();
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                if (datos[i][j] == ""){
                    datos[i][j] = "null";
                }
            }
        }
        guardarDatos(datos);
                
        Scanner teclado = new Scanner(System.in);
        String user_id, password, new_user, new_password, new_name;
        boolean bandera = true;
        
        while(bandera){
            System.out.println("=======================================================");
            System.out.println("DINERO DEL CAJERO.");
            for (int i = 0; i < billetesCajero.length; i++) {
                System.out.print(billetesCajero[i]+"|");
            }
            System.out.println("\n=======================================================");
            System.out.println("");
            
        menu();
        System.out.println("\nIngrese una opción: ");
        int opcion = teclado.nextInt();
        int fila = 0;
        switch (opcion) {
            case 1:
                System.out.println("INICIAR SESIÓN:");
                System.out.println("Ingrese el id del usuario: ");
                user_id = teclado.next();

                if (validateUserId(datos, user_id) == false) {
                System.out.println("\n=======================================================");
                System.out.println("El usuario no tiene cuenta bancaria.");
                System.out.println("Transacción finalizada. Vuelva pronto...");
                System.out.println("=======================================================\n");
                 
                } else {
                    System.out.println("Ingrese la contraseña: ");
                    password = teclado.next();
                    fila = posicion(datos, user_id);
                    if (validatePassword(datos, password, fila) == false) {
                        System.out.println("Contraseña invalida.");
                    } else {
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
                                    System.out.println("Con un limite transaccional de: $"+datos[fila][4]);
                                    System.out.println("Transacción finalizada. Vuelva pronto...");
                                    System.out.println("=======================================================");
                                    break;
                                case 2: 
                                    System.out.println("RETIRO DE DINERO");
                                    System.out.println("Ingrese la cantidad que desea retirar: $");
                                    int cantidadRetirar = teclado.nextInt();
                                    if (retirarDinero(billetesCajero, cantidadRetirar, datos, fila) == true){
                                        int saldoActual = Integer.parseInt (datos[fila][3]);
                                        saldoActual -= cantidadRetirar;
                                        String saldoFinal = String.valueOf(saldoActual);
                                        datos[fila][3] = saldoFinal;
                                        int LimiteTransaccional = Integer.parseInt (datos[fila][4]);
                                        LimiteTransaccional -= cantidadRetirar;
                                        String limiteT = String.valueOf(LimiteTransaccional);
                                        datos[fila][4] = limiteT;
                                        System.out.println("");
                                        System.out.println("=======================================================");
                                        System.out.println("El valor del retiro fue de: $"+cantidadRetirar);
                                        System.out.println("El saldo actual de la cuenta es de: $"+datos[fila][3]);
                                        System.out.println("Transacción finalizada. Vuelva pronto...");
                                        System.out.println("=======================================================");
                                    }
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
                    System.out.println("=======================================================");
                    System.out.println("La contraseña de la cuenta bancaria debe tener 4 digitos.");
                    System.out.println("Transacción finalizada. Vuelva pronto...");
                    System.out.println("=======================================================");
                }else {
                    datos = makeRegister(new_user, new_name, new_password, datos);   
                }
                break;
            case 3:
                System.out.println("CANCELAR LA CUENTA.");
                System.out.println("Ingrese el ID de la cuenta: ");
                String idCuenta = teclado.next();
                if (validateUserId(datos, idCuenta) == false){
                    System.out.println("");
                    System.out.println("=======================================================");
                    System.out.println("La cuenta que se desea eliminar no existe.");
                    System.out.println("Transacción finalizada. Vuelva pronto...");
                    System.out.println("=======================================================");
                    System.out.println("");
                }else{
                    System.out.println("Ingrese la contraseña de la cuenta: ");
                    String contraseña = teclado.next();
                    if (validatePassword(datos, contraseña, fila) == false){
                        System.out.println("Contraseña invalida.");
                    }else{
                        System.out.println("DESEMBOLSO."); 
                        String desembolso = desembolsarDinero(datos, fila);
                        
                        System.out.println("\n=======================================================");
                        System.out.println("El desembolso fue de: $"+desembolso);
                        System.out.print("Se eliminó la cuenta correctamente.");
                        System.out.println("Transacción finalizada. Vuelva pronto...");
                        System.out.println("=======================================================\n");
                        datos = eliminarCuenta(datos, fila);
                    }
                }
                break;
            case 4:
                System.out.println("Programa finalizado.");
                bandera = false;
                break;
                
            default:
                System.out.println("No se reococe la entrada.");
        }
        guardarDatos(datos);
    }        
}
    /** 
    * funcion para mostrar el menu principal
    */
    public static void menu() {
        System.out.println("\nBIENVENIDO A ATM BANK");
        System.out.println("1.Ingresar");
        System.out.println("2.Registrarse");
        System.out.println("3.Cancelar cuenta");
        System.out.println("4.Salir");

    }
    /**
    * esta funcion valida usuario 
    * @param datos
    * @param user_id
    * @return boolean
    */
    public static boolean validateUserId(String datos[][], String user_id) {            
        for (int fila = 0; fila < datos.length; fila++) {
            if(datos[fila][1].equals(user_id)){
                return true;
            }
        }
        return false;    
    }
    /**
    * extrae la posicion en donde se encuentran los datos
    * @param datos
    * @param user_id
    * @return int
    */
    public static int posicion(String datos[][], String user_id){
        int fila = 0;

        for (int i = 0; i < 5; i++) {
            if (datos[i][1].equals(user_id))
                return i;
        }
        return -1;
    }
    /**
    * recibe la contraseña y valida que sea la misma del user_id
    * @param password
    * @param datos
    * @return boolean
    */
    public static boolean validatePassword(String datos[][], String password, int fila) {   
        if (datos[fila][2].equals(password)){
            return true;
        }
        return false;
    }
    /**
    * recibe datos y los almacena en la matriz
    * @param new_user
    * @param new_name
    * @param new_password
    * @param datos
    * @return datos
    */
    public static String[][] makeRegister(String new_user, String new_name, String new_password, String [][] datos ) {
        Random aleatorio = new Random();
        int limiteT = aleatorio.nextInt(10)*1000000;

        for (int fila = 0; fila < 5; fila++) {
            if ((datos[fila][0]).equals("null")){
                datos[fila][0] = new_name;
                datos[fila][1] = new_user;
                datos[fila][2] = new_password;
                datos[fila][3] = "0";
                String limiteTransacional = String.valueOf(limiteT);
                datos[fila][4] = limiteTransacional;
                System.out.println("=======================================================");
                System.out.println("Se añadio CORRECTAMENTE el usuario al registro del banco.");
                System.out.println("Transacción finalizada. Vuelva pronto...");
                System.out.println("=======================================================");
                fila = 5;
            }
        }
    return datos;
    }
    /** 
    * crea el documento de texto para almacenar los datos
    * @return bandera
    */
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
    /**
    * guarda los datos de la matriz al archivo de texto
    * @param datos
    * @return bandera
    */
    public static boolean guardarDatos(String[][] datos) {
        boolean bandera = true;

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("datos.txt");
            pw = new PrintWriter(fichero);
            String cadena = "";
            for (int fila = 0; fila < datos.length; fila++) {
                cadena = datos[fila][0] + "," + datos[fila][1] + "," + datos[fila][2] + ","+ datos[fila][3] + "," + datos[fila][4] + "\n";
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
    /**
    * para cargar los datos que estan en el archivo de texto a la matriz
    * @return datos
    */
    public static String[][] cargarDatos() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String datos[][] = new String[10][5];

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
                datos[i][4] = lineaDividida[4];
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
    /**
    * muestra el menu del cajero   
    */   
    public static void menuCajero(){
        System.out.println("1. Consultar saldo.");
        System.out.println("2. Retirar dinero.");
        System.out.println("3. Consignar dinero.");
    }
    /**
    * llena el cajero aleatoriamente con una cantidad de billetes
    * @param v
    * @return v
    */
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
    /**
    * calcular la cantidad de billetes a dar de acuerdo a la cantidad que se va a retirar
    * @param billetesCajero
    * @param cantidadRetirar
    * @param datos
    * @param posicion
    * @return boolean
    */
    public static boolean retirarDinero(int[] billetesCajero, int cantidadRetirar, String[][] datos, int posicion) {
        int saldoActual = Integer.parseInt (datos[posicion][3]);
        int limiteTransaccional = Integer.parseInt (datos[posicion][4]);
        int dineroTotalCajero = 0;
        dineroTotalCajero += billetesCajero[0]*10000;
        dineroTotalCajero += billetesCajero[1]*20000;
        dineroTotalCajero += billetesCajero[2]*50000;
        dineroTotalCajero += billetesCajero[3]*100000;
        int [] dineroRetirado= new int[4];

        if (saldoActual < cantidadRetirar){
            System.out.println("=======================================================");
            System.out.println("No tiene saldo suficiente");
            System.out.println("Transacción finalizada. Vuelva pronto...");                                        
            System.out.println("=======================================================");
            return false;
        }else{
            if(limiteTransaccional < 0){ 
                System.out.println("=======================================================");
                System.out.println("Superó el limite transaccional");
                System.out.println("Transacción finalizada. Vuelva pronto...");                                        
                System.out.println("=======================================================");
                return false;
            }else{
                 if(billetesCajero[0] == 0 && billetesCajero[1] == 0 && billetesCajero[2] == 0 && billetesCajero[3] == 0){
                    System.out.println("=======================================================");
                    System.out.println("EL CAJERO SE QUEDÓ SIN BILLETES.");
                    System.out.println("=======================================================");
                    return false;
                }else{
                    if(cantidadRetirar > dineroTotalCajero){
                    System.out.println("=======================================================");
                    System.out.println("El cajero no tiene ese monto de dinero.");
                    System.out.println("Transacción finalizada. Vuelva pronto...");                                        
                    System.out.println("=======================================================");
                    return false;
                    }else{
                        if(validarCantidadCinco(cantidadRetirar) == true){
                            System.out.println("=======================================================");
                            System.out.println("El cajero no tiene billetes de $5 mil.");
                            System.out.println("Transacción finalizada. Vuelva pronto...");                                        
                            System.out.println("=======================================================");
                            return false;
                        }else{
                            if(billetesCajero[0] == 0 && cantidadRetirar % 10 == 0){
                                System.out.println("No hay billetes de $10, retire cantidad en multiplo de $20");
                                return false;
                            }else{
                                while (cantidadRetirar > 0){
                                    if(cantidadRetirar > 100000){
                                        if (billetesCajero[3] > 0){
                                            dineroRetirado[3] += 1;
                                            billetesCajero[3] -=1;
                                            cantidadRetirar -= 100000;
                                            }else{
                                                if (billetesCajero[2] > 1){
                                                    dineroRetirado[2] += 2;
                                                    billetesCajero[2] -=2;
                                                    cantidadRetirar -= 100000;
                                                }else{
                                                    if (billetesCajero[1] > 0 && billetesCajero[0] > 0){
                                                        dineroRetirado[1] += 2;
                                                        dineroRetirado[0] += 1;
                                                        billetesCajero[1] -= 2;
                                                        billetesCajero[0] -= 1;
                                                        cantidadRetirar -= 50000;
                                                    }else{
                                                        System.out.println("NO SE PUDO HACER LA OPERACION");
                                                        return false;
                                                    }
                                                }
                                            }
                                    }else{
                                        if(cantidadRetirar >= 50000){
                                            if(billetesCajero[2] > 0){
                                                dineroRetirado[2] += 1;
                                                billetesCajero[2] -=1;
                                                cantidadRetirar -= 50000;
                                            }else{
                                                if (billetesCajero[1] > 1 && billetesCajero[0] > 0){
                                                        dineroRetirado[1] += 2;
                                                        dineroRetirado[0] += 1;
                                                        billetesCajero[1] -= 2;
                                                        billetesCajero[0] -= 1;
                                                        cantidadRetirar -= 50000;
                                                    }
                                                else{
                                                    System.out.println("NO SE PUDO HACER LA OPERACION");
                                                    return false;
                                                }
                                            }
                                        }else{
                                            if (cantidadRetirar >= 20000){
                                                if(billetesCajero[1] > 0){
                                                    dineroRetirado[1] += 1;
                                                    billetesCajero[1] -=1;
                                                    cantidadRetirar -= 20000;
                                                }else{
                                                    if(billetesCajero[0]>1){
                                                        dineroRetirado[0] +=2;
                                                        billetesCajero[0] -=2;
                                                        cantidadRetirar-=20000;
                                                    }  
                                                }
                                            }else{
                                                if(cantidadRetirar >= 10000){
                                                    if(billetesCajero[0] > 0){
                                                        dineroRetirado[0] += 1;
                                                        billetesCajero[0] -=1;
                                                        cantidadRetirar -= 10000;
                                                    }
                                                }else{
                                                    System.out.println("=======================================================");
                                                    System.out.println("NO SE PUEDE RETIRAR MONTOS MENORES A $10 MIL.");
                                                    System.out.println("Transacción finalizada. Vuelva pronto..."); 
                                                    System.out.println("=======================================================");
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                }
                            
                            }
                        }
                    }
                }
            }
        }
        if (billetesCajero[3] < billetesCajero[2]){
            if(billetesCajero[2]>2 && cantidadRetirar > 100000){
                
                while(dineroRetirado[3] > dineroRetirado[2]){
                    dineroRetirado[2] +=2;
                    dineroRetirado[3] -=1;

                    billetesCajero[2] -= 2;
                    billetesCajero[3] += 1;
                }
        }
        }else{
            if(cantidadRetirar > 100000){
                while(dineroRetirado[3] < dineroRetirado[2]){
                    if(billetesCajero[3]>1){
                        dineroRetirado[3] +=1;
                        dineroRetirado[2] -=2;

                        billetesCajero[3] -=1;
                        billetesCajero[2] +=2;
                    }
                }
            
            }
        }
        
        if (billetesCajero[2] < billetesCajero[1] ){
            if(billetesCajero[1]> 1 && billetesCajero[0]>0){
                while(dineroRetirado[1] > dineroRetirado[2]){
                    dineroRetirado[1] +=2;
                    dineroRetirado[0] +=1;
                    dineroRetirado[2] -=1;

                    billetesCajero[1] -=2;
                    billetesCajero[0] -=1;
                    billetesCajero[2] +=1;
                }
            }
        }else{
            while(dineroRetirado[2] > dineroRetirado[1]){
                if (billetesCajero[2]>0){
                    dineroRetirado[2] +=1;
                    dineroRetirado[1] -= 2;
                    dineroRetirado[0] -= 1;
                    
                    billetesCajero[2] -=1;
                    billetesCajero[1] += 2;
                    billetesCajero[0] += 1;
                }
            }
        }
        if (billetesCajero[1] < billetesCajero[0] ){
            if (billetesCajero[0]>0){
                while(dineroRetirado[1] > dineroRetirado[0]){
                    dineroRetirado[0] +=2;
                    dineroRetirado[1] -=1;

                    billetesCajero[0] -=2;
                    billetesCajero[1] +=1;
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

    public static String[][] eliminarCuenta(String[][] datos, int fila) {
        datos[fila][0] = "null";
        datos[fila][1] = "null";
        datos[fila][2] = "null";
        datos[fila][3] = "null";
        return datos;
    }

    public static String desembolsarDinero(String[][] datos, int fila) {
        String desembolso;
        desembolso = datos[fila][3];
        return desembolso;
    }
    
    public static boolean validarCantidadCinco(int n){
        String cadena = String.valueOf(n);
        
        if (cadena.length() == 5){
            if (cadena.charAt(1) == '5'){
                return true;
            }
        }else if (cadena.length() == 6){
            if (cadena.charAt(2) == '5'){
                return true;
            }
        }
        
        return false;
    }
}