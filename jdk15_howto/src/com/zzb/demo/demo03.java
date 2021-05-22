package com.zzb.demo;

public class demo03 {
    public static void main(String[] args) {

    }
}

sealed class Person permits Student, Worker, Teachter {
}

sealed class Student extends Person permits MidStudent, HighStudent {
}

final class MidStudent extends Student {
}

final class HighStudent extends Student {
}

non-sealed class Worker extends Person {
}

final class Teachter extends Person {
}
