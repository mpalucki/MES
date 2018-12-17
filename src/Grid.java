import java.util.List;

public class Grid {
    private Node[] Nodes;
    private List<Element> Elements;
    private int number_of_nodes_in_height;
    private int number_of_nodes_in_width;

    Grid(Node[] given_N, List<Element> given_E,int given_height, int given_width){
        this.Nodes = given_N;
        this.Elements = given_E;
        this.number_of_nodes_in_height = given_height;
        this.number_of_nodes_in_width = given_width;
    }

    void show_grid(){
        for (int i = 0; i < number_of_nodes_in_height; i++)  //given co-ordinates to node
        {
            for (int j = 0; j < number_of_nodes_in_width; j++) {
                System.out.println("id : " + Nodes[j + (number_of_nodes_in_width * i)].id + "(" + Nodes[j +
                        (number_of_nodes_in_width * i)].x + "," + Nodes[j + (number_of_nodes_in_width * i)].y + ")");

            }
        }

        for(int tmp=0;tmp<Elements.size();tmp++) {
            System.out.println("nodes id in element " + (tmp+1) + " : " + Elements.get(tmp).first.id + " " + Elements.get(tmp).second.id + " "
                    + Elements.get(tmp).third.id + " " + Elements.get(tmp).fourth.id + " ");
        }
    }
}
