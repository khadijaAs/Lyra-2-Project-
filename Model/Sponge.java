package Model;

import java.math.BigInteger;
public class Sponge extends Sponge_Header{

  public void initState()
  {
      /*8 premiers cases du vecteur state sont initialises a 0*/
       for(int i=0;i<8;i++)
       {  this.state[i]=new unit64(new BigInteger("0"));}
       /***********************le reste de cases sont initialises a partir d un vecteur appartenant aux constantes du cryptosysteme ***************************/
       this.state[8]=unit64.toBigInteger("6a09e667f3bcc908");
       this.state[9]=unit64.toBigInteger("bb67ae8584caa73b");
       this.state[10]=unit64.toBigInteger("3c6ef372fe94f82b");
       this.state[11]=unit64.toBigInteger("a54ff53a5f1d36f1");
       this.state[12]=unit64.toBigInteger("510e527fade682d1");
       this.state[13]=unit64.toBigInteger("9b05688c2b3e6c1f");
       this.state[14]=unit64.toBigInteger("1f83d9abfb41bd6b");
       this.state[15]=unit64.toBigInteger("5be0cd19137e2179");
    }
  /*on declare un vecteur de taille 8 ,qui contient les valeurs de la matrice*/
  public void init_8_mat(unit64 M[][])
  {    
      for(int i=0;i<8;i++)
      {
         this.in[i]=new unit64(M[0][i].value);
      }
  }
public void absorb()
{ 
  for(int j=0;j<8;j++){       
    this.state[j]=this.state[j].xor(in[j]);}
    spongeLyra(this.state);
}
public void absorbColumn(int row0,unit64[][] M) {
    for (int i = 0; i < BLOCK_LEN_INT64; i++){
        this.state[i]=this.state[i].xor(M[row0][i]);}
    spongeLyra(this.state);
}
/*operation de sponge selon le niveau de sécurite souhaité par l'utilisateur**/
public void spongeLyra(unit64[] v) {
if (SPONGE == 0)/*guarded*/
    for (int i = 0; i < 12; i++){
        ROUND_LYRA(i,v);
    }
else if(SPONGE==1)/*high*/
    for (int i = 0; i < 12; i++){
        ROUND_LYRA_BLAMKA(i,v);
    }
else if(SPONGE==2)/*severe*/
    for (int i = 0; i < 12; i++){
        HALF_ROUND_LYRA_BLAMKA(i,v);
}
}
public static void ROUND_LYRA(int r,unit64[] v){  
    G(r,0,v,0,4,8,12);//v[ 0],v[ 4],v[ 8],v[12]); 
    G(r,1,v,1,5,9,13);//v[ 1],v[ 5],v[ 9],v[13]); 
    G(r,2,v,2,6,10,14);//v[ 2],v[ 6],v[10],v[14]); 
    G(r,3,v,3,7,11,15);//v[ 3],v[ 7],v[11],v[15]); 
    G(r,4,v,0,5,10,15);//v[ 0],v[ 5],v[10],v[15]); 
    G(r,5,v,1,6,11,12);//v[ 1],v[ 6],v[11],v[12]); 
    G(r,6,v,2,7,8,13);//v[ 2],v[ 7],v[ 8],v[13]); 
    G(r,7,v,3,4,9,14);//v[ 3],v[ 4],v[ 9],v[14]);
    }
public static  unit64 fBlaMka(unit64 x, unit64 y){
    long lessX = (long) x.value.longValue();
    long lessY = (long) y.value.longValue();   
    unit64 lessZ = new unit64(BigInteger.valueOf(lessX));
    lessZ.value=lessZ.value.multiply(BigInteger.valueOf(lessY));
    lessZ.rotateLeft(1);  
    unit64 z = lessZ.add(x.add(y));   
    return z;
}
public static void DIAGONALIZE(int r,unit64[] v){
    t0=v[4];v[4]=v[5]; v[5]=v[6]; v[6]=v[7]; v[7]=t0; 
    t0=v[8]; t1=v[9];v[8]=v[10]; v[9]=v[11]; v[10]=t0; v[11]=t1; 
    t0=v[12]; t1=v[13]; t2=v[14]; v[12]=v[15]; v[13]=t0; v[14]=t1; v[15]=t2;
}
public static void ROUND_LYRA_BLAMKA(int r,unit64[] v){
    GBLAMKA(r,0,0,4,8,12,v); 
    GBLAMKA(r,1,1,5,9,13,v);
    GBLAMKA(r,2,2,6,10,14,v);
    GBLAMKA(r,3,3,7,11,15,v);
    GBLAMKA(r,4,0,5,10,15,v);
    GBLAMKA(r,5,1,6,11,12,v);
    GBLAMKA(r,6,2,7,8,13,v);
    GBLAMKA(r,7,3,4,9,14,v);
}
public static void HALF_ROUND_LYRA_BLAMKA(int r,unit64[] v){
    GBLAMKA(r,0,0,4,8,12,v);
    GBLAMKA(r,1,1,5,9,13,v);
    GBLAMKA(r,2,2,6,10,14,v);
    GBLAMKA(r,3,3,7,11,15,v);
    DIAGONALIZE(r,v);
}
public static void GBLAMKA(int r,int i,int a,int b,int c,int d,unit64 v[])
 { 
    v[a]= fBlaMka(v[a],v[b]);
    v[d]=v[d].xor(v[a]).rotateLeft(32);
    v[c]= fBlaMka(v[c],v[d]);
   v[b]=v[b].xor(v[c]).rotateLeft(24);
    v[a]= fBlaMka(v[a],v[b]);
     v[d]=v[d].xor(v[a]).rotateLeft(16);
    v[c]= fBlaMka(v[c],v[d]);
     v[b]=v[b].xor(v[c]).rotateLeft(63);
}
public static void G(int r,int i,unit64 v[],int a,int b,int c,int d){//unit64 a,unit64 b,unit64 c,unit64 d){
      v[a]=v[a].add(v[b]);
      v[d]=v[d].xor(v[a]).rotateLeft(32);
      v[c]=v[c].add(v[d]);
      v[b]=v[b].xor(v[c]).rotateLeft(24);
      v[a]=v[a].add(v[b]);
      v[d]=v[d].xor(v[a]).rotateLeft(16);
      v[c]=v[c].add(v[d]);
      v[b]=v[b].xor(v[c]).rotateLeft(63);
    }
 public void squeeze(int len)
    {
        this.outt=new unit64[len];
        int fullBlocks=len/BLOCKS_LEN_BYTES; ///96 octets
        for(int i=0;i<fullBlocks;i++)
        {
            for(int j=0;j<BLOCK_LEN_INT64;j++){
                this.outt[j+i*BLOCK_LEN_INT64]=this.state[j];//uint 64 => 8 octets
            }
            spongeLyra(this.state);
        }

         //operation de Squeeze sur les octets restants
        for(int j=fullBlocks*12,i=0;j<this.outt.length;j++,i++)
        {
                this.outt[j]=this.state[j%BLOCK_LEN_INT64];
        }
        
    }
    public void reducedSqueezeRow(unit64 M[][],int  row) 
    {
        int j=0;
        for (int i = 0; i < N_COLS; i++) 
        {
            M[row][0+i*BLOCK_LEN_INT64] = state[0];
            M[row][1+i*BLOCK_LEN_INT64] = state[1];
            M[row][2+i*BLOCK_LEN_INT64] = state[2];
            M[row][3+i*BLOCK_LEN_INT64] = state[3];
            M[row][4+i*BLOCK_LEN_INT64] = state[4];
            M[row][5+i*BLOCK_LEN_INT64] = state[5];
            M[row][6+i*BLOCK_LEN_INT64] = state[6];
            M[row][7+i*BLOCK_LEN_INT64] = state[7];
            M[row][8+i*BLOCK_LEN_INT64] = state[8];
            M[row][9+i*BLOCK_LEN_INT64] = state[9];
            M[row][10+i*BLOCK_LEN_INT64] = state[10];
            M[row][11+i*BLOCK_LEN_INT64] = state[11];
            j++;
            reducedSpongeLyra(this.state);
        }
    }
    public void reducedSpongeLyra(unit64[] v)
    {
     if (SPONGE == 0)
         for (int i = 0; i < rho; i++){
            ROUND_LYRA(i,v);
    }
    else if(SPONGE==1)
    for (int i = 0; i < rho; i++){
        ROUND_LYRA_BLAMKA(i,v);
    }
    else if(SPONGE==2)
    for (int i = 0; i < rho; i++){
        HALF_ROUND_LYRA_BLAMKA(i,v);
    }
    }
    public void Duplexfirst(unit64 M[][],int In,int Out)
    {
        int i=0;
        int k=(N_COLS-1)*BLOCK_LEN_INT64;
        while (i < N_COLS*BLOCK_LEN_INT64) {
            for (int j=0; j < BLOCK_LEN_INT64; j++,i++){
                this.state[i%BLOCK_LEN_INT64] =this.state[i%BLOCK_LEN_INT64].xor(M[In][i]);
            }
            //Applies the reduced-round transformation f to the sponge's state
            reducedSpongeLyra(this.state);
            i-=BLOCK_LEN_INT64;
            //M[1][C-1-col] = M[0][col] XOR rand
            for (int j = 0; j < BLOCK_LEN_INT64; j++,i++,k++){
                M[Out][k] = M[In][i].xor(this.state[i%BLOCK_LEN_INT64]);
            }
            k-=2*BLOCK_LEN_INT64;
        }
    }
    public void DuplexFilling(unit64 M[][],int InOut,int In0,int In1,int Out)
    {

        int i=0;
        int k=(N_COLS-1)*BLOCK_LEN_INT64;
        while (i < N_COLS*BLOCK_LEN_INT64){
            //Absorbing "M[row1] [+] M[prev0] [+] M[prev1]"
            for (int j=0; j < BLOCK_LEN_INT64; j++,i++){
                this.state[i%BLOCK_LEN_INT64]  =this.state[i%BLOCK_LEN_INT64].xor (M[InOut][i].add(M[In0][i].add(M[In1][i])));
            }
            i-=BLOCK_LEN_INT64;
            //Applies the reduced-round transformation f to the sponge's state
            reducedSpongeLyra(this.state);
            //M[row0][col] = M[prev0][col] XOR rand
            for (int j = 0; j < BLOCK_LEN_INT64; j++,i++,k++){
                {M[Out][k] = M[In0][i].xor(this.state[i%BLOCK_LEN_INT64]);
                    }
            }
            i-=BLOCK_LEN_INT64;            
            for (int j = 0; j < BLOCK_LEN_INT64; j++,i++){
                M[InOut][i]  = M[InOut][i].xor(this.state[(j+2) % BLOCK_LEN_INT64]);
            }
            k-=2*BLOCK_LEN_INT64;
        }
}
    
public void DuplexWandering(unit64 M[][],int InOut0,int InOut1,int In0,int In1) {
    int i=0;
        while (i < N_COLS*BLOCK_LEN_INT64){
            int col0= this.state[4].mod(N_COLS)*BLOCK_LEN_INT64;
            int col1= this.state[6].mod(N_COLS)*BLOCK_LEN_INT64;       
            for(int j=0;j<BLOCK_LEN_INT64;j++,i++,col0++,col1++)
            {
                this.state[i%BLOCK_LEN_INT64]=state[i%BLOCK_LEN_INT64].xor(M[InOut0][i].add(M[InOut1][i].add(M[In0][col0%N_COLS].add(M[In1][col1%N_COLS]))));
            }
            i-=BLOCK_LEN_INT64;
            reducedSpongeLyra(this.state);
            for (int j = 0; j < BLOCK_LEN_INT64; j++,i++){
            M[InOut0][i] = this.state[i%BLOCK_LEN_INT64].xor(M[InOut0][i]);
            }
            i-=BLOCK_LEN_INT64;
            for (int j = 0; j < BLOCK_LEN_INT64; j++,i++){
            M[InOut1][i]  = this.state[(j+2) % BLOCK_LEN_INT64].xor(M[InOut1][i]);
          }
      }
}

public void DuplexWandering_P(unit64 M[][],int InOut0,int InP,int In0)
    {
        int i=0;
        while (i < N_COLS*BLOCK_LEN_INT64){
            int col1= this.state[6].mod(N_COLS)*BLOCK_LEN_INT64;
            for(int j=0;j<BLOCK_LEN_INT64;j++,i++,col1++)
            {
                this.state[i%BLOCK_LEN_INT64]=state[i%BLOCK_LEN_INT64].xor(M[InOut0][i].add(M[In0][col1%N_COLS].add(M[InP][i])));
            }
            i-=BLOCK_LEN_INT64;
            reducedSpongeLyra(this.state);
            for (int j = 0; j < BLOCK_LEN_INT64; j++,i++){
                M[InOut0][i] = this.state[i%BLOCK_LEN_INT64].xor(M[InOut0][i]);
            }
        }

    }
    
    public void squeeze_P(unit64[][] pkey,int indc,int len)
    {

        int fullBlocks=len/BLOCKS_LEN_BYTES;
        for(int i=0;i<fullBlocks;i++)
        {
            for(int j=0;j<BLOCK_LEN_INT64;j++){
                pkey[indc][j+i*BLOCK_LEN_INT64]=this.state[j];
            }
            spongeLyra(this.state);
        }
        /*operation de squeeze sur le reste de octets */
        for(int j=fullBlocks*12;j<len;j++)
        {
            pkey[indc][j]=this.state[j%BLOCK_LEN_INT64];
        }

    }

}
