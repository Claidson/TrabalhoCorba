/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcApp;

import java.util.Properties;

/**
 * http://www.dca.fee.unicamp.br/cursos/PooJava/objdist/idlexemplo.html
 * https://docs.oracle.com/javase/8/docs/technotes/guides/idl/jidlExample.html
 * http://www.it.uc3m.es/mcfp/docencia/si/material/7b_invocacionEstatica_mcfp.pdf
 * https://pt.slideshare.net/Goncalvinho/artigo-distribuidos-programao-java-com-rmi-e-cobra
 * http://www.inf.ufsc.br/~bosco.sobral/old_page/downloads/comp_obj_dist.htm
 * http://www.batebyte.pr.gov.br/modules/conteudo/conteudo.php?conteudo=286
 * http://code.activestate.com/recipes/81254-implement-a-corba-client-and-server/
 * https://github.com/troeger/corba-example
 * http://legionti.blogspot.com.br/2010/11/como-utilizar-o-omniorb-no-ubuntu.html
 * http://www.inf.ufsc.br/~frank.siqueira/INE5418/CORBA-Banco/index.htm
 * https://projects.gnome.org/ORBit2/orbit-docs/orbit/x478.html
 * sudo orbd -ORBInitialPort 900 -ORBInitialHost 10.151.34.132
 * start tnameserv -ORBInitialPort 5000
 *
 * @author Claidson
 */
public class ServerPoa {

    public static void main(String[] args) {
//        System.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
//        System.setProperty("org.omg.CORBA.ORBInitialPort", "6000");
        Properties prop = new Properties();
       prop.put("org.omg.CORBA.ORBInitialHost", "192.168.1.4");
       // prop.put("org.omg.CORBA.ORBInitialHost", "localhost");
        prop.put("org.omg.CORBA.ORBInitialPort", "1050");

        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, prop);
      

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
                nome.printStackTrace();
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
