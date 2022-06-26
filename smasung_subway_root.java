import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class samsung_subway_root {
    static int N, M;
    static boolean[] visitedLine;
    static boolean[] visitedStation;
    static ArrayList<Integer>[] stations;
    static ArrayList<Integer>[] lines;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


            N = Integer.parseInt(st.nextToken());  // 지하철 역의 수
            M = Integer.parseInt(st.nextToken());  // 지하철 노선의 수

            int start = Integer.parseInt(st.nextToken());  // 출발하는 정거장
            int end = Integer.parseInt(st.nextToken());    // 도착할 정거장
            visitedLine = new boolean[M + 1];
            visitedStation = new boolean[N + 1];
            stations = new ArrayList[N + 1];
            lines = new ArrayList[N + 1];
            for (int i = 1; i < N + 1; i++) {
                stations[i] = new ArrayList<>();
                lines[i] = new ArrayList<>();
            }

            String[] abc = br.readLine().split(" ");
            for (int i = 1; i <= M; i++) { //M개의 줄에 걸쳐 지하철 정거장 번호 입력
                String[] s = br.readLine().split(" ");
                for (String station : s) {
                    int statN = Integer.parseInt(station);
                    stations[statN].add(i);
                    lines[i].add(statN);

                }

                int answer = go(start, end);
                System.out.println(answer);

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
        return -1;  //출발역에서 도착역까지 갈 수 없을 때
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