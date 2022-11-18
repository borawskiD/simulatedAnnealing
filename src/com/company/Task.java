package com.company;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Task {
    public void run(){
        double min = 0;
        double max = 1000;
        double T = 0.95;
        double firstPoint = ThreadLocalRandom.current().nextDouble(min, max);
        double firstValue = F(firstPoint);
        Map<Double, Double> history = new HashMap<>();
        history.put(F(firstPoint), firstPoint);
        for (int i = 0; i<= 1000; i++){
            min = 0.95 * firstPoint;
            max = 1.05 * firstPoint;
            double secondPoint = ThreadLocalRandom.current().nextDouble(min, max);
            double secondValue = F(secondPoint);
            double delta = secondValue - firstValue;
            displayInfo(firstPoint,secondPoint, T);
            if (delta < 0) {
                firstPoint = secondPoint;
                history.put(F(firstPoint), firstPoint);
                System.out.println("Punkt jest lepszy na pierwszy rzut oka - podmieniam go bez sprawdzania innych warunkow");
            }
            else{
                double randomVariable = ThreadLocalRandom.current().nextDouble(0,1);
                System.out.println("Wygenerowana zmienna losowa: " + randomVariable);
                double exp =  Math.exp(((-1) * delta)/T);
                System.out.println("Obliczony exp(-delta / T): " + exp);
                if (exp == 0.0) System.out.println("Liczba przekracza dokladnosc zmiennych w javie :(");
                if (randomVariable < exp){
                    firstPoint = secondPoint;
                    history.put(F(firstPoint), firstPoint);
                    System.out.println("Zmienna losowa jest mniejsza niz " + Math.exp(((-1) * delta)/T) + " zatem punkt mimo bycia gorszym przechodzi");
                }
                else{
                    System.out.println("Zmienna losowa nie jest mniejsza niz wartosc exp(), zatem punkt nie przechodzi");
                }
            }
            T = changeT(T);
        }
        Set<Double> points = history.keySet();
        Optional<Double> x = points.stream().min(Double::compareTo);
        Double minimum = x.get();
        System.out.println("Minimalna wartosc: " + minimum + " dla x = " + history.get(minimum));
        Double maximum = points.stream().max(Double::compareTo).get();
        System.out.println("Maksymalna wartosc: " + maximum + " dla x = " + history.get(maximum));
    }
    private void displayInfo(double firstPoint, double secondPoint, double T){
        System.out.println();
        System.out.printf("Pierwsza wartosc: %f; f(x) =  %f \n", firstPoint, F(firstPoint));
        System.out.printf("Druga wartosc: %f; f(x) =  %f\n", secondPoint , F(secondPoint));
        System.out.printf("Delta: %f\n", F(secondPoint) - F(firstPoint));
        System.out.println("Temperatura: " + T);
    }
    private double changeT (double T){
        return 0.93 * T;
    }
    private double F(double x){
        return 2 * (Math.sin(Math.abs(x))) * (Math.sin(Math.abs(x))) + Math.cos(x);
    }
}
