package com.project.hrm.redis;

public class RedisKeys {
    private RedisKeys() {}

    // Training Program Keys
    public static final String TRAINING_PROGRAM_PREFIX = "training:program:";
    public static final String TRAINING_PROGRAMS_LIST = "training:programs:list";
    public static final String TRAINING_PROGRAM_SESSIONS = "training:program:sessions:";

    // Training Session Keys
    public static final String TRAINING_SESSION_PREFIX = "training:session:";
    public static final String TRAINING_SESSIONS_LIST = "training:sessions:list";

    public static String trainingProgramKey(Integer programId) {
        return TRAINING_PROGRAM_PREFIX + programId;
    }

    public static String trainingSessionKey(Integer sessionId) {
        return TRAINING_SESSION_PREFIX + sessionId;
    }

    public static String programSessionsKey(Integer programId) {
        return TRAINING_PROGRAM_SESSIONS + programId;
    }
}
