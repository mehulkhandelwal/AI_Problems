import java.util.*;
class State {
    int x, y;
    State(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
public class WaterJug {
    public static Stack < State > stack = new Stack < State > ();
    public static ArrayList < State > visited = new ArrayList < State > ();
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter jug1 max capacity: ");
        int jug1=sc.nextInt();
        System.out.println("Enter jug2 max capacity: ");
        int jug2=sc.nextInt();
        System.out.println("Enter target for jug1: ");
        int target1=sc.nextInt();
        System.out.println("Enter target for jug2: ");
        int target2=sc.nextInt();
        State startState = new State(0, 0);

        stack.push(startState);

        while (!stack.empty()) {

            State s = stack.pop();

            System.out.print("--> (" + s.x + "," + s.y + ")");

            if (s.x == target1 && s.y==target2) break;

            generateStates(s,jug1,jug2);

        }
    }
    public static void generateStates(State s, int jug1, int jug2) {
        if (s.x < jug1) {
            pushState(new State(jug1, s.y));
        }
        if (s.y < jug2) {
            pushState(new State(s.x, jug2));
        }
        if (s.x > 0) {
            pushState(new State(0, s.y));
        }
        if (s.y > 0) {
            pushState(new State(s.x, 0));
        }
        if (0 < (s.x + s.y) && (s.x + s.y) >= jug1 && s.y > 0) {
            pushState(new State(jug1, s.y - (jug1 - s.x)));
        }
        if (0 < (s.x + s.y) && (s.x + s.y) >= jug2 && s.x > 0) {
            pushState(new State(s.x - (jug2 - s.y), jug2));
        }
        if (0 < (s.x + s.y) && (s.x + s.y) <= jug1 && s.y >= 0) {
            pushState(new State(s.x + s.y, 0));
        }
        if (0 < (s.x + s.y) && (s.x + s.y) <= jug2 && s.x >= 0) {
            pushState(new State(0, s.x + s.y));
        }
    }
    public static void pushState(State s) {
        if (!checkIfVisited(s)) {
            stack.push(s);
            visited.add(s);
        }
    }
    public static boolean checkIfVisited(State s) {
        for (State st: visited) {
            if (st.x == s.x && st.y == s.y) return true;
        }
        return false;
    }
}
