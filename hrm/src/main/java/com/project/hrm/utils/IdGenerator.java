package com.project.hrm.utils;

import java.util.UUID;

public class IdGenerator {
    public static Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
