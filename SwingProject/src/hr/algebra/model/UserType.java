package hr.algebra.model;

import java.util.Optional;

/**
 *
 * @author Kevin Furjan
 */
public enum UserType {

    Administrator("Administrator"),
    User("User");

    private final String userType;

    private UserType(String userType) {
        this.userType = userType;
    }

    private static Optional<UserType> from(String name) {
        for (UserType value : values()) {
            if (value.userType.equals(name)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
