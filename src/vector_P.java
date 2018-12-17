public class vector_P {
    int number_of_points;
    double ambient_temperature;
    Element element;
    double alfa;
    double[][] ksi_eta_N;
    double[][] Nsum;
    double LengthUp, LengthSide;

    double[] vector_P;
    Point[] pointsOnBounders;

    public vector_P(Element element,double ambient,double alfa){
        this.alfa = alfa;
        this.number_of_points = 4;
        this.ambient_temperature = ambient;
        this.element = element;
        this.pointsOnBounders = new Point[8];
        this.ksi_eta_N = new double[2 * number_of_points][number_of_points];
        this.Nsum = new double[4][4];
        this.vector_P = new double[number_of_points];

        LengthUp = Math.sqrt(Math.pow(element.fourth.x-element.first.x,2) + Math.pow(element.fourth.y-element.first.y,2));
        LengthSide =  Math.sqrt(Math.pow(element.third.x-element.second.x,2) + Math.pow(element.third.y-element.second.y,2));

        pointsOnBounders[5] = new Point(-(1/Math.sqrt(3)),1);
        pointsOnBounders[4] = new Point((1/Math.sqrt(3)),1);
        pointsOnBounders[3] = new Point(1,(1/Math.sqrt(3)));
        pointsOnBounders[2] = new Point(1,-(1/Math.sqrt(3)));
        pointsOnBounders[1] = new Point((1/Math.sqrt(3)),-1);
        pointsOnBounders[0] = new Point( -(1/Math.sqrt(3)),-1);
        pointsOnBounders[7] = new Point(-1,-(1/Math.sqrt(3)));
        pointsOnBounders[6] = new Point(-1, (1/Math.sqrt(3)));
    }

    public double N1(double ksi, double eta) {
        return 0.25 * (1 - ksi) * (1 - eta);
    }

    public double N2(double ksi, double eta) {
        return 0.25 * (1 + ksi) * (1 - eta);
    }

    public double N3(double ksi, double eta) {
        return 0.25 * (1 + ksi) * (1 + eta);
    }

    public double N4(double ksi, double eta) {
        return 0.25 * (1 - ksi) * (1 + eta);
    }

    void calculate_ksi_eta_N(){
        for(int i=0;i<2*number_of_points;i++){
            ksi_eta_N[i][0]=N1(pointsOnBounders[i].x,pointsOnBounders[i].y)*alfa*(LengthUp/2)*ambient_temperature;
            ksi_eta_N[i][1]=N2(pointsOnBounders[i].x,pointsOnBounders[i].y)*alfa*(LengthSide/2)*ambient_temperature;
            ksi_eta_N[i][2]=N3(pointsOnBounders[i].x,pointsOnBounders[i].y)*alfa*(LengthUp/2)*ambient_temperature;
            ksi_eta_N[i][3]=N4(pointsOnBounders[i].x,pointsOnBounders[i].y)*alfa*(LengthSide/2)*ambient_temperature;

        }
    }
    void calculate_Nsum(){
        for(int i=0;i<number_of_points;i++){
            for(int j=0;j<number_of_points;j++){
                Nsum[i][j] += ksi_eta_N[2*i][j]+ksi_eta_N[(2*i)+1][j];
            }
        }
    }

    void calculate_vector_P(){
        calculate_ksi_eta_N();
        calculate_Nsum();
        for(int i=0;i<number_of_points;i++){
            if(element.whichBounder[i] == 1){
                for(int j=0;j<number_of_points;j++){
                    vector_P[j] += Nsum[i][j];
                }
            }
        }
        for(int i=0;i<number_of_points;i++){
            vector_P[i] *= -1;
        }
    }

    double[] sum_two_vector(double[] v1, double []v2){
        double[] ret = new double[number_of_points];
        for(int i=0;i<number_of_points;i++){
            ret[i] += v1[i]+v2[i];
        }
        return ret;
    }
    void show_local_vector_P(){
        System.out.println("local vector P: ");
        for(int i=0;i<number_of_points;i++){
            System.out.print(vector_P[i]+" ");
        }
        System.out.println();
    }

    public double[] getVector_P() {
        return vector_P;
    }
//add given start temperature

}
