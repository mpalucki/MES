public class Matrix_H_condition {
   // double[][] matrix_H_cond;
    int number_of_points;
    Element element;
    double[][][] NxN;
    double[][][] NxNsum;
    double[][] ksi_eta_N;
    double[][] final_H_cond;
    Point[] pointsOnBounders;
    double LengthUp, LengthSide;
    
    public Matrix_H_condition(Element element) {
        this.number_of_points = 4;
        this.element = element;

        this.NxN = new double[2*number_of_points][number_of_points][number_of_points];
        this.NxNsum = new double[number_of_points][number_of_points][number_of_points];
        this.ksi_eta_N = new double[2 * number_of_points][number_of_points];
        this.final_H_cond = new double[number_of_points][number_of_points];
        LengthUp = Math.sqrt(Math.pow(element.fourth.x-element.first.x,2) + Math.pow(element.fourth.y-element.first.y,2));
        LengthSide =  Math.sqrt(Math.pow(element.third.x-element.second.x,2) + Math.pow(element.third.y-element.second.y,2));
        this.pointsOnBounders = new Point[8];

        pointsOnBounders[5] = new Point(-(1/Math.sqrt(3)),1);
        pointsOnBounders[4] = new Point((1/Math.sqrt(3)),1);
        pointsOnBounders[3] = new Point(1,(1/Math.sqrt(3)));
        pointsOnBounders[2] = new Point(1, -(1/Math.sqrt(3)));
        pointsOnBounders[1] = new Point((1/Math.sqrt(3)),-1);
        pointsOnBounders[0] = new Point(-(1/Math.sqrt(3)),-1);
        pointsOnBounders[7] = new Point(-1,-(1/Math.sqrt(3)));
        pointsOnBounders[6] = new Point(-1,(1/Math.sqrt(3)));

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

    void calculate_ksi_eta_N(){
       // System.out.println("Calculate ksi_eta from condition H : \n");
        for(int i=0;i<2*number_of_points;i++){
            ksi_eta_N[i][0]=N1(pointsOnBounders[i].x,pointsOnBounders[i].y);
            //System.out.print(ksi_eta_N[i][0]+" ");
            ksi_eta_N[i][1]=N2(pointsOnBounders[i].x,pointsOnBounders[i].y);
            //System.out.print(ksi_eta_N[i][1]+" ");
            ksi_eta_N[i][2]=N3(pointsOnBounders[i].x,pointsOnBounders[i].y);
            //System.out.print(ksi_eta_N[i][2]+" ");
            ksi_eta_N[i][3]=N4(pointsOnBounders[i].x,pointsOnBounders[i].y);
           // System.out.print(ksi_eta_N[i][3]+" ");
           // System.out.print("\n");
        }
    }
    void calculate_NxN(){
        for(int i=0;i<2*number_of_points;i++){
            for(int j=0;j<number_of_points;j++){
                for (int k=0;k<number_of_points;k++){
                    NxN[i][0][k] = ksi_eta_N[i][0]*ksi_eta_N[i][k]*element.alfa;
                    NxN[i][1][k] = ksi_eta_N[i][1]*ksi_eta_N[i][k]*element.alfa;
                    NxN[i][2][k] = ksi_eta_N[i][2]*ksi_eta_N[i][k]*element.alfa;
                    NxN[i][3][k] = ksi_eta_N[i][3]*ksi_eta_N[i][k]*element.alfa;
                }
            }
        }
//        System.out.println("Calculate NxN from condition H : \n");
//        for(int i=0;i<2*number_of_points;i++) {
//            System.out.println(" matrix  : "+(i+1));
//            for (int j = 0; j < number_of_points; j++) {
//                for (int k = 0; k < number_of_points; k++) {
//                    System.out.print(NxN[i][j][k]+" ");
//                }
//                System.out.println();
//            }
//        }

    }

    void calculate_NxNsum(){
        for(int i=0;i<number_of_points;i++) {
            for (int j = 0; j < number_of_points; j++) {
                for (int k = 0; k < number_of_points; k++) {
                    if(i%2==0)
                        NxNsum[i][j][k] += (NxN[2*i][j][k] + NxN[(2*i)+1][j][k])*(LengthUp/2);
                    if(i%2!=0)
                        NxNsum[i][j][k] += (NxN[2*i][j][k] + NxN[(2*i)+1][j][k])*(LengthSide/2);
                }
            }
        }
//        System.out.println("Calculate NxNsum from condition H : \n");
//        for(int i=0;i<number_of_points;i++) {
//            System.out.println(" matrix  : "+(i+1));
//            for (int j = 0; j < number_of_points; j++) {
//                for (int k = 0; k < number_of_points; k++) {
//                    System.out.print(NxNsum[i][j][k]+" ");
//                }
//                System.out.println();
//            }
//        }
    }
    void calculate_all_H_cond(){
        calculate_ksi_eta_N();
        calculate_NxN();
        calculate_NxNsum();

        for(int i=0;i<4;i++){
            if (element.whichBounder[i] == 1){
                for(int j=0;j<number_of_points;j++){
                    for(int k=0;k<number_of_points;k++){
                        final_H_cond[j][k] += NxNsum[i][j][k];
                    }
                }
            }
        }
    }


    void sum_two_matrix(double[][] m1, double [][]m2){
        for(int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                m1[i][j] += (m1[i][j] + m2[i][j]);
            }
        }
    }
    public double[][] getFinal_H_cond() {
        return final_H_cond;
    }

    public void show_local_cond_h(){
        System.out.println("local H condition");
        for(int i=0;i<number_of_points;i++){
            for(int j=0;j<number_of_points;j++){
                System.out.print(final_H_cond[i][j]+" ");
            }
            System.out.println();
        }
    }
}
