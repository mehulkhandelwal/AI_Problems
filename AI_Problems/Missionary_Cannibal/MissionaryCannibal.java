import java.util.*;

public class MissionaryCannibal {
    private static final Stack<State> st = new Stack<>();
    private static int solutionCounter = 0;

    static {
        State CM = new State(3, 3, true);
        st.push(CM);
    }

    public static void Solve() {
        boolean NextState;
        boolean isTarget;
        while (!st.empty()) {
            NextState = possibleNext();
            isTarget = isGoal();

            if (isTarget)
                printStack();

            if (! NextState || isTarget)
                backtrack();
        }
    }

    private static boolean isGoal() {
        State current = st.peek();

        return current.mis == 0 &&
                current.can == 0 &&
                ! current.boat;
    }

    private static void backtrack() {
        do State.ALL_STATES.remove(st.pop());
        while (! st.empty() &&
               ! st.peek().hasNextTransition());
    }

    private static boolean possibleNext() {
        State next, current = st.peek();

        while (current.hasNextTransition()) {
                next = State.fromCurrentAndTransition(current, current.nextTransition());
                if (next != null) {
                    st.push(next);
                    return true;
                }
        }

        return false;
    }

    private static void printStack() {
        StringBuilder sb = new StringBuilder(String.format("Start of solution %d%n", ++solutionCounter));
        int stepNumber = 0;
        for (State step : st) {
            sb.append(String.format("Step %d: %s%n", ++stepNumber, step));

        }
        sb.append("End of solution");
        System.out.println(sb);
    }

    private enum StateTransition {C, CC, M, MM, MC}

    private static class State {
        private static final Set<State> ALL_STATES = new HashSet<>();

        private int can, mis, indexOfNextStateTransition = 0;
        private boolean boat;

        private State(int can, int mis, boolean boat) {
            setCannibals(can);
            setMissionaries(mis);
            setBoat(boat);

            boolean canWillEatMissionaries = isCannibalsGreaterThanMissionariesOnAnySide();
            if (canWillEatMissionaries)
                throw new IllegalStateException("Cannibals will eat missionary");


            if(! ALL_STATES.add(this))
                throw new IllegalStateException(String.format("%s already exists", this));
        }

        public void setCannibals(int can) {
            if (can > 3 || can < 0)
                throw new IllegalArgumentException("The number of can must be within the range of 0-3");
            this.can = can;
        }

        public void setMissionaries(int mis) {
            if (mis > 3 || mis < 0)
                throw new IllegalArgumentException("The number of mis must be within the range of 0-3");
            this.mis = mis;
        }

        public void setBoat(boolean boat) {
            this.boat = boat;
        }

        private StateTransition nextTransition() {
            return StateTransition.values()[indexOfNextStateTransition++];
        }

        private boolean hasNextTransition() {
            return indexOfNextStateTransition < StateTransition.values().length;
        }

        private boolean isCannibalsGreaterThanMissionariesOnAnySide() {
            return (can > mis && mis != 0) ||
                    (can < mis && mis != 3);
        }

        @Override
        public String toString() {
            return String.format("State:{C: %d, M: %d, B: %b}", can, mis, boat);
        }

        @Override
        public int hashCode() {
            return Objects.hash(can, mis, boat);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof State) {
                State that = (State) obj;
                return can == that.can &&
                        mis == that.mis &&
                        boat == that.boat;
            }
            return false;
        }

        private static State fromCurrentAndTransition(State previous, StateTransition transition) {
            try {
                switch (transition) {
                    case C:
                        return new State(previous.can + (previous.boat ? -1 : 1),
                                previous.mis,
                                !previous.boat);

                    case CC:
                        return new State(previous.can + (previous.boat ? -2 : 2),
                                previous.mis,
                                !previous.boat);

                    case M:
                        return new State(previous.can,
                                previous.mis + (previous.boat ? -1 : 1),
                                !previous.boat);

                    case MM:
                        return new State(previous.can,
                                previous.mis + +(previous.boat ? -2 : 2),
                                !previous.boat);

                    case MC:
                        return new State(previous.can + (previous.boat ? -1 : 1),
                                previous.mis + (previous.boat ? -1 : 1),
                                !previous.boat);
                    default:
                        throw new IllegalArgumentException(
                                String.format("The transition must be one of the following:" +
                                        " %s, %s, %s, %s, or %s.", (Object[]) StateTransition.values()));
                }
            } catch (Exception ex){
                return null;
            }
        }
    }
    public static void main(String[] args) {
        MissionaryCannibal.Solve();
    }
}