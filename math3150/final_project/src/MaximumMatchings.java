import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.*;


public class MaximumMatchings {

    public static void fillGraph(int n, int m, MutableGraph<Integer> graph){

        //Note: |X| = n, |Y| = m

        Scanner scan = new Scanner(System.in);

        for (int i = 1; i <= n ; i++) { //label vertices in X: v_1,...v_n
            for (int j = 1; j <= m; j++) { //label vertices in Y: v_(n+1),...,v_(n+m)
                int isAdjacent = 0;

                try {
                    System.out.printf("Is v_%d∈X adjacent to v_%d∈Y (1 for Yes, 0 for No)  %n", i, n + j); // Finds the pairs of vertices in X and Y which are adjacent
                    isAdjacent = Integer.parseInt(scan.nextLine());
                }catch (NumberFormatException e){
                    System.out.println("Entered a non-integer value, try again."); //Try again if enter string instead of integer
                    j--;
                    continue;
                }

                if (isAdjacent == 1) {
                    graph.putEdge(i, n + j); //If the vertices are adjacent, put an edge in the graph connecting them
                }else if(isAdjacent != 1 && isAdjacent != 0){
                    System.out.println("Enter a non-binary integer value, try again."); //Try again if enter an integer which is not 0 or 1
                    j--;
                    continue;
                }
            }
        }
    }

    public static void displayAdjMatrix(int n, int m, MutableGraph<Integer> G){
        //Note: |X| = n, |Y| = m

        for (int i = 0; i <= n; i++) {

            if(i != 0)
                System.out.printf("\tv%d\t", i); // labels for vertices in X
            else
                System.out.print("\n\t\t");

            for (int j = 1; j <= m; j++) {
                if (i != 0) {

                    // if vertices are adjacent, matrix entry is 1, otherwise 0
                    int isAdj = G.hasEdgeConnecting(i, n + j) ? 1 : 0;
                    System.out.printf("%d\t", isAdj);


                }else
                    System.out.printf("v%d\t", n+j); // labels for vertices in Y
            }

            System.out.print("\n");
        }
    }
    public static void fillPartiteSet(List<Integer> A, int card_A, int offset){
        for (int i = 1; i <= card_A; i++) {
            A.add(offset+i);
        }
    }

    public static void getInitialMatching(List<EndpointPair<Integer>> M, List<EndpointPair<Integer>> E, List<Integer> U){
        Scanner scan = new Scanner(System.in);
        int node_X, node_Y, card_M = 0;

        System.out.println("\n\n\tSelect a Matching:");

        System.out.println("What is the size of the matching?"); // Gets the value for |M|
        card_M = Integer.parseInt(scan.nextLine());

        for (int i = 0; i < card_M; i++) {
            System.out.println("Input an edge in the matching (X vertex #, Y vertex #)");
            String[] matching = scan.nextLine().split(",");

            // {node_X, node_Y} is an edge in the initial matching where node_X is in X and node_Y is in Y
            node_X = Integer.parseInt(matching[0].trim());
            node_Y = Integer.parseInt(matching[1].trim());
            U.remove(Integer.valueOf(node_X));

            for (int j = 0; j < E.size(); j++) {
                if (E.get(j).nodeU().equals(node_X) && E.get(j).nodeV().equals(node_Y)
                        || E.get(j).nodeU().equals(node_Y) && E.get(j).nodeV().equals(node_X))
                    M.add(E.get(j));
            }
        }

        for (int i = 0; i < M.size(); i++) {
            System.out.println(M.get(i));
        }

    }
    public static boolean AugmentingPathAlgorithm(){

        return false;
    }


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        MutableGraph<Integer> G = GraphBuilder.undirected().build(); // Let G be a simple undirected X-Y bigraph

        //Intialize to 0

        int card_X = 0; // |X|
        int card_Y = 0; // |Y|


        List<Integer> X = new ArrayList<>(); //partite set X
        List<Integer> Y = new ArrayList<>(); //partite set Y
        List<Integer> S; //Let S be a subset of X
        List<Integer> T; //Let T be a subset of Y
        List<Integer> U; //Let U be the set of M-unsaturated vertices in X
        List<Integer> marked = new ArrayList<>(); // This is the set of marked vertices of S that have been explored for path extension
        List<EndpointPair<Integer>> E = new ArrayList<>(); // Let E be the edge set of G
        List<EndpointPair<Integer>> M = new ArrayList<>(); // Let M be the initial matching

        System.out.println("What is |X|?");
        card_X = Integer.parseInt(scan.nextLine()); //get cardinality of partite set X

        System.out.println("What is |Y|?");
        card_Y = Integer.parseInt(scan.nextLine()); //get cardinality of partite set X

        fillGraph(card_X, card_Y, G);         // Fills the Graph G with correct vertices and edges
        fillPartiteSet(X, card_X, 0);  // Fills the partite set X from values 1 to |X|
        fillPartiteSet(Y, card_Y, card_X);   //  Fills the partite set Y from values |X|+1 to |X|+|Y|

        System.out.println("Resulting |X| by |Y| Adjacency Matrix\n_____________________________________");
        displayAdjMatrix(card_X, card_Y, G);

        for (EndpointPair<Integer> edge : G.edges()) { // Fills the edge set of G
            E.add(edge);
        }

        U=X;
        getInitialMatching(M, E, U); //gets the initial matching and fills U with the unsaturated vertices in X (by removing all the saturated vertices)

        //Initialization Step
        S = U;
        T = new ArrayList<>();

        for(Integer x : S){
            Set<Integer> neighbours = G.adjacentNodes(x.intValue());
            ArrayList<Integer> neighboursNotInMatching = new ArrayList<>();

            for (Integer neighbour : neighbours){
                for (EndpointPair<Integer> edge: M){
                    if(!(edge.nodeU().equals(neighbour) && edge.nodeV().equals(x) || edge.nodeU().equals(x) && edge.nodeV().equals(neighbour)))
                        neighboursNotInMatching.add(neighbour);
                }
            }




        }
    }

}

