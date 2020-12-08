

import java.util.*;
import java.lang.*;

class BipartiteMatchings
{
    // M = |X| and N = |Y|

    static int M = 0;
    static int N = 0;

    public static void main (String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("What is |X|?");
        M = Integer.parseInt(scan.nextLine()); //get cardinality of partite set X

        System.out.println("What is |Y|?");
        N = Integer.parseInt(scan.nextLine()); //get cardinality of partite set X

        boolean G[][] = new boolean[M][N];

        for (int i = 0; i < M ; i++) { //label vertices in X: u_1,...u_m
            for (int j = 0; j < N; j++) { //label vertices in Y: v_1,...,v_n

                try {
                    System.out.printf("Is u_%d∈X adjacent to v_%d∈Y (1 for Yes, 0 for No)  %n", i, j); // Finds the pairs of vertices in X and Y which are adjacent
                    G[i][j] = Integer.parseInt(scan.nextLine()) == 1;
                } catch (NumberFormatException e) {
                    System.out.println("Entered a non-integer value, try again."); //Try again if enter string instead of integer
                    j--;
                    continue;
                }

            }
        }

        System.out.println("Resulting |X| by |Y| Adjacency Matrix\n_____________________________________");
        for (int i = -1; i < M; i++) {

            if(i != -1)
                System.out.printf("\tu%d\t", i); // labels for vertices in X
            else
                System.out.print("\n\t\t");

            for (int j = 0; j < N; j++) {
                if (i != -1) {

                    // if vertices are adjacent, matrix entry is 1, otherwise 0
                    int isAdj = G[i][j] ? 1 : 0;
                    System.out.printf("%d\t", isAdj);

                }else
                    System.out.printf("v%d\t", j); // labels for vertices in Y
            }

            System.out.print("\n\n");
        }

        System.out.println( "\n|M| = "+ maximumMatching(G));
    }

    // Returns the size of the maximum matching from X to Y and prints the edges in the matching

    static int maximumMatching(boolean G[][]) {
        /*
         * This array keeps track of the edges in the matching (i.e matching[] = M)
         * The value of matching[i] is the endpoint in X
         * and i is the value of its partner in the matching which is in Y
         *
         * If matching[i] = -1, then the ith vertex in Y is not in the matching
         *
         * */

        int matching[] = new int[N];

        // Initially the matching is empty
        for(int i = 0; i < N; ++i)
            matching[i] = -1;

        // Tracks |M| as we iterate through the algorithm
        int matchingSize = 0;

        for (int x = 0; x < M; x++) {

            /*
             * Each vertex in X starts unmarked
             * Hence each vertex, x, in X starts with each of the vertices in Y as unmarked since x hasn't been explored
             *
             */

            /*
             * Mark every vertices in Y as not marked
             * for each vertex in X since each vertex is unmarked to start.
             *
             */

            boolean marked[] =new boolean[N] ;
            for(int i = 0; i < N; ++i)
                marked[i] = false;

            // Find if there's an augmenting path with 'x'
            if (augmentingPath(G, x, marked, matching))
                matchingSize++;
        }

        System.out.println("Resulting Matching \n___________________________________________\n");
        for (int i = 0; i < matching.length; i++) {
            if (matching[i] != -1)
                System.out.printf("Edge: {v%d, u%d} \n", i, matching[i]);
        }
        return matchingSize;
    }


    static boolean augmentingPath(boolean G[][], int x, boolean marked[], int matching[])
    {

        for (int y = 0; y < N; y++)
        {
            /*
            * Try every y in N(x)
            * If vertex x is adjacent to y and y is not visited
            *
            * */
            if (G[x][y] && !marked[y])
            {
                // Mark y as visited
                marked[y] = true;
                int w = matching[y]; //Let w be the vertex matched to y (if w < 0, then y is unsaturated in the matching)

                /* If vertex 'y' is unsaturated (i.e w < 0) we can augment matching by adding the edge joining 'x' and 'y'
                * OR in the case where 'y' is saturated, if adding 'w' to S and running the iteration step on 'w'
                * finds a match for 'w', then there exist an augmenting path with the edge joining 'x' and 'y' in it so we can refactor the matching
                * by adding the edge joining 'x' and 'y' and the edges found by finding a match for 'w'
                *
                * Since 'y' is marked as visited in the
                * 'w' in the following recursive call will not be matched with 'y' again
                 */

                if (w < 0 || augmentingPath(G, w, marked, matching)) {
                    matching[y] = x;
                    return true;
                }
            }
        }
        return false;
    }



}
