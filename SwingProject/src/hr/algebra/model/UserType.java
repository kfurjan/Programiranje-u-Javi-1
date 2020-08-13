package hr.algebra.model;

/**
 * @author Kevin Furjan
 */
public enum UserType {

    Administrator(1),
    User(2);

    private final int userTypeId;

    UserType(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public static UserType fromId(int id) {
        for (UserType value : values()) {
            if (value.userTypeId == id) {
                return value;
            }
        }
        return null;
    }
}
