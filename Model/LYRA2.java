package Model;

import java.math.BigInteger;
 
public class LYRA2 extends Lyra2_Header{    

/*
   initialisation de la matrice qui sera traité et stocké en mémoire
   */
   public void initMatrice()
   {
       for(int i=0;i<nRows;i++)
       {
           for(int j=0;j<nCols*12;j++)
           {
               this.M[i][j]=new unit64(new BigInteger("0"));
           }
       }
   }
   
/*
  Initalisation de la matrice par L'input s
 */
public void RemplirMat(String s)
   {
       for(int i=0,j=0;i<s.length();i+=16,j++)
           this.M[j/nCols][j%nCols]=unit64.toBigInteger(s.substring(i, i+16));
   }

/*
Fonction de lyra2 
*/
public String LYRA2(String password,String salt,int t_cost,int R,int C,int outlen,int niveau)//LYRA2("password","salt",8,814,128,20)
{

this.nRows=R;
this.nCols=C;
this.M=new unit64[R][C*BLOCK_LEN_INT64];
/* Génération de l'input: concaténation des paramètres */
String params =toHex(password) + toHex(salt) +String.format("%08x",outlen) + String.format("%08x",password.length())+ String.format("%08x",salt.length())+ String.format("%08x",t_cost) + String.format("%08x",R) + String.format("%08x",C);
long nBlockInput= ((salt.length()*2+password.length()*2+6*Integer.SIZE*8)/BLOCK_LEN_BLAKE2_SAFE_INT64) + 1;
params+="80";
for(long i=params.length();i<(nBlockInput*BLOCK_LEN_BLAKE2_SAFE_INT64)-1;i++)
{
    params+="0";
}
params+="1";
/*
Déclaration de variable de type Sponge chargé d'exécuter les fonction qui y sont relatives
*/
Sponge H=new Sponge();

//affectation de niveau de sécurité choisi par l'utilisateur
H.SPONGE=niveau;
initMatrice();
RemplirMat(params);
//initialiser la matrice par le vecteur d'initialisation(contenant l'input généré)
H.init_8_mat(this.M);
/*initialisation de state par un vecteur appartenant aux constantes de cryptosystem*/
H.initState();
// Opération d'absorption
for (int i = 0; i < nBlockInput; i++) {
  H.absorb();
}
int  gap=1,stp=1,wnd= 2,sqrt = 2,prev0=2,row1=1,prev1=0,row0;
/*Réinitialiser la première ligne de la matrice*/
            H.reducedSqueezeRow(this.M,0);
/*Réinitialiser la deuxième ligne de la matrice à partir de la première*/
            H.Duplexfirst(this.M, 0, 1);
/*Réinitialiser la 3ième ligne de la matrice à partir de la deuxième*/
	    H.Duplexfirst(this.M, 1, 2);
//Initialisation des lignes restantes à partir de 3
            
for(row0=3;row0<=R-1;row0++)
{

     H.DuplexFilling(M,row1,prev0,prev1,row0);
	
	prev0=row0;
	prev1=row1;
	row1 =(row1 + stp) % wnd;
	
	if (row1==0){

		wnd = 2 * wnd;
		stp = sqrt + gap;
		gap =-gap;		
		
		if (gap ==-1)
			sqrt = 2 * sqrt;
        }
}
/*Réinitialiser des cases de la matrices choisies pseudo aléatoirement */
for(int wCount = 0;wCount<= (R * t_cost) - 1;wCount++)
        {

        row0 = H.state[0].mod(nRows);          
        row1 =H.state[2].mod(nRows);
        H.DuplexWandering(M,row0,row1,prev0,prev1);
	
	prev0 = row0;
	prev1 = row1;
    }

H.absorbColumn(row0,M);

H.squeeze(outlen);//Unit64toHex

String r=unit64.Unit64toHex(H.outt);
return r;
}

public static String toHex(String arg) 
    {      
      return String.format("%x", new BigInteger(1, arg.getBytes()));
    }

}


