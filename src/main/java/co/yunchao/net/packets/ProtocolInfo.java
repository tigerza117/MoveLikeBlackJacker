package co.yunchao.net.packets;

public interface ProtocolInfo {
    byte LOGIN_PACKET = 0x01;
    byte DISCONNECT_PACKET = 0x02;
    byte JOIN_ROOM_PACKET = 0x03;
    byte PLAYER_JOIN_PACKET = 0x04;
    byte PLAYER_LEAVE_PACKET = 0x05;
    byte PLAYER_BET_STACK_PACKET = 0x06;
    byte PLAYER_ACTION_PACKET = 0x07;
    byte PLAYER_METADATA_PACKET = 0x8;
    byte GAME_METADATA_PACKET = 0x9;
    byte CARD_TOGGLE_FLIP_PACKET = 0x10;
    byte CARD_SPAWN_PACKET = 0x11;
    byte CARD_DE_SPAWN_PACKET = 0x12;
    byte CHIP_SPAWN_PACKET = 0x13;
    byte CHIP_DE_SPAWN_PACKET = 0x14;
    byte PLAY_SOUND_PACKET = 0x15;
    byte STOP_SOUND_PACKET = 0x16;
    byte SET_SCORE_PACKET = 0x17;
}
