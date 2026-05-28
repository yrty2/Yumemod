package math;
public class analysis{
    public static <V extends algebra.NormedVectorSpace<V,Double>> V normalize(V a){
        if(a.norm()==0){
            return a.zero();
        }
        return a.sdiv(Math.sqrt(a.norm()));
    }
}