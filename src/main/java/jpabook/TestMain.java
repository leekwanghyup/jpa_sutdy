package jpabook;

import lombok.AllArgsConstructor;
import lombok.ToString;

public class TestMain {
    public static void main(String[] args) {
        int a = 10;
        int b = a;
        b = 100; // b값을 변경하더라도 a값은 변경되지 않음
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        Position p1 = new Position(1,10);
        Position p2 = p1;

        // p2값 변경
        p2.x = 100;
        p2.y = 200;

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);

        // 래퍼클래스나 String 은 불변 객체이다.
        Integer num1 = 100;
        Integer num2 = num1; // num1와 num2는 동일한 객체를 참조

        // num2는 num1이 참조하는 객체와 참조를 끊고
        // 새로운 Integer 객체를 참조한다.
        num2 = 200;  // 새로운 Integer 객체 생성
    }
}

@ToString
@AllArgsConstructor
class  Position{
    int x;
    int y;
}
