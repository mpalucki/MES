import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static sun.misc.Version.print;
import static sun.misc.Version.println;

public class Main {

    public static void main(String[] args) {
        //select file with parameters
        File file = new File("dane.txt");
       
        Scanner br = null;
        List<Double> list = new ArrayList<Double>();
        try {
            br = new Scanner(file);

            while (br.hasNextDouble())
                list.add(br.nextDouble());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(list);
        double height = list.get(0);
        double width = list.get(1);
        double ty = list.get(2);
        double tx = list.get(3);
        double initial_temperature = list.get(4);
        double simulation_time = list.get(5);
        double simulation_step_time = list.get(6);
        double ambient_temperature = list.get(7);
        double alfa = list.get(8);
        double specific_heat = list.get(9);
        double conductitivity = list.get(10);
        double density = list.get(11);
        int number_of_nodes_in_height = (int)(ty);
        int number_of_nodes_in_width =(int)(tx);

        if(number_of_nodes_in_height <= 1 || number_of_nodes_in_width <= 1){
            return;
        }
        int size_of_nodes = number_of_nodes_in_height * number_of_nodes_in_width;
        Node[] nodes_list = new Node[size_of_nodes];

        //-------------------------------------------------------------------------------
        //wezly w elemencie - od lewego dolnego -numeracja przeciwnie do wskazówek zegara

        //warunki brzegowe są sprawdzane od krawędzi górnej elementu, zgodnie z wskazówkami zegara

        //

        for (int i = 0; i < number_of_nodes_in_width; i++) {  //given co-ordinates to node
            for (int j = 0; j < number_of_nodes_in_height; j++) {
                nodes_list[j + (number_of_nodes_in_height*i)] = new Node(

                        i*(width / (number_of_nodes_in_width-1)),
                        j * (height / (number_of_nodes_in_height-1)),
                        initial_temperature,
                        j + (number_of_nodes_in_height*i));
            }
        }


        for (int i = 0; i < number_of_nodes_in_width; i++) { //given co-ordinates to node{
            for (int j = 0; j < number_of_nodes_in_height; j++) {
            }
        }
        //-------------------------------------------------------------------------------
        // Elements


        List<Element> Elements_list = new ArrayList<Element>();
          //flag bounders to the boundary condition
          //0 - up, 1 - right, 2 - down, 3 - left

        for (int z=0;z<number_of_nodes_in_width-1;z++) {
            for (int x=0;x<number_of_nodes_in_height-1;x++){

                int[] whichBounder = new int[4];

                if(z == 0 && x == 0){
                    
                    whichBounder[0] = 1;
                    whichBounder[3] = 1;
                }
                else if(z == number_of_nodes_in_width - 2 && x == 0){
                    whichBounder[0]=1;
                    whichBounder[1]=1;
                }
                else if(z == 0 && x == number_of_nodes_in_height-2){
                    whichBounder[2]=1;
                    whichBounder[3]=1;
                }
                else if(z==number_of_nodes_in_width-2 && x == number_of_nodes_in_height-2){
                    whichBounder[2]=1;
                    whichBounder[1]=1;
                }
                else if(z==0){
                    whichBounder[3]=1;
                }
                else if(z==number_of_nodes_in_width-2){
                    whichBounder[1]=1;
                }
                else if(x==0){
                    whichBounder[0]=1;
                }
                else if(x==number_of_nodes_in_height-2){
                    whichBounder[2] = 1;
                }
                else
                    whichBounder[0] = 0;

                Element tmp = new Element(
                        nodes_list[(z)*number_of_nodes_in_height+x],
                        nodes_list[(z+1)*number_of_nodes_in_height+x],
                        nodes_list[(z+1)*number_of_nodes_in_height+x+1],
                        nodes_list[(z)*number_of_nodes_in_height+x+1],
                        (z*number_of_nodes_in_height)+x+1,
                        whichBounder,
                        conductitivity,
                        alfa
                        );
                Elements_list.add(tmp);

            }
        }

        //wydruk współrzędnych wszystkich elementów
        for(int i=0;i<Elements_list.size();i++){
            Elements_list.get(i).setId(i+1);
//            System.out.println("Id of element : " + Elements_list.get(i).getId());
//            Elements_list.get(i).show_elements();
        }

        Grid grid = new Grid(nodes_list,Elements_list,number_of_nodes_in_height,number_of_nodes_in_width);
        

//        -----------------------------------------------------------------------------------

       


        double[] temperatures_in_nodes = new double[nodes_list.length];
        System.out.println("\n");



        for(int i=0;i<nodes_list.length;i++){
            temperatures_in_nodes[i] = nodes_list[i].t;
        }

        for(int time=(int)simulation_step_time;time<=simulation_time;time+=simulation_step_time) {
            System.out.print("Simulation step : "+time+"\n");
            Global_matrix_H global_h = new Global_matrix_H(number_of_nodes_in_height,number_of_nodes_in_width);
            Global_matrix_H_condition global_cond_h = new Global_matrix_H_condition(number_of_nodes_in_height, number_of_nodes_in_width);
            Matrix_H local_h;
            Matrix_H_condition cond_h;
            Global_matrix_C global_c = new Global_matrix_C(number_of_nodes_in_height,number_of_nodes_in_width);
            Global_vector_P global_vector_p = new Global_vector_P(number_of_nodes_in_height,number_of_nodes_in_width);
            Global_matrix_C C_dt = new Global_matrix_C(number_of_nodes_in_height,number_of_nodes_in_width);
            double[] C_dt_multiple_by_t0 = new double[temperatures_in_nodes.length];

            for(int i=0;i<number_of_nodes_in_height*number_of_nodes_in_width;i++){
                global_vector_p.global_P[i] = 0;
                C_dt_multiple_by_t0[i] = 0;
                for(int j=0;j<number_of_nodes_in_height*number_of_nodes_in_width;j++){
                    global_h.global_H[i][j] = 0;
                    global_cond_h.getGlobal_H_condition()[i][j] = 0;
                    global_c.global_C[i][j] = 0;
                    C_dt.global_C[i][j] = 0;
                }
            }

            for (int i = 0; i < Elements_list.size(); i++) {

                local_h = new Matrix_H(Elements_list.get(i));
                cond_h = new Matrix_H_condition(Elements_list.get(i));
                local_h.calculate_matrix_H();
                cond_h.calculate_all_H_cond(); 
                local_h.add_two_matrix(cond_h.final_H_cond);
                global_h.calculateGlobalMatrix_H(Elements_list.get(i), local_h,global_cond_h);
            }



            Matrix_C local_c;
            for (int i = 0; i < Elements_list.size(); i++) {
                local_c = new Matrix_C(Elements_list.get(i), specific_heat, density);
                local_c.calculate_all_C();
                global_c.calculateGlobalMatrix_C(Elements_list.get(i), local_c);

            }


           
            for (int i = 0; i < number_of_nodes_in_height*number_of_nodes_in_width; i++) {
                for (int j = 0; j < number_of_nodes_in_height*number_of_nodes_in_width; j++) {
                    global_h.global_H[i][j] += (global_c.global_C[i][j] / simulation_step_time);
                    
                }
                
            }


           
            for(int i=0;i<nodes_list.length;i++){
                nodes_list[i].t = temperatures_in_nodes[i];
               
            }

            vector_P local_p;
            for (int i = 0; i < Elements_list.size(); i++) {
                local_p = new vector_P(Elements_list.get(i), ambient_temperature, alfa);
                local_p.calculate_vector_P();
              
                global_vector_p.calculate_Global_vector_P(Elements_list.get(i), local_p);
            }
   

            for (int i = 0; i < number_of_nodes_in_width * number_of_nodes_in_height; i++) {
                for (int j = 0; j < number_of_nodes_in_width * number_of_nodes_in_height; j++) {
                    C_dt.global_C[i][j] = (global_c.global_C[i][j] / simulation_step_time);
                }
            }


            for (int i = 0; i < nodes_list.length; i++) {
           
                for (int j = 0; j < nodes_list.length; j++) {
                    C_dt_multiple_by_t0[i] += (nodes_list[j].t * C_dt.global_C[i][j]);
                }
            }

            double[] tmp_global_vector_P = new double[number_of_nodes_in_height*number_of_nodes_in_width];
            double local_global=0;
           
            for (int i = 0; i < number_of_nodes_in_height*number_of_nodes_in_width; i++) {
              
                local_global = global_vector_p.global_P[i];
                tmp_global_vector_P[i]= C_dt_multiple_by_t0[i] - local_global;
                global_vector_p.global_P[i] = tmp_global_vector_P[i];
                
            }

            Gauss_elimination ge = new Gauss_elimination();
            temperatures_in_nodes = ge.solve(global_h.global_H,global_vector_p.global_P);
            System.out.println("\n\n");
        }
    }


}
