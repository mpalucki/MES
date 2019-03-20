public class Jacobian {
    Point[] tab;
    int number_of_points;
    Point[] tab_ksi_eta;
    Point[] integrationPoints;
    double[][] tab_dksi;    //first - fun N, sec - ksi
    double[][] tab_deta;    //first - fun N, sec - eta
    double[][] jacobian_matrix;
    double [] determinant_jacobian_matrix;
    double [][] reverse_jacobian_matrix;

    public Jacobian(Element element) {
        number_of_points = 4;
        tab = element.getPoints_of_element();
        this.tab_ksi_eta = new Point[number_of_points];
        this.tab_ksi_eta[0] = new Point(-(1 / Math.sqrt(3)), -(1 / Math.sqrt(3)));
        this.tab_ksi_eta[1] = new Point((1 / Math.sqrt(3)), -(1 / Math.sqrt(3)));
        this.tab_ksi_eta[2] = new Point((1 / Math.sqrt(3)), (1 / Math.sqrt(3)));
        this.tab_ksi_eta[3] = new Point(-(1 / Math.sqrt(3)), (1 / Math.sqrt(3)));
        this.integrationPoints = tab;
        this.tab_dksi = new double[number_of_points][number_of_points];
        this.tab_deta = new double[number_of_points][number_of_points];
        this.jacobian_matrix = new double[number_of_points][number_of_points];
        this.determinant_jacobian_matrix = new double[number_of_points];
        this.reverse_jacobian_matrix = new double[number_of_points][number_of_points];
    }

    public void show_ksi_eta() {
        for (int i = 0; i < number_of_points; i++) {
        }
    }

    // funkcje kształtu
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

    //pochodne funkcji kształtu
    public double dN1dksi(double eta) {
        return -(0.25) * (1 - eta);
    }

    public double dN2dksi(double eta) {
        return (0.25) * (1 - eta);
    }

    public double dN3dksi(double eta) {
        return (0.25) * (1 + eta);
    }

    public double dN4dksi(double eta) {
        return -(0.25) * (1 + eta);
    }

    public double dN1deta(double ksi) {
        return -(0.25) * (1 - ksi);
    }

    public double dN2deta(double ksi) {
        return -(0.25) * (1 + ksi);
    }

    public double dN3deta(double ksi) {
        return (0.25) * (1 + ksi);
    }

    public double dN4deta(double ksi) {
        return (0.25) * (1 - ksi);
    }

    public void calculate_derivatives() {
        for (int i = 0; i < number_of_points; i++) {
            tab_dksi[0][i] = dN1dksi(tab_ksi_eta[i].y);
            tab_dksi[1][i] = dN2dksi(tab_ksi_eta[i].y);
            tab_dksi[2][i] = dN3dksi(tab_ksi_eta[i].y);
            tab_dksi[3][i] = dN4dksi(tab_ksi_eta[i].y);

            tab_deta[0][i] = dN1deta(tab_ksi_eta[i].x);
            tab_deta[1][i] = dN2deta(tab_ksi_eta[i].x);
            tab_deta[2][i] = dN3deta(tab_ksi_eta[i].x);
            tab_deta[3][i] = dN4deta(tab_ksi_eta[i].x);
        }
    }

    public void show_derivatives() { //pochodne
        for (int i = 0; i < number_of_points; i++) {
            for (int j = 0; j < number_of_points; j++) {
               
            }
            
        }
        
        for (int i = 0; i < number_of_points; i++) {
            for (int j = 0; j < number_of_points; j++) {
                //System.out.print(tab_deta[i][j] + " ");
            }
          
        }
    }

    public void calculate_Jacobian(){
        calculate_derivatives();


        for(int i=0;i<number_of_points;i++){
            jacobian_matrix[0][i] = tab_dksi[0][i]*integrationPoints[0].x+tab_dksi[1][i]*integrationPoints[1].x+tab_dksi[2][i]*integrationPoints[2].x+tab_dksi[3][i]*integrationPoints[3].x;

            jacobian_matrix[1][i] = tab_dksi[0][i]*integrationPoints[0].y+tab_dksi[1][i]*integrationPoints[1].y+tab_dksi[2][i]*integrationPoints[2].y+tab_dksi[3][i]*integrationPoints[3].y;

            jacobian_matrix[2][i] = tab_deta[0][i]*integrationPoints[0].x+tab_deta[1][i]*integrationPoints[1].x+tab_deta[2][i]*integrationPoints[2].x+tab_deta[3][i]*integrationPoints[3].x;

            jacobian_matrix[3][i] = tab_deta[0][i]*integrationPoints[0].y+tab_deta[1][i]*integrationPoints[1].y+tab_deta[2][i]*integrationPoints[2].y+tab_deta[3][i]*integrationPoints[3].y;

        }
    }
    public void show_Jacobian(){
        for (int i = 0; i < number_of_points; i++) {
            for (int j = 0; j < number_of_points; j++) {
                System.out.print(jacobian_matrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    public void calculate_determinant(){
        for(int i=0;i<number_of_points;i++){
            for(int j=0;j<number_of_points;j++){
                determinant_jacobian_matrix[i] = (jacobian_matrix[0][i]*jacobian_matrix[3][i])-(jacobian_matrix[1][i]*jacobian_matrix[2][i]);

            }
        }
    }

    public void calculate_reverse_matrix(){
        for(int i=0;i<number_of_points;i++) {
            for (int j = 0; j < number_of_points; j++) {
                reverse_jacobian_matrix[i][j] = jacobian_matrix[i][j]/determinant_jacobian_matrix[i];
                reverse_jacobian_matrix[i][j] = Math.round(reverse_jacobian_matrix[i][j]);

            }
        }
    }

    public void show_reverse_Jacobian(){
        for (int i = 0; i < number_of_points; i++) {
            for (int j = 0; j < number_of_points; j++) {
                System.out.print(reverse_jacobian_matrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    public void calculate_all_jacobian(){
        calculate_derivatives();
        calculate_Jacobian();
        calculate_determinant();
        calculate_reverse_matrix();
    }

    public int getNumber_of_points() {
        return number_of_points;
    }

    public double[][] getTab_dksi() {
        return tab_dksi;
    }

    public double[][] getTab_deta() {
        return tab_deta;
    }

    public double[][] getJacobian_matrix() {
        return jacobian_matrix;
    }

    public double[] getDeterminant_jacobian_matrix() {
        return determinant_jacobian_matrix;
    }

    public double[][] getReverse_jacobian_matrix() {
        return reverse_jacobian_matrix;
    }

    public Point[] getTab_ksi_eta() {
        return tab_ksi_eta;
    }
}
