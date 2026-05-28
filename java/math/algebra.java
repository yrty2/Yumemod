package math;
public class algebra{
    public interface Magma<S>{
        S ope(S a);
    }
    public interface Monoid<S> extends Magma<S>{
        S identity();
    }
    public interface Group<S> extends Monoid<S>{
        S inverse();
    }
    public interface AbelianGroup<S>{
        boolean equal(S a);
        S sum(S a);
        S sub(S a);
        S neg();
        S zero();
    }
    public interface Ring<S>{
        boolean equal(S a);
        S sum(S a);
        S sub(S a);
        S mul(S a);
        S neg();
    }
    public interface Module<S,R> extends AbelianGroup<S>{
        S smul(R a);
        R one();
    }
    public interface VectorSpace<S,R> extends Module<S,R>{
        S sdiv(R a);
    }
    public interface NormedVectorSpace<S,R> extends VectorSpace<S,R>{
        R norm();
    }
    public interface MetricVectorSpace<S,R> extends NormedVectorSpace<S,R>{
        R dot(S a);
    }
    public interface Algebra<S,R> extends NormedVectorSpace<S,R>{
        S mul(S a);
        S div(S a);
    }
    public static class complex implements Algebra<complex,Double>{
        public final double real;
        public final double imag;
        public complex(double Re,double Im){
            real=Re;
            imag=Im;
        }
        public complex conj(){
            return new complex(real,-imag);
        }
        public double abs(){
            return Math.hypot(real, imag);
        }
        @Override
        public Double norm(){
            return real*real+imag*imag;
        }
        public double arg(){
            return Math.atan2(imag,real);
        }
        @Override
        public complex sum(complex a){
            return new complex(real+a.real,imag+a.imag);
        }
        @Override
        public complex sub(complex a){
            return new complex(real-a.real,imag-a.imag);
        }
        @Override
        public complex mul(complex a){
            return new complex(real*a.real-imag*a.imag,real*a.imag+imag*a.real);
        }
        @Override
        public complex div(complex a){
            return (mul(a.conj())).sdiv(a.norm());
        }
        public complex sum(double a){
            return new complex(real+a,imag);
        }
        public complex sumi(double a){
            return new complex(real,imag+a);
        }
        public complex sub(double a){
            return new complex(real-a,imag);
        }
        @Override
        public complex smul(Double a){
            return new complex(real*a,imag*a);
        }
        @Override
        public complex sdiv(Double a){
            return new complex(real/a,imag/a);
        }
        @Override
        public complex neg(){
            return new complex(-real,-imag);
        }
        @Override
        public boolean equal(complex a){
            return real==a.real && imag==a.imag;
        }
        @Override
        public complex zero(){
            return new complex(0,0);
        }
        @Override
        public Double one(){
            return 1.0;
        }
        public vec2 vec2(){
            return new vec2(real,imag);
        }
    }
    public static class vec2 implements MetricVectorSpace<vec2,Double>{
        public double x;
        public double y;
        public int dim=2;
        public vec2(double X,double Y){
            x=X;
            y=Y;
        }
        @Override
        public Double dot(vec2 a){
            return x*a.x+y*a.y;
        }
        @Override
        public Double norm(){
            return x*x+y*y;
        }
        public double length(){
            return Math.hypot(x,y);
        }
        public double arg(){
            return Math.atan2(y,x);
        }
        @Override
        public vec2 smul(Double a){
            return new vec2(x*a,y*a);
        }
        @Override
        public vec2 sdiv(Double a){
            return new vec2(x/a,y/a);
        }
        @Override
        public vec2 sum(vec2 a){
            return new vec2(x+a.x,y+a.y);
        }
        @Override
        public vec2 sub(vec2 a){
            return new vec2(x-a.x,y-a.y);
        }
        @Override
        public vec2 neg(){
            return new vec2(-x,-y);
        }
        @Override
        public boolean equal(vec2 a){
            return x==a.x && y==a.y;
        }
        @Override
        public vec2 zero(){
            return new vec2(0,0);
        }
        @Override
        public Double one(){
            return 1.0;
        }
        public complex complex(){
            return new complex(x,y);
        }
    }
    public static class vec3 implements MetricVectorSpace<vec3,Double>{
        public double x;
        public double y;
        public double z;
        public int dim=3;
        public vec3(double X,double Y,double Z){
            x=X;
            y=Y;
            z=Z;
        }
        @Override
        public Double dot(vec3 a){
            return x*a.x+y*a.y+z*a.z;
        }
        @Override
        public Double norm(){
            return x*x+y*y+z*z;
        }
        public double length(){
            return Math.sqrt(norm());
        }
        @Override
        public vec3 smul(Double a){
            return new vec3(x*a,y*a,z*a);
        }
        @Override
        public vec3 sdiv(Double a){
            return new vec3(x/a,y/a,z/a);
        }
        @Override
        public vec3 sum(vec3 a){
            return new vec3(x+a.x,y+a.y,z+a.z);
        }
        @Override
        public vec3 sub(vec3 a){
            return new vec3(x-a.x,y-a.y,z-a.z);
        }
        @Override
        public vec3 neg(){
            return new vec3(-x,-y,-z);
        }
        @Override
        public boolean equal(vec3 a){
            return x==a.x && y==a.y && z==a.z;
        }
        @Override
        public vec3 zero(){
            return new vec3(0,0,0);
        }
        @Override
        public Double one(){
            return 1.0;
        }
        public vec2 xy(){
            return new vec2(x,y);
        }
        public vec2 yz(){
            return new vec2(y,z);
        }
        public vec2 zx(){
            return new vec2(z,x);
        }
        public vec2 yx(){
            return new vec2(y,x);
        }
        public vec2 zy(){
            return new vec2(z,y);
        }
        public vec2 xz(){
            return new vec2(x,z);
        }
    }
    public static class vec4 implements MetricVectorSpace<vec4,Double>{
        public double x;
        public double y;
        public double z;
        public double w;
        public int dim=4;
        public vec4(double X,double Y,double Z,double W){
            x=X;
            y=Y;
            z=Z;
            w=W;
        }
        @Override
        public Double dot(vec4 a){
            return x*a.x+y*a.y+z*a.z+w*a.w;
        }
        @Override
        public Double norm(){
            return x*x+y*y+z*z;
        }
        public double length(){
            return Math.sqrt(norm());
        }
        @Override
        public vec4 smul(Double a){
            return new vec4(x*a,y*a,z*a,w*a);
        }
        @Override
        public vec4 sdiv(Double a){
            return new vec4(x/a,y/a,z/a,w/a);
        }
        @Override
        public vec4 sum(vec4 a){
            return new vec4(x+a.x,y+a.y,z+a.z,w+a.w);
        }
        @Override
        public vec4 sub(vec4 a){
            return new vec4(x-a.x,y-a.y,z-a.z,w-a.w);
        }
        @Override
        public vec4 neg(){
            return new vec4(-x,-y,-z,-w);
        }
        @Override
        public boolean equal(vec4 a){
            return x==a.x && y==a.y && z==a.z && w==a.w;
        }
        @Override
        public vec4 zero(){
            return new vec4(0,0,0,0);
        }
        @Override
        public Double one(){
            return 1.0;
        }
        public vec3 xyz(){
            return new vec3(x,y,z);
        }
        public vec3 yzw(){
            return new vec3(y,z,w);
        }
        public vec2 xy(){
            return new vec2(x,y);
        }
        public vec2 yz(){
            return new vec2(y,z);
        }
        public vec2 zw(){
            return new vec2(z,w);
        }
        public vec2 wx(){
            return new vec2(w,x);
        }
    }
}