package co.yunchao.base.enums;

public enum ChipType {
    CHIP_SMALL {
        @Override
        public int getAmount() {
            return 50;
        }
    },
    CHIP_MEDIUM {
        @Override
        public int getAmount() {
            return 150;
        }
    },
    CHIP_LARGE {
        @Override
        public int getAmount() {
            return 500;
        }
    };

    public abstract int getAmount();
}


