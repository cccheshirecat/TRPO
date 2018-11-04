public interface Data {
    public static final float Jz=20f; //20кгм2 - момент инерции спутника по каналу тангажа J0
    public static final float weight=350f; //кг - вес спутника
    public static final float length = 0.8f; //м -  сторона куба
    public static final float Jmx = 0.02f; //кгм2 - момент инерции маховика
    public static final float Hmax = 18.0f; //н*м*сек – максимально достижимый кинетический момент маховика(реальный при-бор)
    public static final float omegaMax = 900.0f; //1/сек = рад
    public static final float Ryrd = 2*10^-1  ; //нано – тяга управляющего ракетного двигателя [кг*м/сек^2]
    public static final float Myrd =0.8f; //нм
    public static final float CM = 0.4f; //м - центр масс
    public static final float M_yrd = 0.004f; //1/sec^2 - Управляющее ускорение от УРД
    public static final float MyrdMax = 0.2f; //нм - максимально возможный управляющий момент маховика
    public static final float omega1max = 10; //1/sec^2 - максимальное ускорение при разгоне маховика
    public static final float omega1T = 1; //1/sec^2 - ускорение торможения маховика при сбросе накопленного кинетического момента.
    public static final float Mt = 0.03f; //нм  - момент, возникающий при торможении маховика и действующий на спутник
    public static final float M_t=0.001f;//1/sec^2 - угловое ускорение спутника при торможении маховика
    public static final float M_v=0.002f; //1/sec^2 - возмущающее ускорение
}
