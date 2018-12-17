public class Global_vector_P {
    int number_of_points;
    double[] global_P;
    public Global_vector_P(int number,int number1){
        this.number_of_points = number*number1;
        this.global_P = new double[number_of_points];
    }

    void calculate_Global_vector_P(Element element, vector_P vector_p){
        global_P[element.first.id] += vector_p.getVector_P()[0];
        global_P[element.second.id] += vector_p.getVector_P()[1];
        global_P[element.third.id] += vector_p.getVector_P()[2];
        global_P[element.fourth.id] += vector_p.getVector_P()[3];

    }
}
