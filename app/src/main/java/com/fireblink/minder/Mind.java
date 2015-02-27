package com.fireblink.minder;

public class Mind {
    private int _id;
    private String _name;
    private String _body;
    public Mind () {

    }
    public Mind (String title, String body ) {
        this._name = title;
        this._body = body;
    }
    public Mind (int _id, String title, String _body) {
        this._id = _id;
        this._name = title;
        this._body = _body;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_body() {
        return _body;
    }

    public void set_body(String _body) {
        this._body = _body;
    }
}
