public class Global_matrix_H_condition {
    int number_of_points;
    double[][] global_H_condition;
    public Global_matrix_H_condition(int number, int number1){
        this.number_of_points = number*number1;
        this.global_H_condition = new double[number_of_points][number_of_points];
    }
    void calculateGlobal_H_cond(Element element,Matrix_H_condition local_h){
        global_H_condition[element.first.id][element.first.id] += local_h.getFinal_H_cond()[0][0];
        global_H_condition[element.first.id][element.second.id] +=  local_h.getFinal_H_cond()[0][1];
        global_H_condition[element.first.id][element.third.id] +=  local_h.getFinal_H_cond()[0][2];
        global_H_condition[element.first.id][element.fourth.id] +=  local_h.getFinal_H_cond()[0][3];

        global_H_condition[element.second.id][element.first.id] += local_h.getFinal_H_cond()[1][0];
        global_H_condition[element.second.id][element.second.id] += local_h.getFinal_H_cond()[1][1];
        global_H_condition[element.second.id][element.third.id] += local_h.getFinal_H_cond()[1][2];
        global_H_condition[element.second.id][element.fourth.id] += local_h.getFinal_H_cond()[1][3];

        global_H_condition[element.third.id][element.first.id] += local_h.getFinal_H_cond()[2][0];
        global_H_condition[element.third.id][element.second.id] += local_h.getFinal_H_cond()[2][1];
        global_H_condition[element.third.id][element.third.id] += local_h.getFinal_H_cond()[2][2];
        global_H_condition[element.third.id][element.fourth.id] += local_h.getFinal_H_cond()[2][3];

        global_H_condition[element.fourth.id][element.first.id] += local_h.getFinal_H_cond()[3][0];
        global_H_condition[element.fourth.id][element.second.id] += local_h.getFinal_H_cond()[3][1];
        global_H_condition[element.fourth.id][element.third.id] += local_h.getFinal_H_cond()[3][2];
        global_H_condition[element.fourth.id][element.fourth.id] += local_h.getFinal_H_cond()[3][3];
    }

    public double[][] getGlobal_H_condition() {
        return global_H_condition;
    }
}
