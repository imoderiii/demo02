package com.zzb.deno;

import java.util.Objects;

public class demo01 {
    public static void main(String[] args) {
        // System.out.println(getObjLen("11"));
        // System.out.println(beforeSth("iii"));

        String str = """
                div
                   ul
                       li
                       li
                   ul
                div
                """;
        System.out.println(str.length());


    }

    static int getObjLen(Object o) {
        if (o instanceof String tmp) {
            return tmp.length();
        }
        return 0;
    }

    static int beforeSth(Object o) {
        if (o instanceof String str && str.length() >= 7) {
            return str.length();
        }
        return 0;
    }
}

class B {
    private String name;
    private int age;

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        B b = (B) o;
        return age == b.age && Objects.equals(name, b.name);
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof B b && age == b.age && Objects.equals(this.name, this.name);
    }

}

