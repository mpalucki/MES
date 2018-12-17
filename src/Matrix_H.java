public class Matrix_H {
    int number_of_points;
    Element element;
    Jacobian jacobian;
    Point[] tmp;
    double conductive;
    double[][] detN_det_X;
    double[][] detN_det_Y;
    double[][][] detN_det_X_detN_det_X_T;
    double[][][] detN_detY_detN_detY_T;
    double[][][] sum_detN;
    double[][] matrix_H;
    //double[][] matrix_H_cond;

    public Matrix_H(Element element){
        this.number_of_points = 4;
        this.tmp = element.getPoints_of_element();

        this.jacobian = new Jacobian(element);
        this.jacobian.calculate_all_jacobian();
        this.conductive = element.k;
        this.number_of_points = jacobian.getNumber_of_points();
        this.detN_det_X=new double[number_of_points][number_of_points];
        this.detN_det_Y=new double[number_of_points][number_of_points];
        this.detN_det_X_detN_det_X_T = new double[number_of_points][number_of_points][number_of_points];
        this.detN_detY_detN_detY_T = new double[number_of_points][number_of_points][number_of_points];
        this.sum_detN = new double[number_of_points][number_of_points][number_of_points];
        this.matrix_H = new double[number_of_points][number_of_points];
        //this.matrix_H_cond = new double[number_of_points][number_of_points];
    }

    public void calculate_detN_detX(){
        double[][] reverse = jacobian.getReverse_jacobian_matrix();
        double [][] ksi = jacobian.getTab_dksi();
        double [][] eta = jacobian.getTab_deta();
        for(int i=0;i<number_of_points;i++){
            detN_det_X[0][i]=reverse[0][0]*ksi[i][0]+reverse[1][0]*eta[i][0];
            detN_det_X[1][i]=reverse[0][1]*ksi[i][1]+reverse[1][1]*eta[i][1];
            detN_det_X[2][i]=reverse[0][2]*ksi[i][2]+reverse[1][2]*eta[i][2];
            detN_det_X[3][i]=reverse[0][3]*ksi[i][3]+reverse[1][3]*eta[i][3];
        }
//        System.out.println("detN_detX");
//        for(int i=0;i<number_of_points;i++){
//            for(int j=0;j<number_of_points;j++){
//                System.out.print(detN_det_X[i][j]+" ");
//            }
//            System.out.println();
//        }
    }


    public void calculate_detN_detY(){
        double[][] reverse = jacobian.getReverse_jacobian_matrix();
        double [][] ksi = jacobian.getTab_dksi();
        double [][] eta = jacobian.getTab_deta();
        for(int i=0;i<number_of_points;i++){
            detN_det_Y[0][i]=reverse[2][0]*ksi[i][0]+reverse[3][0]*eta[i][0];
            detN_det_Y[1][i]=reverse[2][1]*ksi[i][1]+reverse[3][1]*eta[i][1];
            detN_det_Y[2][i]=reverse[2][2]*ksi[i][2]+reverse[3][2]*eta[i][2];
            detN_det_Y[3][i]=reverse[2][3]*ksi[i][3]+reverse[3][3]*eta[i][3];
        }
//        System.out.println("detN_detX");
//        for(int i=0;i<number_of_points;i++){
//            for(int j=0;j<number_of_points;j++){
//                System.out.print(detN_det_Y[i][j]+" ");
//            }
//            System.out.println();
//        }
    }

    public void calculate_detN_detX_multiple(){
        for(int x=0;x<number_of_points;x++) {
            for (int i = 0; i < number_of_points; i++) {
                for (int j = 0; j < number_of_points; j++) {
                    detN_det_X_detN_det_X_T[x][i][j]=detN_det_X[x][i]*detN_det_X[x][j];
                }
            }
        }

    }

    public void calculate_detN_detY_multiple(){
        for(int x=0;x<number_of_points;x++) {
            for (int i = 0; i < number_of_points; i++) {
                for (int j = 0; j < number_of_points; j++) {
                    detN_detY_detN_detY_T[x][i][j]=detN_det_Y[x][i]*detN_det_Y[x][j];
                }
            }
        }
    }

    public void multiple_detN_detX_by_determinant() {
        double[] det = jacobian.getDeterminant_jacobian_matrix();
        for (int x = 0; x < number_of_points; x++) {
            //System.out.print("point "+ (x+1)+"\n");
            for (int i = 0; i < number_of_points; i++) {
                for (int j = 0; j < number_of_points; j++) {
                    detN_det_X_detN_det_X_T[x][i][j] *= det[x];
                    detN_detY_detN_detY_T[x][i][j] *= det[x];
                }
            }
        }

//        for(int x=0;x<number_of_points;x++) {
//            for (int i = 0; i < number_of_points; i++) {
//                for (int j = 0; j < number_of_points; j++) {
//                    System.out.print(detN_detY_detN_detY_T[x][i][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
//        System.out.println();

    }
    public void calculate_sum_detN(){
        for (int x = 0; x < number_of_points; x++) {
            for (int i = 0; i < number_of_points; i++) {
                for (int j = 0; j < number_of_points; j++) {
                    sum_detN[x][i][j]=conductive*(detN_det_X_detN_det_X_T[x][i][j]+detN_detY_detN_detY_T[x][i][j]);
                }
            }
        }

        //TUTAJ JEST BłĄD!!!!!
//        for(int x=0;x<number_of_points;x++) {
//            for (int i = 0; i < number_of_points; i++) {
//                for (int j = 0; j < number_of_points; j++) {
//                    System.out.print(sum_detN[x][i][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
//        System.out.println();
    }

    public void calculate_matrix_H(){
        calculate_detN_detX();
        calculate_detN_detY();
        calculate_detN_detX_multiple();
        calculate_detN_detY_multiple();
        multiple_detN_detX_by_determinant();
        calculate_sum_detN();
        for (int x = 0; x < number_of_points; x++) {
            for (int i = 0; i < number_of_points; i++) {
                for (int j = 0; j < number_of_points; j++) {
                matrix_H[x][i]+=sum_detN[j][x][i];//+sum_detN[1][i][j]+sum_detN[2][i][j]+sum_detN[3][i][j];
                }
            }
        }

    }


    public void show_con(){
        for (int x = 0; x < number_of_points; x++) {
            System.out.print("point " + (x + 1) + "\n");
            for (int i = 0; i < number_of_points; i++) {
                for (int j = 0; j < number_of_points; j++) {
                    System.out.print(sum_detN[x][i][j]+" ");
                }
                System.out.print("\n");
            }
        }
    }
    public void show(){
        for(int i=0;i<number_of_points;i++){
            for(int j=0;j<number_of_points;j++){
                System.out.print(matrix_H[i][j]+" ");
            }
            System.out.print("\n");
        }
    }

    void add_two_matrix(double[][] m1){
        for(int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                this.matrix_H[i][j] += m1[i][j];
            }
        }
    }

    public double[][] getMatrix_H() {
        return matrix_H;
    }

}
