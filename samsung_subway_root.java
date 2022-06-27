import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class subway_root{
    static int N, M, T;
    static boolean[] visitedLine;
    static boolean[] visitedStation;
    static ArrayList<Integer>[] stations;
    static ArrayList<Integer>[] lines;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            visitedLine = new boolean[M + 1];
            visitedStation = new boolean[N + 1];
            stations = new ArrayList[N + 1];
            lines = new ArrayList[N + 1];
            for (int i = 1; i < N + 1; i++) {
                stations[i] = new ArrayList<>();
                lines[i] = new ArrayList<>();
            }

            String[] abc = br.readLine().split(" ");
            for (int i = 1; i <= M; i++) {
                String[] s = br.readLine().split(" ");
                for (String station : s) {
                    int statN = Integer.parseInt(station);
                    stations[statN].add(i);
                    lines[i].add(statN);

                }
            }


            int answer = go(start, end);


            System.out.println("#" + t + " " + answer);

        }
    }


    private static int go(int start, int end) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.transCount));
        visitedStation[start] = true;
        for (int line : stations[start]) {
            pq.add(new Node(line, start, 0));
            visitedLine[line] = true;
        }

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (now.curStation == end) {
                return now.transCount;
            }

            for (int nextStation : lines[now.curLine]) {

                if (!visitedStation[nextStation]) {
                    visitedStation[nextStation] = true;
                    pq.add(new Node(now.curLine, nextStation, now.transCount));

                    for (int nextLine : stations[nextStation]) {
                        if (!visitedLine[nextLine]) {
                            visitedLine[nextLine] = true;
                            pq.add(new Node(nextLine, nextStation, now.transCount + 1));
                        }
                    }
                }
            }
        }
        return -1;
    }

    private static class Node {
        int curLine;
        int curStation;
        int transCount;

        public Node(int curLine, int curStation, int transCount) {
            this.curLine = curLine;
            this.curStation = curStation;
            this.transCount = transCount;
        }
    }
}