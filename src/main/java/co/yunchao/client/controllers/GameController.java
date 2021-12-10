package co.yunchao.client.controllers;

import co.yunchao.client.views.Table;

public class GameController {
    private final Table view;

    public GameController() {
        view = new Table();
    }

    public void Start() {
        view.render();
    }
}
