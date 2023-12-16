package RatMaze;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DFS_Class {
    // Here we define what we will need in our DFS (our graph, its size and the travered array)
    // We also define what we will need when it comes to multithreading (like the MAX # of threads in parellel)

    private char[][] graph;
    private int N;
    private boolean[][] traversed;
    private int MAX_THREADS = 1024;
    private boolean found = false;
    private ArrayList<Pair> Answer;

    public DFS_Class(char[][] indexes, int dimensions) {
        graph = indexes;
        this.N = dimensions;
        this.traversed = new boolean[N][N];
        ArrayList<Pair> Arr = new ArrayList<>();
        DFS(0, 0, Arr);

        // A loop is used here to make sure that we travered every possible route before we move to the answer
        while (Thread.activeCount() > 3) {

        }

        if (found) {
            for (Pair pair : Answer) {
                //System.out.print(Answer.get(i).First + " " + Answer.get(i).Second + "\n");
                graph[pair.First][pair.Second] = '2';
            }
            graph[0][0] = '2';
            getAnswer();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++)
                    System.out.print(graph[i][j]);
                System.out.println("\n");
            }

        } else {
            System.out.println("Not found!");
        }
    }

    private static class Pair {

        public int First;
        public int Second;

        Pair(int x, int y) {
            First = x;
            Second = y;
        }
    }


    // The class that gets threaded, it creates a thread for every path possible until one of the condtions are met
    // Possiblities are: 1- a deadend is reached, we end the path / 2- we reached the maximum # of threads, we use normal DFS
    // 3- We found a path, we inform the main class and safely end other threads

    private class realDFS implements Runnable {

        int x, y;
        ArrayList<Pair> ans;

        realDFS(int x, int y, ArrayList<Pair> ans) {
            this.x = x;
            this.y = y;
            this.ans = ans;
        }

        public void run() {
            try {
                DFS(x, y, ans);
            } catch (InterruptedException ex) {
                Logger.getLogger(DFS_Class.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void DFS(int x, int y, ArrayList<Pair> ans) throws InterruptedException {
            if (found) {
                return;
            }
            if (graph[x][y] == '2') {
                Answer = ans;
                found = true;
                return;
            }
            if (!(x + 1 == N) && !(traversed[x + 1][y]) && !(graph[x + 1][y] == '0')) {

                ArrayList<Pair> ans1 = new ArrayList<>(ans);
                ans1.add(new Pair(x + 1, y));

                traversed[x + 1][y] = true;
                if (Thread.activeCount() < MAX_THREADS) {
                    realDFS g1 = new realDFS(x + 1, y, ans1);
                    Thread t1 = new Thread(g1);
                    t1.start();
                } else {
                    DFS(x + 1, y, ans1);
                }
            }
            if (!(y + 1 == N) && !(traversed[x][y + 1]) && !(graph[x][y + 1] == '0')) {

                ArrayList<Pair> ans2 = new ArrayList<>(ans);

                ans2.add(new Pair(x, y + 1));
                traversed[x][y + 1] = true;
                if (Thread.activeCount() < MAX_THREADS) {
                    realDFS g1 = new realDFS(x, y + 1, ans2);
                    Thread t1 = new Thread(g1);
                    t1.start();

                } else {
                    DFS(x, y + 1, ans2);
                }
            }

        }
    }

    // Our init DFS method, invoked only once in most cases when MAXTHREAD's value is more than 2

    private void DFS(int x, int y, ArrayList<Pair> ans) {
        if (found) {
            return;
        }
        if (graph[x][y] == '2') {
            Answer = ans;
            found = true;
            return;
        }
        if (!(x + 1 == N) && !(traversed[x + 1][y]) && !(graph[x + 1][y] == '0')) {

            ArrayList<Pair> ans1 = new ArrayList<>(ans);
            ans1.add(new Pair(x + 1, y));

            traversed[x + 1][y] = true;
            if (Thread.activeCount() < MAX_THREADS) {
                realDFS g1 = new realDFS(x + 1, y, ans1);
                Thread t1 = new Thread(g1);

                t1.start();

            } else {
                DFS(x + 1, y, ans1);
            }
        }
        if (!(y + 1 == N) && !(traversed[x][y + 1]) && !(graph[x][y + 1] == '0')) {
            ArrayList<Pair> ans1 = new ArrayList<>(ans);
            ans1.add(new Pair(x, y + 1));
            traversed[x][y + 1] = true;
            if (Thread.activeCount() < MAX_THREADS) {
                realDFS g1 = new realDFS(x, y + 1, ans1);
                Thread t1 = new Thread(g1);
                t1.start();

            } else {
                DFS(x, y + 1, ans1);
            }
        }
    }

    public char[][] getAnswer() {
        while (Thread.activeCount() > 3) {

        }

        // Check var found first before calling the function
        return graph;
    }
    public boolean isFound()
    {
        return found;
    }
}