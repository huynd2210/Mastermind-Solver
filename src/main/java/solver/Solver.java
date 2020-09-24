package solver;

import game.Engine;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solver {

    public Engine engine;

    public Solver(Engine engine){
        this.engine = engine;
    }

    public String addOne(String guess){
        return addOne(guess, guess.length() - 1);
    }

    public String getLastPossibleGuess(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < engine.secretLength; i++){
            sb.append(engine.numberOfSymbols);
        }
        return sb.toString();
    }

    public String getFirstGuess(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < engine.secretLength; i++){
            sb.append('1');
        }
        return sb.toString();
    }

    public String addOne(String guess, int index) {
        StringBuilder sb = new StringBuilder(guess);
        String result;
        if (guess.equals(getLastPossibleGuess())){
            return getLastPossibleGuess();
        }

        if (sb.charAt(index) == Character.forDigit(engine.numberOfSymbols, 10)){
            sb.setCharAt(index, '1');
            return addOne(sb.toString(), index - 1);
        }else{
            int tmp = Character.getNumericValue(sb.charAt(index)) + 1;
            sb.setCharAt(index, Character.forDigit(tmp, 10));
            result = sb.toString();
            return result;
        }
    }

    public List<String> initAllPossibleGuesses() {
        String guess = getFirstGuess();
        List<String> possibleGuesses = new ArrayList<>();
        possibleGuesses.add(guess);

        for (int i = 0; i < Math.pow(engine.numberOfSymbols, engine.secretLength) - 1; i++) {
            guess = addOne(guess);
            possibleGuesses.add(guess);
        }
        return possibleGuesses;
    }

    public List<String> updateTable(String guess, List<String> potentialAnswer){
        Pair<Integer, Integer> result = this.engine.answerQuery(guess);
        potentialAnswer.removeIf(s -> !result.equals(this.engine.simulateQuery(s, guess)));
        return potentialAnswer;
    }

    public String getNextGuess(String mode, List<String> potentialAnswer){
        if (mode.equalsIgnoreCase("random")){
            return getRandomGuess(potentialAnswer);
        }else{
            return "";
        }
    }

    public String getRandomGuess(List<String> potentialAnswer){
        return potentialAnswer.get(getRandomNumberInRange(0, potentialAnswer.size() - 1));
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public List<String> solve(){
        List<String> guessesList = new ArrayList<>();
        List<String> potentialAnswer = initAllPossibleGuesses();
        boolean correct = false;
        while (!correct){
            String guess = getNextGuess("random", potentialAnswer);
            guessesList.add(guess);
            correct = this.engine.isCorrect(guess);
            updateTable(guess, potentialAnswer);
        }
        return guessesList;
    }

    public void solveWithDetails(){
        List<String> guessesList = new ArrayList<>();
        List<String> potentialAnswer = initAllPossibleGuesses();
        boolean correct = false;
        while (!correct){
            String guess = getNextGuess("random", potentialAnswer);
            System.out.println("Guess is: " + guess);
            guessesList.add(guess);
            correct = this.engine.isCorrect(guess);
            updateTable(guess, potentialAnswer);
            System.out.println("Remaining Possible Options");
            System.out.println(potentialAnswer);
        }
    }

}
