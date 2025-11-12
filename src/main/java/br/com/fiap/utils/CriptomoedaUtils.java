package br.com.fiap.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class CriptomoedaUtils {
    private static final Map<Integer, Double> lastPrice = new ConcurrentHashMap<>();

    private static final double MAX_STEP_PCT = 0.05;
    private static final double MIN_PRICE = 0.01;
    private static final double SEED_MIN = 1.0;
    private static final double SEED_MAX = 500.0;

    private CriptomoedaUtils() {}

    public static double nextPrice(int cryptoId) {

        return lastPrice.compute(cryptoId, (id, prev) -> {
            double novo;
            if (prev == null || prev <= 0.0) {
                novo = ThreadLocalRandom.current().nextDouble(SEED_MIN, SEED_MAX);
            } else {
                double pct = ThreadLocalRandom.current().nextDouble(-MAX_STEP_PCT, MAX_STEP_PCT);
                novo = prev * (1.0 + pct);
            }
            novo = Math.max(MIN_PRICE, round8(novo));
            return novo;
        });
    }

    public static double peekLastPrice(int cryptoId) {
        return lastPrice.get(cryptoId);
    }

    public static void resetCrypto(int cryptoId) {
        lastPrice.remove(cryptoId);
    }

    private static double round8(double v) {
        return Math.round(v * 1e8) / 1e8;
    }


}
