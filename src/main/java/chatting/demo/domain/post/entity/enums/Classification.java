package chatting.demo.domain.post.entity.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Classification {
    T_Shirt("티셔츠"),
    Hat("모자"),
    Pants("바지"),
    Shoo("신발"),
    Access("악세사리");

    private final String name;
    public String getName() {
        return name;
    }
}
