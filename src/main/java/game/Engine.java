package game;

import org.javatuples.Pair;

public class Engine {
    public String secret;
    public int secretLength;
    public int numberOfSymbols;

    public Engine(String secret, int numberOfSymbols){
        this.secret = secret;
        this.secretLength = secret.length();
        this.numberOfSymbols = numberOfSymbols;
    }

   public Engine(String secret){
        this.secret = secret;
        this.secretLength = secret.length();
        this.numberOfSymbols = 6;
   }

   public Engine(){
        this.numberOfSymbols = 6;
   }

   public void addSecret(String secret){
        this.secret = secret;
        this.secretLength = secret.length();

   }

    public boolean isCorrect(String guess){
        return guess.equals(secret);
    }

    //check correct character at correct place
    public int numberOfCorrectGuess(String guess, String secret) {
        if (guess.equalsIgnoreCase(secret)) {
            return secret.length();
        } else {
            int count = 0;
            for (int i = 0; i < guess.length(); i++) {
                if (guess.charAt(i) == secret.charAt(i)) {
                    count++;
                }
            }
            return count;
        }
    }
    //check correct character at wrong place
    public int numberOfCorrectCharacter(String guess, String secret) {
        int count = 0;
        for (int i = 0; i < guess.length(); i++){
            if (secret.contains(Character.toString(guess.charAt(i))) && secret.charAt(i) != guess.charAt(i)){
                count++;
            }
        }
        return count;
    }

    public Pair<Integer, Integer> answerQuery(String guess){
        return new Pair<>(numberOfCorrectGuess(guess, secret), numberOfCorrectCharacter(guess, secret));
    }

    public Pair<Integer, Integer> simulateQuery(String potentialAnswer, String guess){
        return new Pair<>(numberOfCorrectGuess(guess, potentialAnswer), numberOfCorrectCharacter(guess, potentialAnswer));
    }
}
