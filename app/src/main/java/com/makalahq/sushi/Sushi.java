package com.makalahq.sushi;

import bolts.Task;
import com.parse.*;

import java.util.Arrays;

/**
 * Created 17/10/14
 * for SushiDemo
 * by rme
 */
public class Sushi {

    /**
     * register to an object type
     * @param type
     */
    static public void register(String type) {
        ParseInstallation i = ParseInstallation.getCurrentInstallation();
        i.addUnique("sushi", type);
        i.saveInBackground();
    }

    /**
     * unregister to an object type
     * @param type
     */
    static public void unregister(String type) {
        ParseInstallation i = ParseInstallation.getCurrentInstallation();
        i.removeAll("sushi", Arrays.asList(new String[]{type}));
        i.saveInBackground();
    }

    /**
     * unregister to all object types
     */
    static public void unregisterAll() {
        ParseInstallation i = ParseInstallation.getCurrentInstallation();
        i.remove("sushi");
        i.saveInBackground();
    }

    /**
     * send the update trigger to the server
     * @param className the class of saved object
     */
    static private void updateLastMod(String className) {
        ParseObject ev = new ParseObject("SushiUpdates");
        ev.put("objectName", className);
        ev.saveEventually();
    }

    /**
     * Wrappers around Parse save methods
     */

    static public void save(ParseObject obj) throws com.parse.ParseException {
        obj.save();
        updateLastMod(obj.getClassName());
    }

    static public Task<Void> saveInBackground(ParseObject obj) {
        updateLastMod(obj.getClassName());
        return obj.saveInBackground();
    }

    static public void saveInBackground(ParseObject obj, SaveCallback callback) {
        updateLastMod(obj.getClassName());
        obj.saveInBackground(callback);
    }

    static public Task<Void> saveEventually(ParseObject obj) {
        updateLastMod(obj.getClassName());
        return obj.saveEventually();
    }

    static public void saveEventually(ParseObject obj, SaveCallback callback) {
        updateLastMod(obj.getClassName());
        obj.saveEventually(callback);
    }

    /**
     * Wrappers around Parse delete methods
     */
    
    static public void delete(ParseObject obj) throws ParseException {
        updateLastMod(obj.getClassName());
        obj.delete();
    }
    
    static public Task<Void> deleteInBackground(ParseObject obj) {
        updateLastMod(obj.getClassName());
        return obj.deleteInBackground();
    }

    static public void deleteInBackground(ParseObject obj, DeleteCallback callback) {
        updateLastMod(obj.getClassName());
        obj.deleteInBackground(callback);
    }

    static public Task<Void> deleteEventually(ParseObject obj) {
        updateLastMod(obj.getClassName());
        return obj.deleteEventually();
    }

    static public void deleteEventually(ParseObject obj, DeleteCallback callback) {
        updateLastMod(obj.getClassName());
        obj.deleteEventually(callback);
    }

}