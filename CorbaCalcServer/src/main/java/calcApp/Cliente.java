/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcApp;

import java.util.Properties;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;

/**
 *
 * @author Claidson
 */
public class Cliente {

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("org.omg.CORBA.ORBInitialHost", "10.151.34.132");
        prop.put("org.omg.CORBA.ORBInitialPort", "1050");

        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, prop);

        org.omg.CORBA.Object objRef = null;
        org.omg.CosNaming.NamingContext naming = null;

        try {
            objRef = orb.resolve_initial_references("NamingService");
            naming = org.omg.CosNaming.NamingContextHelper.narrow(objRef);

        } catch (org.omg.CORBA.ORBPackage.InvalidName nome) {
            System.out.println("Pau ao buscar o service orb");
            System.exit(0);
        }

//        org.omg.CosNaming.NameComponent[] path = new org.omg.CosNaming.NameComponent[1];
//        path[0] = new org.omg.CosNaming.NameComponent();
//        path[0].id = "calculadora";
//        path[0].kind = "calc";
        org.omg.CosNaming.NamingContextExt ncRef = org.omg.CosNaming.NamingContextExtHelper.narrow(objRef);
        // resolve the Object Reference in Naming
        //String name = "calculadora";
        //  Hello helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));

        String tex = "calc";

        try {
            org.omg.CosNaming.NameComponent[] path = ncRef.to_name(tex);
            objRef = naming.resolve(path);
        } catch (org.omg.CosNaming.NamingContextPackage.NotFound e) {
            System.out.println("Naming Sevice não licalizado");
            System.exit(0);
        } catch (CannotProceed ex) {
            System.out.println("cliente parou");
            System.exit(0);
        } catch (InvalidName ex) {
            System.out.println("nome inválido [cliente]");
            System.exit(0);
        }
        calc calc = calcHelper.narrow(objRef);
        try {
            System.out.println("1+1 :" + calc.soma(1, 2));
            System.out.println("1-1 :" + calc.sub(1, 2));
            System.out.println("1*1 :" + calc.multi(1, 2));
            System.out.println("1/1 :" + calc.div(1, 2));
        } catch (org.omg.CORBA.SystemException e) {
            System.out.println("Erro ao calcular no cliente CORBA " + e.getMessage());

        }

    }
}
