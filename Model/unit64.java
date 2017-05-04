package Model;
/*
Conception de la classe unit64 qui prend un seule attribut de type BigInteger
*****************************************************************************
Cette classe a pour objectif de contrôler les opérations sur les variables de type Big integer tel que:

- La rotation( qui doit être effectué exactement sur 64 bits)
- Le modulo
- le XOR .....

*/
import java.math.BigInteger;


public class unit64 {
    BigInteger value;
    /*
    Constructeur qui prend en paramètre un BigInteger
    */
    public unit64(BigInteger b)
  {
    this.value=b;
  }
    /*Transforme un string en un unit64 (BigInteger)*/
    public static unit64 toBigInteger(String s)
        {         
            BigInteger bigInt = new BigInteger(s, 16);
            unit64 resultat=new unit64(bigInt);
            return resultat;            
        }
    /*
    Transforme un tableau de unit64 en un String
    chaque unit64(BigInteger) est écrit sous format hexadécimal+concaténation ==> String
    */
    static public String Unit64toHex(unit64[] tab)
    {
        String s=new String();
        for(int i=0;i<tab.length;i++)
        {
            s+=String.format("%x",tab[i].value);
        }
        return s;
    }
   /*
    
    Opération de xor de deux instances de la classe unit64
   */
  public unit64 xor(unit64 a){
    BigInteger res=this.value.xor(a.value);
    String s=""+Math.max(res.longValue(),-res.longValue());
    BigInteger ress=new BigInteger(s);
    unit64 resultat=new unit64(ress);
    return resultat;    
  }
  /*
  effectue une rotation sur 64 bits
  */
  public unit64 rotateLeft(int c) {
    BigInteger ret=this.value;
    while(c-->0)
    {
        ret = ret.shiftLeft(1);
        if (ret.testBit(64))
        {
            ret = ret.clearBit(64).setBit(0);
        }
    }
      unit64 resultat=new unit64(ret);
      return resultat;
  }
  /*
  Sommation de deux instances de la classe unit64
  */
  public unit64 add(unit64 a){
    BigInteger res=this.value.add(a.value);
    String s=""+Math.max(res.longValue(),-res.longValue());
    BigInteger ress=new BigInteger(s);
    unit64 resultat=new unit64(ress);
    return resultat;    
  }
  
  /*
  Opération de modulo de deux instances de la classe unit64
  */
  public int mod(int b){
        String s="";
        s+=b;
        BigInteger a=new BigInteger(s);
        BigInteger res=this.value.mod(a);
        return res.intValue();
  }
}

