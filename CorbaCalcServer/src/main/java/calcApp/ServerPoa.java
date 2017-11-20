/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcApp;


/**
 *
 * @author Claidson
 */
public class ServerPoa {

    public static void main(String[] args) {
        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
        org.omg.CORBA.Object objPoa = null;
        org.omg.PortableServer.POA rootPOA = null;
        try {
            objPoa = orb.resolve_initial_references("RootPOA");
            

        } catch (org.omg.CORBA.ORBPackage.InvalidName e) {
            System.out.println("Pau no nome server: " + e.getMessage());
        }
        rootPOA = org.omg.PortableServer.POAHelper.narrow(objPoa);
        CalcPoaImple calc = new CalcPoaImple();
        try {
            byte[] servamtId = rootPOA.activate_object(calc);
            org.omg.CORBA.Object ref = rootPOA.id_to_reference(servamtId);
            org.omg.CORBA.Object obj = null;
            org.omg.CosNaming.NamingContext naming = null;
            try {
                obj = orb.resolve_initial_references("NamingService");
                System.out.println("Localizando naming service...");
                naming = org.omg.CosNaming.NamingContextExtHelper.narrow(obj);
                System.out.println("Narrow... ");

            } catch (org.omg.CORBA.ORBPackage.InvalidName nome) {
                System.out.println("Naming service não localizado");
                System.exit(0);
            }
            org.omg.CosNaming.NameComponent[] nome = new org.omg.CosNaming.NameComponent[1];
            nome[0] = new org.omg.CosNaming.NameComponent();
            nome[0].id = "calculadora";
            nome[0].kind = "calc";
            try {
                naming.bind(nome, ref);
            } catch (org.omg.CosNaming.NamingContextPackage.NotFound e) {
                System.out.println("bjeto não encontrado");
                System.exit(0);
            } catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound e) {
                System.out.println("bjeto ja existe");
                System.exit(0);
            } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
                System.out.println("nome inválido");
                System.exit(0);
            } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed e) {
                System.out.println("erro... parei aqui");
                System.exit(0);
            }
            rootPOA.the_POAManager().activate();
            System.out.println("Servidor ativado...");
            orb.run();

        } catch (java.lang.Exception e) {
            System.out.println("Deu pau no service do servidor");
            e.printStackTrace();
        }
    }
}
