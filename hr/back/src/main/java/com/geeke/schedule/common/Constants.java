package com.geeke.schedule.common;

import lombok.Getter;

public class Constants {

    @Getter
    public enum TaskRunStatus {

        STOPPED("0", "已停止"),
        RUNNING("1", "运行中"),
        ;

        private final String code;

        private final String desc;

        TaskRunStatus(final String code, final String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    @Getter
    public enum TaskLockStatus {

        UNLOCKED("0", "已解锁"),
        LOCKED("1", "已锁定"),
        ;

        private final String code;

        private final String desc;

        TaskLockStatus(final String code, final String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    @Getter
    public enum TaskDeleteFlag {

        NOT_DELETED("0", "未删除"),
        DELETED("1", "已删除"),
        ;

        private final String code;

        private final String desc;

        TaskDeleteFlag(final String code, final String desc) {
            this.code = code;
            this.desc = desc;
        }

    }
}
