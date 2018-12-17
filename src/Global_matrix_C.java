public class Global_matrix_C {
    int number_of_points;
    double[][] global_C;
    public Global_matrix_C(int number, int number1){
        this.number_of_points = number*number1;
        this.global_C = new double[number_of_points][number_of_points];
    }
    void calculateGlobalMatrix_C(Element element,Matrix_C tmp) {

        global_C[element.first.id][element.first.id] += tmp.getNxNsum()[0][0];
        global_C[element.first.id][element.second.id] +=  tmp.getNxNsum()[0][1];
        global_C[element.first.id][element.third.id] +=  tmp.getNxNsum()[0][2];
        global_C[element.first.id][element.fourth.id] +=  tmp.getNxNsum()[0][3];

        global_C[element.second.id][element.first.id] += tmp.getNxNsum()[1][0];
        global_C[element.second.id][element.second.id] += tmp.getNxNsum()[1][1];
        global_C[element.second.id][element.third.id] += tmp.getNxNsum()[1][2];
        global_C[element.second.id][element.fourth.id] += tmp.getNxNsum()[1][3];

        global_C[element.third.id][element.first.id] += tmp.getNxNsum()[2][0];
        global_C[element.third.id][element.second.id] += tmp.getNxNsum()[2][1];
        global_C[element.third.id][element.third.id] += tmp.getNxNsum()[2][2];
        global_C[element.third.id][element.fourth.id] += tmp.getNxNsum()[2][3];

        global_C[element.fourth.id][element.first.id] += tmp.getNxNsum()[3][0];
        global_C[element.fourth.id][element.second.id] += tmp.getNxNsum()[3][1];
        global_C[element.fourth.id][element.third.id] += tmp.getNxNsum()[3][2];
        global_C[element.fourth.id][element.fourth.id] += tmp.getNxNsum()[3][3];
    }

}
