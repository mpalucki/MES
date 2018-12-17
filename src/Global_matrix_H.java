public class Global_matrix_H {
    int number_of_points;
    double[][] global_H;
    public Global_matrix_H(int number,int number1){
        this.number_of_points = number*number1;
        this.global_H = new double[number_of_points][number_of_points];
    }

    void calculateGlobalMatrix_H(Element element,Matrix_H local_h, Global_matrix_H_condition local_h_condition) {

        global_H[element.first.id][element.first.id] += local_h.getMatrix_H()[0][0];
        global_H[element.first.id][element.second.id] +=  local_h.getMatrix_H()[0][1];
        global_H[element.first.id][element.third.id] +=  local_h.getMatrix_H()[0][2];
        global_H[element.first.id][element.fourth.id] +=  local_h.getMatrix_H()[0][3];

        global_H[element.second.id][element.first.id] += local_h.getMatrix_H()[1][0];
        global_H[element.second.id][element.second.id] += local_h.getMatrix_H()[1][1];
        global_H[element.second.id][element.third.id] += local_h.getMatrix_H()[1][2];
        global_H[element.second.id][element.fourth.id] += local_h.getMatrix_H()[1][3];

        global_H[element.third.id][element.first.id] += local_h.getMatrix_H()[2][0];
        global_H[element.third.id][element.second.id] += local_h.getMatrix_H()[2][1];
        global_H[element.third.id][element.third.id] += local_h.getMatrix_H()[2][2];
        global_H[element.third.id][element.fourth.id] += local_h.getMatrix_H()[2][3];

        global_H[element.fourth.id][element.first.id] += local_h.getMatrix_H()[3][0];
        global_H[element.fourth.id][element.second.id] += local_h.getMatrix_H()[3][1];
        global_H[element.fourth.id][element.third.id] += local_h.getMatrix_H()[3][2];
        global_H[element.fourth.id][element.fourth.id] += local_h.getMatrix_H()[3][3];
        //add_condition(local_h_condition);
    }

    void add_condition(Global_matrix_H_condition cond_h){
        for(int i=0;i<number_of_points*number_of_points;i++){
            for(int j=0;j<number_of_points;j++){
                global_H[i][j] += cond_h.getGlobal_H_condition()[i][j];
            }
        }
    }
}
