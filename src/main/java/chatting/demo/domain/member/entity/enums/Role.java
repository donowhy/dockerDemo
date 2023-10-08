package chatting.demo.domain.member.entity.enums;

public enum Role {

    USER("유저", "USER"),
    ADMIN("관리자", "ADMIN"),

    SELLER("판매자", "SELLER");

    private final String key;
    private final String value;

    Role(String key, String value){
        this.key = key;
        this.value = value;
    }

    String getRole(String key){
        Role role = Role.valueOf(key);
        return role.key;
    }
}
