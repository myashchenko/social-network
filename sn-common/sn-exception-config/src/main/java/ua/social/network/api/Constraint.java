package ua.social.network.api;

/**
 * @author Mykola Yashchenko
 */
public enum Constraint {
    FRIEND_REQUEST_FROM_TO("UC_FRIEND_REQUESTS_FROM_TO");

    private String constraintName;

    Constraint(final String constraintName) {
        this.constraintName = constraintName;
    }

    public static Constraint of(final String constraintName) {
        for (Constraint constraint : values()) {
            if (constraintName.contains(constraint.constraintName)) {
                return constraint;
            }
        }

        return null;
    }
}
