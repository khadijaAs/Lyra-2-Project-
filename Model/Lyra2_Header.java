
package Model;


import java.math.BigInteger;

public abstract class Lyra2_Header {
     //tCost = 8; nRows = 8192; nCols = 128; nThreads = 1; nRoundsSponge = 1; bSponge = 12; sponge = 0;
   protected int nRows;
   protected int nCols;
   protected unit64[][] M;/*matrice concernee */
   protected int BLOCK_LEN_BLAKE2_SAFE_INT64=16;   
   protected static int BLOCK_LEN_INT64=12;       /*block de 12 unit64*/
   protected static int BLOCKS_LEN_BYTES =  96;   /*Block de 96 octets => 12 unit 64*/
   protected static int ROW_LEN_BYTES= 64* BLOCKS_LEN_BYTES;  
   abstract public String LYRA2(String password,String salt,int t_cost,int R,int C,int outlen,int niveau);
}
