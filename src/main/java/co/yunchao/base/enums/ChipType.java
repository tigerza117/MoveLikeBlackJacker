package co.yunchao.base.enums;

public enum ChipType {
    CHIP_SMALL {
        @Override
        public int getPoint() {
            return 50;
        }
    },
    CHIP_MEDIUM {
        @Override
        public int getPoint() {
            return 150;
        }
    },
    CHIP_LARGE {
        @Override
        public int getPoint() {
            return 500;
        }
    };

    public abstract int getPoint();
}


