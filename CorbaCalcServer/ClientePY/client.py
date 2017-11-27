import sys
from omniORB import CORBA
import calcApp, CosNaming


orb = CORBA.ORB_init()
obj = orb.string_to_object("corbaname:iiop:10.151.34.132:1050/NamingService#calc")
calc = obj._narrow(calcApp.calc)

if calc is None:
  print "nao localizado."
  sys.exit(1)


print "Soma 1 + 2 : {}".format(calc.soma(1,2)) 
print "Sub 2 - 1 : {}".format(calc.sub(2,1)) 
print "Multiplica 1 * 2 : {}".format(calc.multi(1,2)) 
print "Divide 8 / 2 : {}".format(calc.div(8,2)) 


