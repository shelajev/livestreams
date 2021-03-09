package primes.web;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.*;

import java.util.stream.*;


import java.util.*;

@Controller("/primes")
public class PrimesController {

    private Random r = new Random();

    @Get("/random/{upperbound}")
    public List<Long> random(int upperbound) {
        int to = 2 + r.nextInt(upperbound - 2);
        int from = 1 + r.nextInt(to - 1);

        return primeSequence(from, to);
    }

    @Get("/{from}/{to}")
    public List<Long> primes(@PathVariable int from, @PathVariable int to) {
        return primeSequence(from, to);
    }

    public static boolean isPrime(long n) {
    return LongStream.rangeClosed(2, (long) Math.sqrt(n))
            .allMatch(i -> n % i != 0);
    }

    public static List<Long> primeSequence(long min, long max) {
    return LongStream.range(min, max)
            .filter(PrimesController::isPrime)
            .boxed()
            .collect(Collectors.toList());
    }

}
