package com.example.demo;

public class Collision {
    private static boolean verticalCollision(int topA, int bottomA, int topB, int bottomB) {
        return (bottomA > topB && bottomA < bottomB) || (bottomB > topA && bottomB < bottomA);
    }

    private static boolean horizontalCollision(int leftA, int rightA, int leftB, int rightB) {
        return (rightA > leftB && rightA < rightB) || (rightB > leftA && rightB < rightA);
    }

    public static boolean isCollision(Entity entityA, Entity entityB) {
        int leftA = entityA.getX();
        int rightA = entityA.getX() + entityA.getWidth();
        int topA = entityA.getY();
        int bottomA = entityA.getY() + entityA.getHeight();

        int leftB = entityB.getX();
        int rightB = entityB.getX() + entityB.getWidth();
        int topB = entityB.getY();
        int bottomB = entityB.getY() + entityB.getHeight();

        return (verticalCollision(topA, bottomA, topB, bottomB)
                && horizontalCollision(leftA, rightA, leftB, rightB))
                || (verticalCollision(topA, bottomA, topB, bottomB) && leftA == leftB)
                || (horizontalCollision(leftA, rightA, leftB, rightB) && topA == topB)
                || isDuplicate(entityA, entityB);
    }

    public static boolean isDuplicate(Entity entityA, Entity entityB) {
        return entityA.getX() == entityB.getX() && entityB.getY() == entityA.getY();
    }

}
