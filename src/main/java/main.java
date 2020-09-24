import game.Engine;
import solver.Solver;

import java.util.List;

public class main {

    public static void collectData(int numberOfEpisodes){
        double sum = 0;
        for (int i = 0; i < numberOfEpisodes; i++){
            Solver solver = new Solver(new Engine());
            String secret = solver.getRandomGuess(solver.initAllPossibleGuesses());
            solver = new Solver(new Engine(secret));
            sum += solver.solve().size();
        }
        sum /= numberOfEpisodes;
        System.out.println(sum);
    }

    public static void main(String[] args) {
        Solver tmp = new Solver(new Engine("1616", 6));
        double sum = 0;
        for (int i = 0; i < 10000; i++){
            sum += tmp.solve().size();
        }
        sum /= 10000;
        System.out.println(sum);

        //4.7506, 4.786, 4.5156, 4.7681, 4.7739, 3.8348, 4.5115
    }
}
