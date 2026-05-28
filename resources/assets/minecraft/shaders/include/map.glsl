uniform int CurveMode;
uniform float CurveWeight;

vec2 CameraDirection(){
    mat3 invViewRot = transpose(mat3(ModelViewMat));
    vec3 look3D = -invViewRot[2];
    vec2 lookXZ = look3D.xz;
    if (length(lookXZ) > 0.0001) {
        return normalize(lookXZ);
    } else {
        return vec2(1.0, 0.0);
    }
}

vec2 cexp(vec2 p){
return exp(p.x)*vec2(cos(p.y),sin(p.y));
}

vec2 cln(vec2 p){
return vec2(log(length(p)),atan(p.y,p.x));
}

vec2 cmul(vec2 p,vec2 q){
return vec2(p.x*q.x-p.y*q.y,p.x*q.y+p.y*q.x);
}

vec2 cpow(vec2 p,float a){
return cexp(a*cln(p));
}

vec3 mobius3(vec3 p,vec3 q){
    float qq=dot(q,q);
    float pq=dot(p,q);
    float pp=dot(p,p);
    return (p*(1-2*pq-qq)+q*(1+pp))/(1-2*pq+qq*pp);
}

vec3 mobius3h(vec3 p,vec3 q){
    float qq=dot(q,q);
    float pq=dot(p,q);
    float pp=dot(p,p);
    return (p*(1+2*pq+qq)+q*(1-pp))/(1+2*pq+qq*pp);
}

vec2 conj(vec2 p){
return vec2(p.x,-p.y);
}

float selfdot(vec3 p){
return dot(p,p);
}

vec3 eulerpow(vec3 a,float b){
float r=length(a);
float Z=acos(a.y/r);
float siz=sin(Z*b);
vec2 p=a.xz/sin(Z);
float XY=atan(p.y,p.x);
return pow(r,b)*vec3(cos(b*XY)*siz,cos(b*Z),sin(b*XY)*siz);
}

vec3 proj1(vec3 p){
return p/(1-dot(p,p)/(CurveWeight*CurveWeight));
}

vec3 proj2(vec3 p){
return 2*p/(1+sqrt(1+dot(p,p)*4/(CurveWeight*CurveWeight)));
}

vec3 projection(vec3 p){
    float weight=CurveWeight;
    vec3 res;
    switch(CurveMode){
        case 0:
            vec2 camdir=CameraDirection();
            vec2 q=cmul(camdir,cpow(cmul(conj(camdir),p.xz),4./CurveWeight));
            //このケースでは、CurveWeightは整数である必要がある。これは、中心の正方形を多角形へ写す
            res=vec3(q.x,p.y,q.y);
            break;
        case 1:
            res=mobius3(p,vec3(10000,0,0));
            break;
        case 2:
            res=mobius3(p,vec3(0,weight,0))-vec3(0,weight,0);
            break;
        case 3:
            vec2 camdir1=CameraDirection();
            vec2 q1=cmul(camdir1,cpow(cmul(conj(camdir1),p.xz+vec2(2,0)),4./CurveWeight)-cmul(conj(camdir1),cpow(vec2(2,0),4./CurveWeight)));
            res=vec3(q1.x,p.y,q1.y);
            break;
        case 4:
            res=eulerpow(p,CurveWeight);
            break;
        case 5:
            res=mobius3h(p,vec3(0,weight,0))-vec3(0,weight,0);
            break;
        case 6:
            if(length(p)>=weight-1){
                res=p;
                break;
            }
            res=proj1(p-vec3(0,1,0))+vec3(0,1,0);
            break;
        case 7:
            res=proj2(p+vec3(0,1,0))-vec3(0,1,0);
            break;
        case 8:
            if(length(p)>=weight-1){
                res=p;
                break;
            }
            res=proj1(p+vec3(0,1,0))-vec3(0,1,0);
            break;
        case 9:
            res=proj2(p-vec3(0,1,0))+vec3(0,1,0);
            break;
        case 10:
            float rad2=length(p);
            vec3 v2=vec3(0,weight*sin(rad2/5.),0);
            res=mobius3(p,v2)-v2;
            break;
        default:
            res=p;
            break;
        
    }
    return res;
};