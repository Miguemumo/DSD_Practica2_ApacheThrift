package dsd;

import java.util.ArrayList;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class JavaClient {
  public static void main(String [] args) {
      ArrayList<Integer>  num = new ArrayList<>();
      ArrayList<String>  op = new ArrayList<>();

      if (args.length > 2 && args.length%2 != 0){
        try {
        TTransport transport;

        transport = new TSocket("localhost", 9090);
        transport.open();

        TProtocol protocol = new  TBinaryProtocol(transport);
        Calculadora.Client client = new Calculadora.Client(protocol);
        
        for (int i=0; i<args.length; i++){
            if (i%2 == 0)
                num.add(Integer.parseInt(args[i]));
            else
                op.add(args[i]);
        }

        perform(client, op, num);

        transport.close();
        } catch (TException x) {
        x.printStackTrace(); }
      }
    }

  private static void perform(Calculadora.Client client, ArrayList<String> operadores, ArrayList<Integer> numeros) throws TException
  {
    int product = 0;
    int entero1, entero2;
    String operador_actual;
    
    entero1 = numeros.get(0);
    
    System.out.print(entero1 + " ");
    
    for (int i = 0; i<numeros.size()-1; i++){
        entero2 = numeros.get(i+1);
        operador_actual = operadores.get(i);
    
        if (operadores.get(i).equals("+")){
           entero1 = client.suma(entero1,entero2);
        } else if (operadores.get(i).equals("-")){
            entero1 = client.resta(entero1,entero2);
        } else if (operadores.get(i).equals("x")){
            entero1 = client.multiplicacion(entero1,entero2);
        } else if (operadores.get(i).equals("/")){
            entero1 = client.division(entero1,entero2);
        } else {
            entero1 = client.potencia(entero1,entero2);
        }
        
        System.out.print(operador_actual + " " + entero2 + " ");
        
        
    }
    
    System.out.println("= " + entero1);

  }
}
