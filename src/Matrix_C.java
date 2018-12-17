public class Matrix_C {
    Jacobian jacobian;
    double cp;
    double ro;
    Element element;
    Point[] tmp;
    Point[] ksi_eta;
    double[][] ksi_eta_N;
    double[][][] NxN;
    double[][] NxNsum;  //matrix C
    int number_of_points;

    public Matrix_C(Element element,double cp, double ro) {
        this.element = element;
        this.tmp = element.getPoints_of_element();
        this.jacobian = new Jacobian(element);
        jacobian.calculate_all_jacobian();
        this.cp = cp;
        this.ro = ro;
        this.number_of_points = jacobian.getNumber_of_points();
        ksi_eta = jacobian.getTab_ksi_eta();
        ksi_eta_N = new double[number_of_points][number_of_points];
        this.NxN = new double[number_of_points][number_of_points][number_of_points];
        this.NxNsum = new double[number_of_points][number_of_points];
    }

    //funkcje kształtu
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


    void calculate_ksi_eta_N() {
        for (int i = 0; i < number_of_points; i++) {
            ksi_eta_N[i][0] = N1(ksi_eta[i].x, ksi_eta[i].y);
          //  System.out.print(ksi_eta_N[i][0] + " ");
            ksi_eta_N[i][1] = N2(ksi_eta[i].x, ksi_eta[i].y);
          //  System.out.print(ksi_eta_N[i][1] + " ");
            ksi_eta_N[i][2] = N3(ksi_eta[i].x, ksi_eta[i].y);
          //  System.out.print(ksi_eta_N[i][2] + " ");
            ksi_eta_N[i][3] = N4(ksi_eta[i].x, ksi_eta[i].y);
           // System.out.print(ksi_eta_N[i][3] + " ");
           // System.out.print("\n");
        }
        //dalej tak samo jak dla NxN w condition
    }

    void calculate_NxN(){
        for(int i=0;i<4;i++){
            for(int j=0;j<number_of_points;j++){
                for (int k=0;k<number_of_points;k++){
                    NxN[i][0][k] = ksi_eta_N[i][0]*ksi_eta_N[i][k]*jacobian.determinant_jacobian_matrix[i]*cp*ro;
                    NxN[i][1][k] = ksi_eta_N[i][1]*ksi_eta_N[i][k]*jacobian.determinant_jacobian_matrix[i]*cp*ro;
                    NxN[i][2][k] = ksi_eta_N[i][2]*ksi_eta_N[i][k]*jacobian.determinant_jacobian_matrix[i]*cp*ro;
                    NxN[i][3][k] = ksi_eta_N[i][3]*ksi_eta_N[i][k]*jacobian.determinant_jacobian_matrix[i]*cp*ro;
                }
            }
        }
    }
    void calculate_NxNsum(){
       // System.out.print("Macierz C kompletna : \n");
            for (int j = 0; j < number_of_points; j++) {
                for (int k = 0; k < number_of_points; k++) {
                   NxNsum[j][k] = NxN[0][j][k]+NxN[1][j][k]+NxN[2][j][k]+NxN[3][j][k];
                    //System.out.print(NxNsum[j][k]+" ");
                }
                //System.out.print("\n");
            }
    }

    void calculate_all_C(){
        calculate_ksi_eta_N();
        calculate_NxN();
        calculate_NxNsum();
    }

    void show(){
        System.out.println("Matrix C local:");
        for(int i=0;i<number_of_points;i++){
            for(int j=0;j<number_of_points;j++){
                System.out.print(NxNsum[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public double[][] getNxNsum() {
        return NxNsum;
    }
}
