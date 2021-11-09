import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

class Constants {
    static int LEFT = 0; 
    static int RIGHT = 1; 
    static boolean visited = true; 
    static boolean notVisited = false; 
}

class State {

    int parentState;
    int MissionaryOnLeft;
    int MissionaryOnRight;
    int CannibalOnLeft;
    int CannibalOnRight;
    int side;
    

    public State(int MissionaryOnLeft, int CannibalOnLeft, int MissionaryOnRight, int CannibalOnRight,
            int boatCapacity, int side) {

        this.MissionaryOnLeft = MissionaryOnLeft;
        this.CannibalOnLeft = CannibalOnLeft;

        this.MissionaryOnRight = MissionaryOnRight;
        this.CannibalOnRight = CannibalOnRight;

        this.side = side;
    }

    public boolean isThisAValidState() {
        
        if (MissionaryOnLeft >= 0 && CannibalOnLeft >= 0 && MissionaryOnRight >= 0 && CannibalOnRight >= 0
                && (MissionaryOnLeft == 0 || MissionaryOnLeft >= CannibalOnLeft)
                && (MissionaryOnRight == 0 || MissionaryOnRight >= CannibalOnRight)) {
            return true;
        }
        return false;
    }

    public boolean isItTheGoalState() {
        if (MissionaryOnLeft == 0 && CannibalOnLeft == 0) {
            return true;
        }
        return false;
    }
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) {
            return false;
        }

        State s = (State) obj;
        return (s.CannibalOnLeft == CannibalOnLeft && s.MissionaryOnLeft == MissionaryOnLeft
                && s.side == side && s.CannibalOnRight == CannibalOnRight
                && s.MissionaryOnRight == MissionaryOnRight);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + this.MissionaryOnLeft;
        hash = 31 * hash + this.MissionaryOnRight;
        hash = 31 * hash + this.CannibalOnLeft;
        hash = 31 * hash + this.CannibalOnRight;
        hash = 31 * hash + this.side;
        return hash;
    }

    public List<State> getSuccessors() {
        List<State> successors = new ArrayList<>();
        generateSuccessors(successors);
        return successors;
    }

    public void generateSuccessors(List<State> successors) {
        if (side == Constants.LEFT) {
            for (int i = 0; i <= MissionaryOnLeft; i++) {
                for (int j = 0; j <= CannibalOnLeft; j++) {
                    if ((i + j) != 0 && ((i + j) <= 2) && (i == 0 || i >= j)) {
                        State tem = new State(MissionaryOnLeft - i, CannibalOnLeft - j, MissionaryOnRight + i,
                                CannibalOnRight + j, 2, Constants.RIGHT);
                        if (tem.isThisAValidState()) {
                            successors.add(tem);
                        }
                    }
                }
            }
        } else if (side == Constants.RIGHT) {
            for (int i = 0; i <= MissionaryOnRight; i++) {
                for (int j = 0; j <= CannibalOnRight; j++) {

                    if ((i + j) != 0 && ((i + j) <= 2)) {
                        State tem = new State(MissionaryOnLeft + i, CannibalOnLeft + j, MissionaryOnRight - i,
                                CannibalOnRight - j, 2, Constants.LEFT);

                        if (tem.isThisAValidState()) {
                            successors.add(tem);
                        }
                    }
                }
            }
        }
    }
    public String toString() {
        if (side == Constants.LEFT) {
            return "(" + MissionaryOnLeft + "," + CannibalOnLeft + ",Left,"
                    + MissionaryOnRight + "," + CannibalOnRight + ")";
        } else {
            return "(" + MissionaryOnLeft + "," + CannibalOnLeft + ",Right,"
                    + MissionaryOnRight + "," + CannibalOnRight + ")";
        }
    }

    public void printAll(List<State> l) {
        for (State s : l) {
            System.out.println(s);
        }
    }

    public int getParentState() {
        return parentState;
    }

    public void setParentState(int parentState) {
        this.parentState = parentState;
    }

    public int getMissionaryOnLeft() {
        return MissionaryOnLeft;
    }

    public void setMissionaryOnLeft(int MissionaryOnLeft) {
        this.MissionaryOnLeft = MissionaryOnLeft;
    }

    public int getMissionaryOnRight() {
        return MissionaryOnRight;
    }

    public void setMissionaryOnRight(int MissionaryOnRight) {
        this.MissionaryOnRight = MissionaryOnRight;
    }

    public int getCannibalOnLeft() {
        return CannibalOnLeft;
    }

    public void setCannibalOnLeft(int CannibalOnLeft) {
        this.CannibalOnLeft = CannibalOnLeft;
    }

    public int getCannibalOnRight() {
        return CannibalOnRight;
    }

    public void setCannibalOnRight(int CannibalOnRight) {
        this.CannibalOnRight = CannibalOnRight;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }
}

class DFS {
    int nodeNo;
    State[] stateArray;
    State initialState;

    public DFS(State initialState) {
        this.initialState = initialState;
        stateArray = new State[100001];
        nodeNo = 0;
    }
    public State getFinalState() {
        if (initialState.isItTheGoalState()) {
            return initialState;
        }
        nodeNo = 0;
        Stack<State> stack = new Stack<>();
        HashMap<State, Integer> map = new HashMap<>();
        map.put(initialState, nodeNo);
        initialState.setParentState(-1);
        stateArray[nodeNo] = initialState;
        stack.push(initialState);
        while (!stack.isEmpty()) {
            State u = stack.pop();
            int indexU = map.get(u);

            List<State> successors = u.getSuccessors();

            for (State v : successors) {
                if (!map.containsKey(v)) {
                    nodeNo++;
                    v.setParentState(indexU);
                    stateArray[nodeNo] = v;

                    if (v.isItTheGoalState()) {
                        return v;
                    }

                    map.put(v, nodeNo);
                    stack.push(v);
                }
            }
        }

        return null;
    }

    public void printPath() {
        int t = 0;
        State s = getFinalState();
        if (s == null) {
            System.out.println("No solution found.");
            return;
        }

        String[] str = new String[100001];

        while (!s.equals(initialState)) {
            str[t] = s.toString();
            t++;
            s = stateArray[s.getParentState()];
        }
        str[t] = s.toString();

        for (int i = t; i >= 0; i--) {
            System.out.print(str[i]);
            if (i != 0) {
                System.out.print(" --> ");
            }
        }
        System.out.println("");
    }
}

public class Main1 {

    private static int initialMissionaryOnLeft;
    private static int initialCannibalOnLeft;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter initial number of Missionaries on the left bank: ");
        initialMissionaryOnLeft = sc.nextInt();
        System.out.println("Enter initial number of Cannibals on the left bank: ");
        initialCannibalOnLeft = sc.nextInt();
        State initialState = new State(initialMissionaryOnLeft, initialCannibalOnLeft, 0, 0, 2, Constants.LEFT);
        initialState.setParentState(-1);
        DFS dfs = new DFS(initialState);
        dfs.printPath();
    }
}