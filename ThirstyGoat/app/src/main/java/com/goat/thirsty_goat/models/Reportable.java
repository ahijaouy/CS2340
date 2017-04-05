package com.goat.thirsty_goat.models;

import org.json.JSONObject;

/**
 * Created by GabrielNAN on 3/28/17.
 */

interface Reportable {
    void setID(int id);
    JSONObject toJson();
}
