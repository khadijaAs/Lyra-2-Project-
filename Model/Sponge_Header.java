package Model;
abstract public class Sponge_Header{
    static int N_COLS=128;
    static int nPARALLEL=2;
    static int BLOCKS_LEN_BYTES =  96;/*Byte.SIZE */
    static int ROW_LEN_BYTES= 64* BLOCKS_LEN_BYTES;
    static int BLOCK_LEN_INT64=12;
    static int BLOCK_LEN_BLAKE2_SAFE_INT64=8;
    static int BLOCK_LEN_BLAKE2_SAFE_BYTES=BLOCK_LEN_BLAKE2_SAFE_INT64*8;
    static int rho=1;
    unit64[] state=new unit64[16];
    unit64[] in=new unit64[8];
    unit64[] outt;
    static unit64 t0,t1,t2;
    public  int SPONGE=0;
    abstract void squeeze(int len);
    abstract void absorb();
    abstract void absorbColumn(int i,unit64[][] M);
    abstract void reducedSpongeLyra(unit64[] v);
    abstract void reducedSqueezeRow(unit64 M[][],int  row);
    abstract void Duplexfirst(unit64 M[][],int In,int Out);
    abstract void DuplexFilling(unit64 M[][],int InOut,int In0,int In1,int Out);
    abstract void DuplexWandering(unit64 M[][],int InOut0,int InOut1,int In0,int In1);
    }
