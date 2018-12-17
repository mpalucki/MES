public class Element {
    int id;
    int[] whichBounder; //boundary condictions
    Point[] points_of_element;
   Node first, second, third, fourth;
   double k; //wspolczynnik przewodzenia
    double alfa;
    double[][] matrixH2;
    double[][] matrixH1;
    double[][] H;

    Element(){

    }

    public int getId() {
        return id;
    }

    Element(Node given_first, Node given_sec, Node given_third, Node given_fourth, int id, int[] whichBounder,double k,double alfa) {
        matrixH2 = new double[4][4];
        matrixH1 = new double[4][4];
        H = new double[4][4];
        this.points_of_element = new Point[4];
        this.k = k;
        this.alfa = alfa;
        points_of_element[0] = new Point(given_first.x,given_first.y);
        points_of_element[1] = new Point(given_sec.x,given_sec.y);
        points_of_element[2] = new Point(given_third.x ,given_third.y);
        points_of_element[3] = new Point(given_fourth.x,given_fourth.y);

        this.whichBounder = new int[4];
        this.id = id;
        first = given_first;
        second = given_sec;
        third = given_third;
        fourth = given_fourth;
        this.whichBounder = whichBounder;
    }

    public void show_elements(){
        System.out.println("x : "+first.x+" ,y : "+first.y);
        System.out.println("x : "+second.x+" ,y : "+second.y);
        System.out.println("x : "+third.x+" ,y : "+third.y);
        System.out.println("x : "+fourth.x+" ,y : "+fourth.y);
        for (int i=0;i<4;i++){
            System.out.println("Bounder "+i+" "+whichBounder[i]);
        }
    }

    public int[] getWhichBounder() {
        return whichBounder;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point[] getPoints_of_element() {
        return points_of_element;
    }
}
