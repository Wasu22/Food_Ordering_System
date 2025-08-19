package com.cdac.entities;


public enum Rating {
    POOR(1),
    AVERAGE(2),
    GOOD(3),
    VERY_GOOD(4),
    EXCELLENT(5);

    private final int stars;

    Rating(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }
}