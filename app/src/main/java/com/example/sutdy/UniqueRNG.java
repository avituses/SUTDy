package com.example.sutdy;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueRNG {
    private Set<Integer> generatedNumbers;
    private Random random;

    public UniqueRNG() {
        this.random = new Random();
    }

    public int getNextNumber() {
        int number;
        number = 100000 + random.nextInt(900000);
        return number;

        }

}