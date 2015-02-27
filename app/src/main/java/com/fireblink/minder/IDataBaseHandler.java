package com.fireblink.minder;

import java.util.List;

public interface IDataBaseHandler {
    public void addMind(Mind mind);
    public Mind getMindById(int id);
    public List<Mind> getAllMinds();
    public int getMindCount();
    public int updateMind(Mind mind);
    public void deleteMind(Mind mind);
    public void deleteAll();
}
