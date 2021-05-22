package com.zzb.demo;

import org.junit.Test;
import java.util.Optional;

public class demo05_OptionalClass {
    public static void main(String[] args) {

    }

    @Test
    public void test01() {
        Optional<Man> optionalMan = Optional.ofNullable(getMan(false));
        System.out.println(optionalMan.orElse(new Man()));
        System.out.println(optionalMan.orElse(new Man()).getSalary());
    }

    static Man getMan(boolean flag) {
        if (flag) return new Man("imoder", 18, true, 12.00);
        return null;
    }
}

